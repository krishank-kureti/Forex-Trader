package com.forex.service;

import com.forex.model.TradeHistory;
import com.forex.repository.TradeHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeHistoryService {
    
    private final TradeHistoryRepository tradeHistoryRepository;
    
    public TradeHistoryService(TradeHistoryRepository tradeHistoryRepository) {
        this.tradeHistoryRepository = tradeHistoryRepository;
    }
    
    public List<TradeHistory> getHistory(Long traderId) {
        return tradeHistoryRepository.findByTraderIdOrderByTimestampDesc(traderId);
    }
    
    public List<TradeHistory> getAllHistory() {
        return tradeHistoryRepository.findAllByOrderByTimestampDesc();
    }
}