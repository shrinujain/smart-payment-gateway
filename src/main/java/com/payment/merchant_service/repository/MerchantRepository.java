package com.payment.merchant_service.repository;

import com.payment.merchant_service.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository  extends JpaRepository<Merchant,Long> {}
