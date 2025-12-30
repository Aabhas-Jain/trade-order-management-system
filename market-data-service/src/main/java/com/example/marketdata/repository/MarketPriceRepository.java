package com.example.marketdata.repository;

import com.example.marketdata.domain.entity.MarketPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MarketPriceRepository extends JpaRepository<MarketPrice, Long> {

    Optional<MarketPrice> findBySymbol(String symbol);
}