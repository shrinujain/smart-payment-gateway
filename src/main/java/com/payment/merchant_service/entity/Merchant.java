package com.payment.merchant_service.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Merchant {

    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String businessName;
    private String email;

    @Enumerated(EnumType.STRING)  // Important! Saves as String in DB, not 0,1,2
    private KycStatus kycStatus=KycStatus.PENDING;

    private LocalDateTime createdAt=LocalDateTime.now();


}
