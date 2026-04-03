package com.forex.service;

import com.forex.model.Wallet;
import com.forex.repository.WalletRepository;
import org.springframework.stereotype.Service;

@Service
public class WalletService {
    
    private final WalletRepository walletRepository;
    
    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }
    
    public Wallet getWallet(Long traderId) {
        return walletRepository.findByTraderId(traderId)
                .orElseGet(() -> createWallet(traderId));
    }
    
    public double getBalance(Long traderId) {
        Wallet wallet = getWallet(traderId);
        return wallet != null ? wallet.getBalance() : 0.0;
    }
    
    public boolean checkSufficientFunds(Long traderId, double amount) {
        return getBalance(traderId) >= amount;
    }
    
    public void updateBalance(Long traderId, double delta) {
        Wallet wallet = getWallet(traderId);
        if (wallet != null) {
            wallet.setBalance(wallet.getBalance() + delta);
            walletRepository.save(wallet);
        }
    }
    
    private Wallet createWallet(Long traderId) {
        Wallet wallet = new Wallet();
        wallet.setTraderId(traderId);
        wallet.setBalance(0.0);
        wallet.setCurrency("USD");
        return walletRepository.save(wallet);
    }
}