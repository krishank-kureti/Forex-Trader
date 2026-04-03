package com.forex.repository;

import com.forex.model.CurrencyPair;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CurrencyPairRepository extends JpaRepository<CurrencyPair, Long> {
    List<CurrencyPair> findByIsActive(Boolean isActive);
    Optional<CurrencyPair> findByBaseCurrencyAndQuoteCurrency(String baseCurrency, String quoteCurrency);
}