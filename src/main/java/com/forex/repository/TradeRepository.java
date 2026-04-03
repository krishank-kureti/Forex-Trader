package com.forex.repository;

import com.forex.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TradeRepository extends JpaRepository<Trade, Long> {
    List<Trade> findByBuyOrderIdOrSellOrderId(Long buyOrderId, Long sellOrderId);
    List<Trade> findAllByOrderByExecutedAtDesc();
}