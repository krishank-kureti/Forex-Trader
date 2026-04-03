package com.forex.service;

import com.forex.model.*;
import com.forex.patterns.strategy.FeeStrategy;
import com.forex.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class SettlementService {
    
    private static final Logger log = LoggerFactory.getLogger(SettlementService.class);
    
    private final SettlementRepository settlementRepository;
    private final TradeRepository tradeRepository;
    private final OrderRepository orderRepository;
    private final WalletRepository walletRepository;
    private final TradeHistoryRepository tradeHistoryRepository;
    private final FeeStrategy feeStrategy;
    
    public SettlementService(SettlementRepository settlementRepository, TradeRepository tradeRepository,
                             OrderRepository orderRepository, WalletRepository walletRepository,
                             TradeHistoryRepository tradeHistoryRepository, FeeStrategy feeStrategy) {
        this.settlementRepository = settlementRepository;
        this.tradeRepository = tradeRepository;
        this.orderRepository = orderRepository;
        this.walletRepository = walletRepository;
        this.tradeHistoryRepository = tradeHistoryRepository;
        this.feeStrategy = feeStrategy;
    }
    
    @Transactional
    public void settle(Trade trade) {
        log.info("Starting settlement for trade {}", trade.getId());
        
        Order buyOrder = orderRepository.findById(trade.getBuyOrderId()).orElse(null);
        Order sellOrder = orderRepository.findById(trade.getSellOrderId()).orElse(null);
        
        if (buyOrder == null || sellOrder == null) {
            log.error("Cannot settle: orders not found");
            return;
        }
        
        double settlementAmount = trade.getExecutedPrice() * buyOrder.getAmount();
        double fee = calculateFee(settlementAmount);
        
        Settlement settlement = new Settlement();
        settlement.setTradeId(trade.getId());
        settlement.setSettlementAmount(settlementAmount);
        settlement.setFee(fee);
        settlement.setStatus(Settlement.Status.SETTLING);
        
        settlementRepository.save(settlement);
        
        try {
            updateBuyerWallet(buyOrder.getTraderId(), settlementAmount);
            updateSellerWallet(sellOrder.getTraderId(), settlementAmount - fee);
            
            settlement.setStatus(Settlement.Status.SETTLED);
            settlement.setSettledAt(LocalDateTime.now());
            settlementRepository.save(settlement);
            
            recordToHistory(trade, buyOrder, sellOrder);
            
            log.info("Settlement completed for trade {}", trade.getId());
            
        } catch (Exception e) {
            log.error("Settlement failed for trade {}", trade.getId(), e);
            settlement.setStatus(Settlement.Status.FAILED);
            settlementRepository.save(settlement);
        }
    }
    
    public double calculateFee(double tradeAmount) {
        return feeStrategy.calculateFee(tradeAmount);
    }
    
    private void updateBuyerWallet(Long traderId, double amount) {
        Wallet wallet = walletRepository.findByTraderId(traderId).orElse(null);
        if (wallet != null) {
            wallet.setBalance(wallet.getBalance() - amount);
            walletRepository.save(wallet);
        }
    }
    
    private void updateSellerWallet(Long traderId, double amount) {
        Wallet wallet = walletRepository.findByTraderId(traderId).orElse(null);
        if (wallet != null) {
            wallet.setBalance(wallet.getBalance() + amount);
            walletRepository.save(wallet);
        }
    }
    
    private void recordToHistory(Trade trade, Order buyOrder, Order sellOrder) {
        TradeHistory buyHistory = new TradeHistory();
        buyHistory.setTraderId(buyOrder.getTraderId());
        buyHistory.setTradeId(trade.getId());
        buyHistory.setType(TradeHistory.TradeType.BUY);
        buyHistory.setCurrencyPair("USD/EUR");
        buyHistory.setAmount(buyOrder.getAmount());
        buyHistory.setPrice(trade.getExecutedPrice());
        tradeHistoryRepository.save(buyHistory);
        
        TradeHistory sellHistory = new TradeHistory();
        sellHistory.setTraderId(sellOrder.getTraderId());
        sellHistory.setTradeId(trade.getId());
        sellHistory.setType(TradeHistory.TradeType.SELL);
        sellHistory.setCurrencyPair("USD/EUR");
        sellHistory.setAmount(sellOrder.getAmount());
        sellHistory.setPrice(trade.getExecutedPrice());
        tradeHistoryRepository.save(sellHistory);
    }
    
    public void triggerSettlement(Trade trade) {
        settle(trade);
    }
    
    public Settlement getSettlement(Long tradeId) {
        return settlementRepository.findByTradeId(tradeId).orElse(null);
    }
    
    public void rollback(Trade trade) {
        Settlement settlement = settlementRepository.findByTradeId(trade.getId()).orElse(null);
        if (settlement != null) {
            settlement.setStatus(Settlement.Status.ROLLED_BACK);
            settlementRepository.save(settlement);
        }
    }
}