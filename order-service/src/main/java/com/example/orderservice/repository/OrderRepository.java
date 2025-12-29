package com.example.orderservice.repository;

import com.example.orderservice.domain.entity.Order;
import com.example.orderservice.domain.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findByStatus(OrderStatus status, Pageable pageable);

    Page<Order> findBySymbol(String symbol, Pageable pageable);

    Page<Order> findByStatusAndSymbol(OrderStatus status, String symbol, Pageable pageable);

    Page<Order> findByUserId(Long userId, Pageable pageable);
}