package com.forex.patterns.facade;

import com.forex.dto.WalletResponse;
import com.forex.model.Transaction;
import com.forex.model.Wallet;
import com.forex.service.TransactionService;
import com.forex.service.WalletService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

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
        
        if (amount > 1_000_000) {
            Transaction pendingTx = transactionService.recordTransaction(wallet.getId(), Transaction.Type.DEPOSIT, amount, Transaction.Status.PENDING);
            scheduleDepositFailure(pendingTx.getId(), traderId, amount);
            return new WalletResponse(wallet.getId(), walletService.getBalance(traderId), "USD", "PENDING", "Deposit pending verification");
        }
        
        Transaction pendingTx = transactionService.recordTransaction(wallet.getId(), Transaction.Type.DEPOSIT, amount, Transaction.Status.PENDING);
        
        boolean paymentSuccess = paymentGateway.processPayment(traderId.toString(), amount);
        
        if (paymentSuccess) {
            walletService.updateBalance(traderId, amount);
            scheduleDepositSuccess(pendingTx.getId(), traderId, amount);
            return new WalletResponse(wallet.getId(), walletService.getBalance(traderId), "USD", "PENDING", "Deposit pending verification");
        }
        
        scheduleDepositFailure(pendingTx.getId(), traderId, amount);
        return new WalletResponse(wallet.getId(), walletService.getBalance(traderId), "USD", "PENDING", "Deposit pending verification");
    }
    
    @Async
    public CompletableFuture<Void> scheduleDepositSuccess(Long transactionId, Long traderId, double amount) {
        try {
            Thread.sleep(30000);
            
            Wallet wallet = walletService.getWallet(traderId);
            walletService.updateBalance(traderId, amount);
            transactionService.updateTransactionStatus(transactionId, Transaction.Status.SUCCESS);
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return CompletableFuture.completedFuture(null);
    }
    
    @Async
    public CompletableFuture<Void> scheduleDepositFailure(Long transactionId, Long traderId, double amount) {
        try {
            Thread.sleep(30000);
            
            transactionService.updateTransactionStatus(transactionId, Transaction.Status.FAILED);
            transactionService.updateTransactionMessage(transactionId, "Keep it under 1 million");
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return CompletableFuture.completedFuture(null);
    }
    
    public WalletResponse withdraw(Long traderId, double amount) {
        Wallet wallet = walletService.getWallet(traderId);
        
        if (!walletService.checkSufficientFunds(traderId, amount)) {
            transactionService.recordTransaction(wallet.getId(), Transaction.Type.WITHDRAWAL, amount, Transaction.Status.FAILED);
            return new WalletResponse(wallet.getId(), walletService.getBalance(traderId), "USD", "FAILED", "Insufficient funds");
        }
        
        Transaction pendingTx = transactionService.recordTransaction(wallet.getId(), Transaction.Type.WITHDRAWAL, amount, Transaction.Status.PENDING);
        
        boolean withdrawalSuccess = paymentGateway.processWithdrawal(traderId.toString(), amount);
        
        if (withdrawalSuccess) {
            walletService.updateBalance(traderId, -amount);
            scheduleWithdrawalSuccess(pendingTx.getId(), traderId);
            return new WalletResponse(wallet.getId(), walletService.getBalance(traderId), "USD", "PENDING", "Withdrawal pending verification");
        }
        
        scheduleWithdrawalFailure(pendingTx.getId());
        return new WalletResponse(wallet.getId(), walletService.getBalance(traderId), "USD", "PENDING", "Withdrawal pending verification");
    }
    
    @Async
    public CompletableFuture<Void> scheduleWithdrawalSuccess(Long transactionId, Long traderId) {
        try {
            Thread.sleep(30000);
            
            transactionService.updateTransactionStatus(transactionId, Transaction.Status.SUCCESS);
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return CompletableFuture.completedFuture(null);
    }
    
    @Async
    public CompletableFuture<Void> scheduleWithdrawalFailure(Long transactionId) {
        try {
            Thread.sleep(30000);
            
            transactionService.updateTransactionStatus(transactionId, Transaction.Status.FAILED);
            transactionService.updateTransactionMessage(transactionId, "Withdrawal failed");
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return CompletableFuture.completedFuture(null);
    }
    
    public double getBalance(Long traderId) {
        return walletService.getBalance(traderId);
    }
}