package com.forex.repository;

import com.forex.model.TradeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TradeHistoryRepository extends JpaRepository<TradeHistory, Long> {
    List<TradeHistory> findByTraderIdOrderByTimestampDesc(Long traderId);
    List<TradeHistory> findAllByOrderByTimestampDesc();
}