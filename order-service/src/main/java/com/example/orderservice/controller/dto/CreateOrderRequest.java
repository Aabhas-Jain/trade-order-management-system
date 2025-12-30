package com.example.orderservice.controller.dto;

import com.example.orderservice.domain.enums.OrderSide;
import com.example.orderservice.domain.enums.OrderType;

import java.math.BigDecimal;

public class CreateOrderRequest {
    public String symbol;
    public OrderSide side;
    public Integer quantity;
    public OrderType orderType;
    public BigDecimal limitPrice;
}