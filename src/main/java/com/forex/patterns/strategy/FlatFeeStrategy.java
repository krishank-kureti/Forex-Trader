package com.forex.patterns.strategy;

import org.springframework.stereotype.Component;

@Component
public class FlatFeeStrategy implements FeeStrategy {
    
    private static final double FLAT_FEE = 2.50;
    
    @Override
    public double calculateFee(double amount) {
        return FLAT_FEE;
    }
}