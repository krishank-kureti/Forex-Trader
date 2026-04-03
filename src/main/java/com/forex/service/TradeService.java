package com.forex.service;

import com.forex.dto.OrderRequest;
import com.forex.dto.OrderResponse;
import com.forex.model.CurrencyPair;
import com.forex.model.Order;
import com.forex.patterns.factory.OrderFactory;
import com.forex.patterns.observer.TradeEventPublisher;
import com.forex.repository.CurrencyPairRepository;
import com.forex.repository.OrderRepository;
import com.forex.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeService {
    
    private final OrderRepository orderRepository;
    private final WalletService walletService;
    private final CurrencyPairRepository currencyPairRepository;
    private final OrderFactory orderFactory;
    private final TradeEventPublisher tradeEventPublisher;
    
    public TradeService(OrderRepository orderRepository, WalletService walletService, 
                        CurrencyPairRepository currencyPairRepository, OrderFactory orderFactory,
                        TradeEventPublisher tradeEventPublisher) {
        this.orderRepository = orderRepository;
        this.walletService = walletService;
        this.currencyPairRepository = currencyPairRepository;
        this.orderFactory = orderFactory;
        this.tradeEventPublisher = tradeEventPublisher;
    }
    
    public OrderResponse placeOrder(OrderRequest request, Long traderId) {
        if (!validateUser(traderId)) {
            throw new RuntimeException("User validation failed");
        }
        
        double totalAmount = request.getAmount() * (request.getPrice() != null ? request.getPrice() : 1.0);
        if (!checkBalance(traderId, totalAmount)) {
            return new OrderResponse(null, null, request.getType().name(), request.getAmount(), 
                    request.getPrice(), "REJECTED", null, "Insufficient balance");
        }
        
        Order order = new Order();
        order.setTraderId(traderId);
        order.setCurrencyPairId(request.getCurrencyPairId());
        order.setType(request.getType());
        order.setAmount(request.getAmount());
        order.setPrice(request.getPrice());
        order.setStatus(Order.OrderStatus.VALIDATING);
        
        order = orderRepository.save(order);
        
        try {
            orderFactory.createAndExecuteOrder(order);
            
            order.setStatus(Order.OrderStatus.PLACED);
            order = orderRepository.save(order);
            
            tradeEventPublisher.notifyObservers(order);
            
            CurrencyPair pair = currencyPairRepository.findById(request.getCurrencyPairId())
                    .orElse(null);
            String pairCode = pair != null ? pair.getPairCode() : "UNKNOWN";
            
            return new OrderResponse(order.getId(), pairCode, order.getType().name(), 
                    order.getAmount(), order.getPrice(), order.getStatus().name(), 
                    order.getCreatedAt(), "Order placed successfully");
            
        } catch (Exception e) {
            order.setStatus(Order.OrderStatus.REJECTED);
            orderRepository.save(order);
            return new OrderResponse(order.getId(), null, request.getType().name(), 
                    request.getAmount(), request.getPrice(), "REJECTED", null, e.getMessage());
        }
    }
    
    public boolean validateUser(Long traderId) {
        return traderId != null && traderId > 0;
    }
    
    public boolean checkBalance(Long traderId, double amount) {
        return walletService.checkSufficientFunds(traderId, amount);
    }
    
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        if (order.getStatus() == Order.OrderStatus.MATCHED || 
            order.getStatus() == Order.OrderStatus.EXECUTED ||
            order.getStatus() == Order.OrderStatus.SETTLING ||
            order.getStatus() == Order.OrderStatus.SETTLED) {
            throw new RuntimeException("Cannot cancel order in current status");
        }
        
        order.setStatus(Order.OrderStatus.CANCELLED);
        orderRepository.save(order);
    }
    
    public List<Order> getHistory(Long traderId) {
        return orderRepository.findByTraderId(traderId);
    }
}