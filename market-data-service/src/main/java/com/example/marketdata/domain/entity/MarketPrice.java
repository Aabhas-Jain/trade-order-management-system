package com.example.marketdata.domain.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "market_price")
public class MarketPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String symbol;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Instant updatedAt;

    public Long getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}