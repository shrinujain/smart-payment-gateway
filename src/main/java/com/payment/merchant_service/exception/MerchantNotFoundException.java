package com.payment.merchant_service.exception;

public class MerchantNotFoundException extends RuntimeException{

    public MerchantNotFoundException(Long id) {
        super("Merchant Merchant not found with id:" + id);
    }
}
