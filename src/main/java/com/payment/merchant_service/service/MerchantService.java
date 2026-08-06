package com.payment.merchant_service.service;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.payment.merchant_service.entity.KycStatus;
import com.payment.merchant_service.entity.Merchant;
import com.payment.merchant_service.exception.MerchantNotFoundException;
import com.payment.merchant_service.repository.MerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MerchantService {

    private MerchantRepository repo;

    private final PubSubTemplate pubSubTemplate;

    public MerchantService(MerchantRepository repo, PubSubTemplate pubSubTemplate) {
        this.repo = repo;
        this.pubSubTemplate = pubSubTemplate;
    }

    public Merchant approve(Long id) {
        Merchant m = repo.findById(id)
                .orElseThrow(() -> new MerchantNotFoundException(id));

        if(m.getKycStatus() != KycStatus.PENDING) {
            throw new IllegalStateException("Only PENDING merchant can be APPROVED. Current: " + m.getKycStatus());
        }
        m.setKycStatus(KycStatus.APPROVED);
        Merchant saved = repo.save(m);

        String json = "{\"merchantId\": "+saved.getId()+",\"email\":\""+saved.getEmail()+"\"}";
        pubSubTemplate.publish("merchant-approved",json);

        System.out.println("Publish to GCP:"+json);

        return repo.save(m);
    }

    public Merchant reject(Long id){
        Merchant m = repo.findById(id)
                .orElseThrow(()-> new MerchantNotFoundException(id));

        if(m.getKycStatus()!=KycStatus.PENDING){
            throw new IllegalArgumentException("Only pending can be Rejected");
        }
        m.setKycStatus(KycStatus.REJECTED);
        return repo.save(m);
    }
}
