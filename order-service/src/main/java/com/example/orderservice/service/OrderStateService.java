package com.example.orderservice.service;

import com.example.orderservice.domain.entity.Order;
import com.example.orderservice.domain.entity.OrderHistory;
import com.example.orderservice.domain.enums.OrderStatus;
import com.example.orderservice.repository.OrderHistoryRepository;
import com.example.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderStateService {

    private final OrderRepository orderRepository;
    private final OrderHistoryRepository historyRepository;

    public OrderStateService(OrderRepository orderRepository,
                             OrderHistoryRepository historyRepository) {
        this.orderRepository = orderRepository;
        this.historyRepository = historyRepository;
    }

    @Transactional
    public Order updateStatus(Order order, OrderStatus newStatus, String reason) {
        order.setStatus(newStatus);
        Order updatedOrder = orderRepository.save(order);

        OrderHistory history = new OrderHistory();
        history.setOrderId(updatedOrder.getOrderId());
        history.setStatus(newStatus);
        history.setReason(reason);
        historyRepository.save(history);

        return updatedOrder;
    }
}