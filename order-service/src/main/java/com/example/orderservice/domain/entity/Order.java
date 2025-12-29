package com.example.orderservice.domain.entity;

import com.example.orderservice.domain.enums.OrderSide;
import com.example.orderservice.domain.enums.OrderStatus;
import com.example.orderservice.domain.enums.OrderType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private Long userId;

    private String symbol;

    @Enumerated(EnumType.STRING)
    private OrderSide side;

    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    private BigDecimal limitPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Instant createdAt;
    private Instant updatedAt;

    private String workflowId;

    @PrePersist
    public void onCreate() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        this.status = OrderStatus.PENDING;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = Instant.now();
    }
}