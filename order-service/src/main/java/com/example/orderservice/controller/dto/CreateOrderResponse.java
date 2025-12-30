package com.example.orderservice.controller.dto;

import java.time.Instant;

public class CreateOrderResponse {
    public Long orderId;
    public String status;
    public Instant createdAt;
    public String workflowId;
}