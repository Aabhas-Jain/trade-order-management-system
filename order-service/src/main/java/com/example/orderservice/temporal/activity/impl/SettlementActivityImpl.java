package com.example.orderservice.temporal.activity.impl;

import com.example.orderservice.domain.entity.Order;
import com.example.orderservice.domain.enums.OrderStatus;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.service.OrderStateService;
import com.example.orderservice.temporal.activity.SettlementActivity;
import io.temporal.activity.Activity;
import org.springframework.stereotype.Component;

@Component
public class SettlementActivityImpl implements SettlementActivity {

    private final OrderRepository orderRepository;
    private final OrderStateService stateService;

    public SettlementActivityImpl(OrderRepository orderRepository,
                                  OrderStateService stateService) {
        this.orderRepository = orderRepository;
        this.stateService = stateService;
    }

    @Override
    public void settle(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> Activity.wrap(new RuntimeException("Order not found")));

        stateService.updateStatus(order, OrderStatus.FILLED, "Order settled successfully");
    }
}