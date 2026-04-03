package com.forex.patterns.facade;

public interface PaymentGatewayPort {
    boolean processPayment(String userId, Double amount);
    boolean processWithdrawal(String userId, Double amount);
}