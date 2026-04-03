package com.forex.service;

import com.forex.model.CurrencyPair;
import com.forex.model.Order;
import com.forex.repository.CurrencyPairRepository;
import com.forex.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderMatchingEngine {
    
    private static final Logger log = LoggerFactory.getLogger(OrderMatchingEngine.class);
    
    private final OrderRepository orderRepository;
    private final CurrencyPairRepository currencyPairRepository;
    private final TradeProcessor tradeProcessor;
    
    public OrderMatchingEngine(OrderRepository orderRepository, CurrencyPairRepository currencyPairRepository, 
                               TradeProcessor tradeProcessor) {
        this.orderRepository = orderRepository;
        this.currencyPairRepository = currencyPairRepository;
        this.tradeProcessor = tradeProcessor;
    }
    
    @Scheduled(fixedRate = 10000)
    public void matchOrders() {
        log.info("Running order matching engine...");
        
        List<CurrencyPair> activePairs = currencyPairRepository.findByIsActive(true);
        
        for (CurrencyPair pair : activePairs) {
            matchOrdersForPair(pair.getId());
        }
    }
    
    private void matchOrdersForPair(Long currencyPairId) {
        List<Order> pendingOrders = orderRepository.findPendingOrdersByCurrencyPair(currencyPairId);
        
        if (pendingOrders.size() < 2) {
            return;
        }
        
        List<Order> buyOrders = pendingOrders.stream()
                .filter(o -> o.getType() == Order.OrderType.BUY)
                .collect(Collectors.toList());
        
        List<Order> sellOrders = pendingOrders.stream()
                .filter(o -> o.getType() == Order.OrderType.SELL)
                .collect(Collectors.toList());
        
        for (Order buyOrder : buyOrders) {
            for (Order sellOrder : sellOrders) {
                if (isCompatible(buyOrder, sellOrder)) {
                    tradeProcessor.createAndExecuteTrade(buyOrder, sellOrder);
                    return;
                }
            }
        }
    }
    
    private boolean isCompatible(Order buyOrder, Order sellOrder) {
        if (buyOrder.getPrice() == null || sellOrder.getPrice() == null) {
            return false;
        }
        return buyOrder.getPrice() >= sellOrder.getPrice();
    }
    
    public List<Order> getPendingOrders() {
        return orderRepository.findByStatus(Order.OrderStatus.PLACED);
    }
    
    public List<Order[]> findCompatiblePairs(List<Order> orders) {
        return null;
    }
}