package com.forex.repository;

import com.forex.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByTraderId(Long traderId);
    
    @Query("SELECT o FROM Order o WHERE o.status IN ('PLACED', 'MATCHED') AND o.currencyPairId = :currencyPairId")
    List<Order> findPendingOrdersByCurrencyPair(Long currencyPairId);
    
    List<Order> findByStatus(Order.OrderStatus status);
}