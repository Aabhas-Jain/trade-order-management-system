package com.example.marketdata.service;

import com.example.marketdata.domain.entity.MarketPrice;
import com.example.marketdata.repository.MarketPriceRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Random;

@Component
public class MarketPriceScheduler {

    private final MarketPriceRepository repository;
    private final Random random = new Random();

    private static final List<String> SYMBOLS =
            List.of("AAPL", "GOOG", "MSFT", "AMZN");

    public MarketPriceScheduler(MarketPriceRepository repository) {
        this.repository = repository;
    }

    @Scheduled(fixedRate = 10000) // every 10 seconds
    public void updatePrices() {
        for (String symbol : SYMBOLS) {

            BigDecimal price = BigDecimal.valueOf(100 + random.nextInt(200));

            MarketPrice marketPrice = repository.findBySymbol(symbol)
                    .orElseGet(() -> {
                        MarketPrice mp = new MarketPrice();
                        mp.setSymbol(symbol);
                        return mp;
                    });

            marketPrice.setPrice(price);
            marketPrice.setUpdatedAt(Instant.now());

            repository.save(marketPrice);
        }
    }
}