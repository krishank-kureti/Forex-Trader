package com.forex.config;

import com.forex.model.CurrencyPair;
import com.forex.repository.CurrencyPairRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    private final CurrencyPairRepository currencyPairRepository;
    
    public DataInitializer(CurrencyPairRepository currencyPairRepository) {
        this.currencyPairRepository = currencyPairRepository;
    }
    
    @Override
    public void run(String... args) {
        if (currencyPairRepository.count() == 0) {
            CurrencyPair pair1 = new CurrencyPair();
            pair1.setBaseCurrency("USD");
            pair1.setQuoteCurrency("EUR");
            pair1.setIsActive(true);
            currencyPairRepository.save(pair1);
            
            CurrencyPair pair2 = new CurrencyPair();
            pair2.setBaseCurrency("USD");
            pair2.setQuoteCurrency("GBP");
            pair2.setIsActive(true);
            currencyPairRepository.save(pair2);
            
            CurrencyPair pair3 = new CurrencyPair();
            pair3.setBaseCurrency("USD");
            pair3.setQuoteCurrency("JPY");
            pair3.setIsActive(true);
            currencyPairRepository.save(pair3);
            
            CurrencyPair pair4 = new CurrencyPair();
            pair4.setBaseCurrency("EUR");
            pair4.setQuoteCurrency("USD");
            pair4.setIsActive(true);
            currencyPairRepository.save(pair4);
            
            CurrencyPair pair5 = new CurrencyPair();
            pair5.setBaseCurrency("GBP");
            pair5.setQuoteCurrency("USD");
            pair5.setIsActive(true);
            currencyPairRepository.save(pair5);
        }
    }
}