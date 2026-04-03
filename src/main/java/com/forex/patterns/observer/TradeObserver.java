package com.forex.patterns.observer;

import com.forex.model.Order;

public interface TradeObserver {
    void onOrderPlaced(Order order);
    void onOrderMatched(Order order);
    void onTradeExecuted(Long tradeId);
}