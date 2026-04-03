package com.forex.patterns.strategy;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class PercentageFeeStrategy implements FeeStrategy {
    
    private static final double FEE_PERCENTAGE = 0.001;
    
    @Override
    public double calculateFee(double amount) {
        return amount * FEE_PERCENTAGE;
    }
}