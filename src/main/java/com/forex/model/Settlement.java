package com.forex.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "settlements")
public class Settlement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "trade_id", nullable = false)
    private Long tradeId;
    
    @Column(name = "buyer_wallet_id")
    private Long buyerWalletId;
    
    @Column(name = "seller_wallet_id")
    private Long sellerWalletId;
    
    @Column(name = "settlement_amount", nullable = false)
    private Double settlementAmount;
    
    @Column(nullable = false)
    private Double fee;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
    
    @Column(name = "settled_at")
    private LocalDateTime settledAt;
    
    public enum Status {
        PENDING, SETTLING, SETTLED, FAILED, ROLLED_BACK
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getTradeId() { return tradeId; }
    public void setTradeId(Long tradeId) { this.tradeId = tradeId; }
    
    public Long getBuyerWalletId() { return buyerWalletId; }
    public void setBuyerWalletId(Long buyerWalletId) { this.buyerWalletId = buyerWalletId; }
    
    public Long getSellerWalletId() { return sellerWalletId; }
    public void setSellerWalletId(Long sellerWalletId) { this.sellerWalletId = sellerWalletId; }
    
    public Double getSettlementAmount() { return settlementAmount; }
    public void setSettlementAmount(Double settlementAmount) { this.settlementAmount = settlementAmount; }
    
    public Double getFee() { return fee; }
    public void setFee(Double fee) { this.fee = fee; }
    
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    
    public LocalDateTime getSettledAt() { return settledAt; }
    public void setSettledAt(LocalDateTime settledAt) { this.settledAt = settledAt; }
}