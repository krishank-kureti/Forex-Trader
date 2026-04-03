package com.forex.dto;

import com.forex.model.Order;

public class OrderRequest {
    private Long currencyPairId;
    private Order.OrderType type;
    private Double amount;
    private Double price;
    
    public Long getCurrencyPairId() { return currencyPairId; }
    public void setCurrencyPairId(Long currencyPairId) { this.currencyPairId = currencyPairId; }
    
    public Order.OrderType getType() { return type; }
    public void setType(Order.OrderType type) { this.type = type; }
    
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}