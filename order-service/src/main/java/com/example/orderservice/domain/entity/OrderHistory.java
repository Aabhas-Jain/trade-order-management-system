package com.example.orderservice.domain.entity;

import com.example.orderservice.domain.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "order_history")
public class OrderHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private String reason;

    private Instant changedAt;

    @PrePersist
    public void onCreate() {
        this.changedAt = Instant.now();
    }
}