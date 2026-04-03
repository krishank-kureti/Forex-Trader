package com.forex.dto;

import java.time.LocalDateTime;

public class TradeResponse {
    private Long tradeId;
    private Long buyOrderId;
    private Long sellOrderId;
    private Double executedPrice;
    private LocalDateTime executedAt;
    
    public TradeResponse() {}
    
    public TradeResponse(Long tradeId, Long buyOrderId, Long sellOrderId, Double executedPrice, LocalDateTime executedAt) {
        this.tradeId = tradeId;
        this.buyOrderId = buyOrderId;
        this.sellOrderId = sellOrderId;
        this.executedPrice = executedPrice;
        this.executedAt = executedAt;
    }
    
    public Long getTradeId() { return tradeId; }
    public void setTradeId(Long tradeId) { this.tradeId = tradeId; }
    
    public Long getBuyOrderId() { return buyOrderId; }
    public void setBuyOrderId(Long buyOrderId) { this.buyOrderId = buyOrderId; }
    
    public Long getSellOrderId() { return sellOrderId; }
    public void setSellOrderId(Long sellOrderId) { this.sellOrderId = sellOrderId; }
    
    public Double getExecutedPrice() { return executedPrice; }
    public void setExecutedPrice(Double executedPrice) { this.executedPrice = executedPrice; }
    
    public LocalDateTime getExecutedAt() { return executedAt; }
    public void setExecutedAt(LocalDateTime executedAt) { this.executedAt = executedAt; }
}