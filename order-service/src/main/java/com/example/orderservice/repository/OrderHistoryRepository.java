package com.example.orderservice.repository;

import com.example.orderservice.domain.entity.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {

    List<OrderHistory> findByOrderIdOrderByChangedAtAsc(Long orderId);
}