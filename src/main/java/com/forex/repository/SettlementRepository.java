package com.forex.repository;

import com.forex.model.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SettlementRepository extends JpaRepository<Settlement, Long> {
    Optional<Settlement> findByTradeId(Long tradeId);
}