package com.forex.patterns.observer;

import com.forex.model.Order;
import com.forex.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class WalletUpdateObserver implements TradeObserver {
    
    private static final Logger log = LoggerFactory.getLogger(WalletUpdateObserver.class);
    
    private final WalletService walletService;
    
    public WalletUpdateObserver(WalletService walletService) {
        this.walletService = walletService;
    }
    
    @Override
    public void onOrderPlaced(Order order) {
        log.info("Wallet: Processing order placement for trader {}", order.getTraderId());
    }
    
    @Override
    public void onOrderMatched(Order order) {
        log.info("Wallet: Order {} matched, preparing for trade execution", order.getId());
    }
    
    @Override
    public void onTradeExecuted(Long tradeId) {
        log.info("Wallet: Trade {} executed, wallet balances will be updated", tradeId);
    }
}