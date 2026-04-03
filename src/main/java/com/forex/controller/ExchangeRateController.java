package com.forex.controller;

import com.forex.dto.CurrencyPairRequest;
import com.forex.model.CurrencyPair;
import com.forex.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ExchangeRateController {
    
    private final ExchangeRateService exchangeRateService;
    
    public ExchangeRateController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }
    
    @GetMapping("/rates")
    public ResponseEntity<Map<String, Double>> getAllRates() {
        return ResponseEntity.ok(exchangeRateService.fetchRates());
    }
    
    @GetMapping("/rates/{pair}")
    public ResponseEntity<Double> getRate(@PathVariable String pair) {
        Double rate = exchangeRateService.fetchRate(pair);
        return ResponseEntity.ok(rate);
    }
}