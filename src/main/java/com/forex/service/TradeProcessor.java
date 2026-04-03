package com.forex.service;

import com.forex.model.Order;
import com.forex.model.Trade;
import com.forex.patterns.observer.TradeEventPublisher;
import com.forex.repository.OrderRepository;
import com.forex.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TradeProcessor {
    
    private static final Logger log = LoggerFactory.getLogger(TradeProcessor.class);
    
    private final TradeRepository tradeRepository;
    private final OrderRepository orderRepository;
    private final TradeEventPublisher tradeEventPublisher;
    private final SettlementService settlementService;
    
    public TradeProcessor(TradeRepository tradeRepository, OrderRepository orderRepository, 
                          TradeEventPublisher tradeEventPublisher, SettlementService settlementService) {
        this.tradeRepository = tradeRepository;
        this.orderRepository = orderRepository;
        this.tradeEventPublisher = tradeEventPublisher;
        this.settlementService = settlementService;
    }
    
    @Transactional
    public Trade createAndExecuteTrade(Order buyOrder, Order sellOrder) {
        double executedPrice = calculateExecutedPrice(buyOrder, sellOrder);
        
        Trade trade = new Trade();
        trade.setBuyOrderId(buyOrder.getId());
        trade.setSellOrderId(sellOrder.getId());
        trade.setExecutedPrice(executedPrice);
        trade.setExecutedAt(LocalDateTime.now());
        
        trade = tradeRepository.save(trade);
        
        updateOrderStatus(buyOrder, Order.OrderStatus.EXECUTED);
        updateOrderStatus(sellOrder, Order.OrderStatus.EXECUTED);
        
        tradeEventPublisher.notifyTradeExecuted(trade.getId());
        
        settlementService.triggerSettlement(trade);
        
        log.info("Trade {} executed between orders {} and {} at price {}", 
                trade.getId(), buyOrder.getId(), sellOrder.getId(), executedPrice);
        
        return trade;
    }
    
    private double calculateExecutedPrice(Order buyOrder, Order sellOrder) {
        if (buyOrder.getPrice() != null && sellOrder.getPrice() != null) {
            return (buyOrder.getPrice() + sellOrder.getPrice()) / 2;
        }
        return buyOrder.getPrice() != null ? buyOrder.getPrice() : sellOrder.getPrice();
    }
    
    public void updateOrderStatus(Order order, Order.OrderStatus status) {
        order.setStatus(status);
        orderRepository.save(order);
    }
    
    public void triggerSettlement(Trade trade) {
        settlementService.settle(trade);
    }
}