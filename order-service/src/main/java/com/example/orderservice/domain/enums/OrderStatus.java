package com.example.orderservice.domain.enums;

public enum OrderStatus {
    PENDING,
    VALIDATING,
    FRAUD_CHECK,
    EXECUTING,
    FILLED,
    REJECTED,
    FAILED
}