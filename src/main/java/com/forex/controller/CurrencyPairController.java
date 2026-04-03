package com.forex.controller;

import com.forex.dto.CurrencyPairRequest;
import com.forex.model.CurrencyPair;
import com.forex.repository.CurrencyPairRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class CurrencyPairController {
    
    private final CurrencyPairRepository currencyPairRepository;
    
    public CurrencyPairController(CurrencyPairRepository currencyPairRepository) {
        this.currencyPairRepository = currencyPairRepository;
    }
    
    @GetMapping("/pairs")
    public ResponseEntity<List<CurrencyPair>> getAllPairs() {
        return ResponseEntity.ok(currencyPairRepository.findAll());
    }
    
    @PostMapping("/pairs")
    public ResponseEntity<CurrencyPair> createPair(@RequestBody CurrencyPairRequest request) {
        CurrencyPair pair = new CurrencyPair();
        pair.setBaseCurrency(request.getBaseCurrency());
        pair.setQuoteCurrency(request.getQuoteCurrency());
        pair.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);
        
        CurrencyPair saved = currencyPairRepository.save(pair);
        return ResponseEntity.ok(saved);
    }
    
    @PutMapping("/pairs/{id}")
    public ResponseEntity<CurrencyPair> updatePair(@PathVariable Long id, @RequestBody CurrencyPairRequest request) {
        CurrencyPair pair = currencyPairRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Currency pair not found"));
        
        pair.setBaseCurrency(request.getBaseCurrency());
        pair.setQuoteCurrency(request.getQuoteCurrency());
        if (request.getIsActive() != null) {
            pair.setIsActive(request.getIsActive());
        }
        
        CurrencyPair updated = currencyPairRepository.save(pair);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/pairs/{id}")
    public ResponseEntity<String> deletePair(@PathVariable Long id) {
        currencyPairRepository.deleteById(id);
        return ResponseEntity.ok("Currency pair deleted successfully");
    }
}