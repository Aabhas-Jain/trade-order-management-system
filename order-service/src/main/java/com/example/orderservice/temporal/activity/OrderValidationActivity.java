package com.example.orderservice.temporal.activity;

import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface OrderValidationActivity {
    void validate(Long orderId);
}