package com.forex.controller;

import com.forex.dto.OrderRequest;
import com.forex.dto.OrderResponse;
import com.forex.model.Order;
import com.forex.service.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trades")
public class TradeController {
    
    private final TradeService tradeService;
    
    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }
    
    @PostMapping("/place")
    public ResponseEntity<OrderResponse> placeOrder(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody OrderRequest request) {
        Long traderId = 1L;
        return ResponseEntity.ok(tradeService.placeOrder(request, traderId));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long id) {
        tradeService.cancelOrder(id);
        return ResponseEntity.ok("Order cancelled successfully");
    }
    
    @GetMapping("/history")
    public ResponseEntity<List<Order>> getHistory(@AuthenticationPrincipal UserDetails userDetails) {
        Long traderId = 1L;
        return ResponseEntity.ok(tradeService.getHistory(traderId));
    }
}