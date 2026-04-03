package com.forex.model;

import jakarta.persistence.*;

@Entity
@Table(name = "currency_pairs")
public class CurrencyPair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "base_currency", nullable = false)
    private String baseCurrency;
    
    @Column(name = "quote_currency", nullable = false)
    private String quoteCurrency;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
    
    public String getPairCode() {
        return baseCurrency + "/" + quoteCurrency;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getBaseCurrency() { return baseCurrency; }
    public void setBaseCurrency(String baseCurrency) { this.baseCurrency = baseCurrency; }
    
    public String getQuoteCurrency() { return quoteCurrency; }
    public void setQuoteCurrency(String quoteCurrency) { this.quoteCurrency = quoteCurrency; }
    
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}