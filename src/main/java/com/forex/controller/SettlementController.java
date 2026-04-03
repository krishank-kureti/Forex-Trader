package com.forex.controller;

import com.forex.dto.SettlementResponse;
import com.forex.dto.TradeHistoryResponse;
import com.forex.model.Settlement;
import com.forex.model.TradeHistory;
import com.forex.service.SettlementService;
import com.forex.service.TradeHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/settlement")
public class SettlementController {
    
    private final SettlementService settlementService;
    private final TradeHistoryService tradeHistoryService;
    
    public SettlementController(SettlementService settlementService, TradeHistoryService tradeHistoryService) {
        this.settlementService = settlementService;
        this.tradeHistoryService = tradeHistoryService;
    }
    
    @GetMapping("/{tradeId}")
    public ResponseEntity<SettlementResponse> getSettlement(@PathVariable Long tradeId) {
        Settlement settlement = settlementService.getSettlement(tradeId);
        if (settlement == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(new SettlementResponse(
                settlement.getId(),
                settlement.getTradeId(),
                settlement.getSettlementAmount(),
                settlement.getFee(),
                settlement.getStatus().name()
        ));
    }
    
    @GetMapping("/history")
    public ResponseEntity<List<TradeHistoryResponse>> getHistory() {
        return ResponseEntity.ok(tradeHistoryService.getAllHistory().stream()
                .map(h -> new TradeHistoryResponse(
                        h.getId(),
                        h.getTradeId(),
                        h.getType().name(),
                        h.getCurrencyPair(),
                        h.getAmount(),
                        h.getPrice(),
                        h.getTimestamp(),
                        h.getPnl()
                ))
                .collect(Collectors.toList()));
    }
}