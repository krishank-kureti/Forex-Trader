package com.forex.patterns.observer;

import com.forex.model.Order;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TradeEventPublisher {
    
    private final List<TradeObserver> observers = new ArrayList<>();
    private final NotificationObserver notificationObserver;
    private final WalletUpdateObserver walletUpdateObserver;
    
    public TradeEventPublisher(NotificationObserver notificationObserver, WalletUpdateObserver walletUpdateObserver) {
        this.notificationObserver = notificationObserver;
        this.walletUpdateObserver = walletUpdateObserver;
    }
    
    @PostConstruct
    public void init() {
        subscribe(notificationObserver);
        subscribe(walletUpdateObserver);
    }
    
    public void subscribe(TradeObserver observer) {
        observers.add(observer);
    }
    
    public void unsubscribe(TradeObserver observer) {
        observers.remove(observer);
    }
    
    public void notifyOrderPlaced(Order order) {
        observers.forEach(o -> o.onOrderPlaced(order));
    }
    
    public void notifyOrderMatched(Order order) {
        observers.forEach(o -> o.onOrderMatched(order));
    }
    
    public void notifyTradeExecuted(Long tradeId) {
        observers.forEach(o -> o.onTradeExecuted(tradeId));
    }
    
    public void notifyObservers(Order order) {
        notifyOrderPlaced(order);
    }
}