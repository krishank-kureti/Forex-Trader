package com.forex.dto;

public class WalletResponse {
    private Long walletId;
    private Double balance;
    private String currency;
    private String status;
    private String message;
    
    public WalletResponse() {}
    
    public WalletResponse(Long walletId, Double balance, String currency, String status, String message) {
        this.walletId = walletId;
        this.balance = balance;
        this.currency = currency;
        this.status = status;
        this.message = message;
    }
    
    public Long getWalletId() { return walletId; }
    public void setWalletId(Long walletId) { this.walletId = walletId; }
    
    public Double getBalance() { return balance; }
    public void setBalance(Double balance) { this.balance = balance; }
    
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}