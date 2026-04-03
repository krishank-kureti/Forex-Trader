package com.forex.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ExchangeRateService {
    
    private static final Logger log = LoggerFactory.getLogger(ExchangeRateService.class);
    
    private static final Map<String, Double> RATES = new HashMap<>();
    
    static {
        RATES.put("USD/EUR", 0.92);
        RATES.put("USD/GBP", 0.79);
        RATES.put("USD/JPY", 149.50);
        RATES.put("EUR/USD", 1.09);
        RATES.put("EUR/GBP", 0.86);
        RATES.put("GBP/USD", 1.27);
    }
    
    @Cacheable(value = "exchangeRates")
    public Map<String, Double> fetchRates() {
        log.info("Fetching exchange rates from external API");
        return new HashMap<>(RATES);
    }
    
    @Cacheable(value = "exchangeRate", key = "#pair")
    public Double fetchRate(String pair) {
        log.info("Fetching rate for {}", pair);
        return RATES.getOrDefault(pair, 1.0);
    }
}