package com.forex.dto;

public class SettlementResponse {
    private Long settlementId;
    private Long tradeId;
    private Double settlementAmount;
    private Double fee;
    private String status;
    
    public SettlementResponse() {}
    
    public SettlementResponse(Long settlementId, Long tradeId, Double settlementAmount, Double fee, String status) {
        this.settlementId = settlementId;
        this.tradeId = tradeId;
        this.settlementAmount = settlementAmount;
        this.fee = fee;
        this.status = status;
    }
    
    public Long getSettlementId() { return settlementId; }
    public void setSettlementId(Long settlementId) { this.settlementId = settlementId; }
    
    public Long getTradeId() { return tradeId; }
    public void setTradeId(Long tradeId) { this.tradeId = tradeId; }
    
    public Double getSettlementAmount() { return settlementAmount; }
    public void setSettlementAmount(Double settlementAmount) { this.settlementAmount = settlementAmount; }
    
    public Double getFee() { return fee; }
    public void setFee(Double fee) { this.fee = fee; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}