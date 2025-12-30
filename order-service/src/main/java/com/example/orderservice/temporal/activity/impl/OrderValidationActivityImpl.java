package com.example.orderservice.temporal.activity.impl;

import com.example.orderservice.domain.entity.Order;
import com.example.orderservice.domain.enums.OrderStatus;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.service.OrderStateService;
import com.example.orderservice.temporal.activity.OrderValidationActivity;
import io.temporal.activity.Activity;
import org.springframework.stereotype.Component;

@Component
public class OrderValidationActivityImpl implements OrderValidationActivity {

    private final OrderRepository orderRepository;
    private final OrderStateService stateService;

    public OrderValidationActivityImpl(OrderRepository orderRepository,
                                       OrderStateService stateService) {
        this.orderRepository = orderRepository;
        this.stateService = stateService;
    }

    @Override
    public void validate(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> Activity.wrap(new RuntimeException("Order not found")));

        stateService.updateStatus(order, OrderStatus.VALIDATING, "Validating order");

        if (order.getQuantity() <= 0) {
            throw Activity.wrap(new IllegalArgumentException("Invalid quantity"));
        }

        if (order.getOrderType().name().equals("LIMIT") && order.getLimitPrice() == null) {
            throw Activity.wrap(new IllegalArgumentException("Limit price required"));
        }
    }
}