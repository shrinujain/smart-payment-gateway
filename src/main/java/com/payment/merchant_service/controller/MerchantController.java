package com.payment.merchant_service.controller;

import com.payment.merchant_service.entity.KycStatus;
import com.payment.merchant_service.entity.Merchant;
import com.payment.merchant_service.repository.MerchantRepository;
import com.payment.merchant_service.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/merchants")
public class MerchantController {

    @Autowired
    private MerchantRepository repo;

    @Autowired
    private MerchantService service;



@GetMapping("/count")
    public Map<KycStatus, Long> countMerhantByStatus(){
        return repo.findAll().stream()
                .collect(Collectors.groupingBy(Merchant::getKycStatus,Collectors.counting()));
    }


    @PostMapping
    public Merchant onboard(@RequestBody Merchant merchant){
        merchant.setKycStatus(KycStatus.PENDING);
        return repo.save(merchant);
    }


    @GetMapping
    public List<Merchant> getMerchants(@RequestParam(required=false)String status){
        if(status==null) return repo.findAll();

        return repo.findAll().stream()
                .filter(m -> m.getKycStatus().equals(status))
                .toList();
    }

    @PutMapping("/{id}/approve")
    public Merchant approve(@PathVariable Long id){
        return service.approve(id);
    }

    @PutMapping("/{id}/reject")
    public Merchant reject(@PathVariable Long id) {
        return service.reject(id);
    }

}
