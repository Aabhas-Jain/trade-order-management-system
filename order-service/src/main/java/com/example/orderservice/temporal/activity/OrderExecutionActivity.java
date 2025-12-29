package com.example.orderservice.temporal.activity;

import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface OrderExecutionActivity {
    void execute(Long orderId);
}