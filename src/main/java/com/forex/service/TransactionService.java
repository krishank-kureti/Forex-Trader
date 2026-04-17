package com.forex.service;

import com.forex.model.Transaction;
import com.forex.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    
    private final TransactionRepository transactionRepository;
    
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
    
    public Transaction recordTransaction(Long walletId, Transaction.Type type, double amount, Transaction.Status status) {
        Transaction transaction = new Transaction();
        transaction.setWalletId(walletId);
        transaction.setType(type);
        transaction.setAmount(amount);
        transaction.setStatus(status);
        return transactionRepository.save(transaction);
    }
    
    public List<Transaction> getHistory(Long walletId) {
        return transactionRepository.findByWalletIdOrderByTimestampDesc(walletId);
    }
    
    public void updateTransactionStatus(Long transactionId, Transaction.Status status) {
        transactionRepository.findById(transactionId).ifPresent(tx -> {
            tx.setStatus(status);
            transactionRepository.save(tx);
        });
    }
    
    public void updateTransactionMessage(Long transactionId, String message) {
        transactionRepository.findById(transactionId).ifPresent(tx -> {
            tx.setMessage(message);
            transactionRepository.save(tx);
        });
    }
}