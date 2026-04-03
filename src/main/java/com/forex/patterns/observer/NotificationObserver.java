package com.forex.patterns.observer;

import com.forex.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class NotificationObserver implements TradeObserver {
    
    private static final Logger log = LoggerFactory.getLogger(NotificationObserver.class);
    
    @Override
    public void onOrderPlaced(Order order) {
        log.info("Notification: Order {} placed by trader {}", order.getId(), order.getTraderId());
    }
    
    @Override
    public void onOrderMatched(Order order) {
        log.info("Notification: Order {} matched", order.getId());
    }
    
    @Override
    public void onTradeExecuted(Long tradeId) {
        log.info("Notification: Trade {} executed", tradeId);
    }
}