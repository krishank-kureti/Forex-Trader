package com.forex.dto;

import java.time.LocalDateTime;

public class OrderResponse {
    private Long orderId;
    private String currencyPair;
    private String type;
    private Double amount;
    private Double price;
    private String status;
    private LocalDateTime createdAt;
    private String message;
    
    public OrderResponse() {}
    
    public OrderResponse(Long orderId, String currencyPair, String type, Double amount, 
                         Double price, String status, LocalDateTime createdAt, String message) {
        this.orderId = orderId;
        this.currencyPair = currencyPair;
        this.type = type;
        this.amount = amount;
        this.price = price;
        this.status = status;
        this.createdAt = createdAt;
        this.message = message;
    }
    
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    
    public String getCurrencyPair() { return currencyPair; }
    public void setCurrencyPair(String currencyPair) { this.currencyPair = currencyPair; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}