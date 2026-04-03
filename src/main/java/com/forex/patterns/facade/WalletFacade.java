package com.forex.patterns.facade;

import com.forex.dto.WalletResponse;
import com.forex.model.Transaction;
import com.forex.model.Wallet;
import com.forex.service.TransactionService;
import com.forex.service.WalletService;
import org.springframework.stereotype.Component;

@Component
public class WalletFacade {
    
    private final WalletService walletService;
    private final PaymentGatewayPort paymentGateway;
    private final TransactionService transactionService;
    
    public WalletFacade(WalletService walletService, PaymentGatewayPort paymentGateway, TransactionService transactionService) {
        this.walletService = walletService;
        this.paymentGateway = paymentGateway;
        this.transactionService = transactionService;
    }
    
    public WalletResponse deposit(Long traderId, double amount) {
        Wallet wallet = walletService.getWallet(traderId);
        
        boolean paymentSuccess = paymentGateway.processPayment(traderId.toString(), amount);
        
        if (paymentSuccess) {
            walletService.updateBalance(traderId, amount);
            transactionService.recordTransaction(wallet.getId(), Transaction.Type.DEPOSIT, amount, Transaction.Status.SUCCESS);
            return new WalletResponse(wallet.getId(), walletService.getBalance(traderId), "USD", "SUCCESS", "Deposit successful");
        }
        
        transactionService.recordTransaction(wallet.getId(), Transaction.Type.DEPOSIT, amount, Transaction.Status.FAILED);
        return new WalletResponse(wallet.getId(), walletService.getBalance(traderId), "USD", "FAILED", "Payment failed");
    }
    
    public WalletResponse withdraw(Long traderId, double amount) {
        if (!walletService.checkSufficientFunds(traderId, amount)) {
            return new WalletResponse(null, walletService.getBalance(traderId), "USD", "FAILED", "Insufficient funds");
        }
        
        Wallet wallet = walletService.getWallet(traderId);
        
        boolean withdrawalSuccess = paymentGateway.processWithdrawal(traderId.toString(), amount);
        
        if (withdrawalSuccess) {
            walletService.updateBalance(traderId, -amount);
            transactionService.recordTransaction(wallet.getId(), Transaction.Type.WITHDRAWAL, amount, Transaction.Status.SUCCESS);
            return new WalletResponse(wallet.getId(), walletService.getBalance(traderId), "USD", "SUCCESS", "Withdrawal successful");
        }
        
        transactionService.recordTransaction(wallet.getId(), Transaction.Type.WITHDRAWAL, amount, Transaction.Status.FAILED);
        return new WalletResponse(wallet.getId(), walletService.getBalance(traderId), "USD", "FAILED", "Withdrawal failed");
    }
    
    public double getBalance(Long traderId) {
        return walletService.getBalance(traderId);
    }
}