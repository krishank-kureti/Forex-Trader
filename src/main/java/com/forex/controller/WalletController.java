package com.forex.controller;

import com.forex.dto.WalletRequest;
import com.forex.dto.WalletResponse;
import com.forex.model.Transaction;
import com.forex.patterns.facade.WalletFacade;
import com.forex.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {
    
    private final WalletFacade walletFacade;
    private final TransactionService transactionService;
    
    public WalletController(WalletFacade walletFacade, TransactionService transactionService) {
        this.walletFacade = walletFacade;
        this.transactionService = transactionService;
    }
    
    @PostMapping("/deposit")
    public ResponseEntity<WalletResponse> deposit(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody WalletRequest request) {
        Long traderId = getTraderId(userDetails);
        return ResponseEntity.ok(walletFacade.deposit(traderId, request.getAmount()));
    }
    
    @PostMapping("/withdraw")
    public ResponseEntity<WalletResponse> withdraw(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody WalletRequest request) {
        Long traderId = getTraderId(userDetails);
        return ResponseEntity.ok(walletFacade.withdraw(traderId, request.getAmount()));
    }
    
    @GetMapping("/balance")
    public ResponseEntity<Double> getBalance(@AuthenticationPrincipal UserDetails userDetails) {
        Long traderId = getTraderId(userDetails);
        return ResponseEntity.ok(walletFacade.getBalance(traderId));
    }
    
    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> getTransactions(@AuthenticationPrincipal UserDetails userDetails) {
        Long traderId = getTraderId(userDetails);
        return ResponseEntity.ok(transactionService.getHistory(traderId));
    }
    
    private Long getTraderId(UserDetails userDetails) {
        return 1L;
    }
}