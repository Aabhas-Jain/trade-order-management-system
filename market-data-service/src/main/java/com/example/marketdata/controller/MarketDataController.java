package com.example.marketdata.controller;

import com.example.marketdata.domain.entity.MarketPrice;
import com.example.marketdata.repository.MarketPriceRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/market")
public class MarketDataController {

    private final MarketPriceRepository repository;

    public MarketDataController(MarketPriceRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/price/{symbol}")
    public MarketPrice getPrice(@PathVariable String symbol) {
        return repository.findBySymbol(symbol.toUpperCase())
                .orElseThrow(() -> new RuntimeException("Symbol not found"));
    }

    @GetMapping("/status")
    public String marketStatus() {
        return "OPEN";
    }
}