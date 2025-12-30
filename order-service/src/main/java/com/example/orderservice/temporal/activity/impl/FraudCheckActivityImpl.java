package com.example.orderservice.temporal.activity.impl;

import com.example.orderservice.domain.entity.Order;
import com.example.orderservice.domain.enums.OrderStatus;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.service.OrderStateService;
import com.example.orderservice.temporal.activity.FraudCheckActivity;
import io.temporal.activity.Activity;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class FraudCheckActivityImpl implements FraudCheckActivity {

    private final OrderRepository orderRepository;
    private final OrderStateService stateService;
    private final Random random = new Random();

    public FraudCheckActivityImpl(OrderRepository orderRepository,
                                  OrderStateService stateService) {
        this.orderRepository = orderRepository;
        this.stateService = stateService;
    }

    @Override
    public void check(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> Activity.wrap(new RuntimeException("Order not found")));

        stateService.updateStatus(order, OrderStatus.FRAUD_CHECK, "Fraud check started");

        try {
            Thread.sleep(500 + random.nextInt(1500));
        } catch (InterruptedException ignored) {}

        if (random.nextInt(10) < 2) {
            throw Activity.wrap(new RuntimeException("Fraud detected"));
        }
    }
}