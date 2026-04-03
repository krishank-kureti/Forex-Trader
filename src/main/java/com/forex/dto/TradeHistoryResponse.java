package com.forex.dto;

import java.time.LocalDateTime;

public class TradeHistoryResponse {
    private Long id;
    private Long tradeId;
    private String type;
    private String currencyPair;
    private Double amount;
    private Double price;
    private LocalDateTime timestamp;
    private Double pnl;
    
    public TradeHistoryResponse() {}
    
    public TradeHistoryResponse(Long id, Long tradeId, String type, String currencyPair, 
                                Double amount, Double price, LocalDateTime timestamp, Double pnl) {
        this.id = id;
        this.tradeId = tradeId;
        this.type = type;
        this.currencyPair = currencyPair;
        this.amount = amount;
        this.price = price;
        this.timestamp = timestamp;
        this.pnl = pnl;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getTradeId() { return tradeId; }
    public void setTradeId(Long tradeId) { this.tradeId = tradeId; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getCurrencyPair() { return currencyPair; }
    public void setCurrencyPair(String currencyPair) { this.currencyPair = currencyPair; }
    
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public Double getPnl() { return pnl; }
    public void setPnl(Double pnl) { this.pnl = pnl; }
}