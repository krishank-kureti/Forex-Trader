package com.forex.dto;

public class WalletRequest {
    private Double amount;
    private String currency;
    private String password;
    
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}