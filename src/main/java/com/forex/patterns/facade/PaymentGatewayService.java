package com.forex.patterns.facade;

import org.springframework.stereotype.Component;

@Component
public class PaymentGatewayService implements PaymentGatewayPort {
    
    @Override
    public boolean processPayment(String userId, Double amount) {
        return true;
    }
    
    @Override
    public boolean processWithdrawal(String userId, Double amount) {
        return true;
    }
}