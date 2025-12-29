package com.example.orderservice.temporal.activity;

import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface SettlementActivity {
    void settle(Long orderId);
}