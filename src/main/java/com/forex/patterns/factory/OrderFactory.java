package com.forex.patterns.factory;

import com.forex.model.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderFactory {
    
    public void createAndExecuteOrder(Order orderEntity) {
        if (orderEntity.getType() == Order.OrderType.BUY) {
            System.out.println("Executing BUY order for amount: " + orderEntity.getAmount());
        } else if (orderEntity.getType() == Order.OrderType.SELL) {
            System.out.println("Executing SELL order for amount: " + orderEntity.getAmount());
        } else {
            throw new IllegalArgumentException("Unknown order type: " + orderEntity.getType());
        }
    }
}