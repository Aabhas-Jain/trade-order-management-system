package com.example.orderservice.service;

import com.example.orderservice.controller.dto.CreateOrderRequest;
import com.example.orderservice.domain.entity.Order;
import com.example.orderservice.domain.enums.OrderStatus;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.temporal.workflow.TradeOrderWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import org.springframework.stereotype.Service;

@Service
public class OrderApplicationService {

    private static final String TASK_QUEUE = "TRADE_ORDER_TASK_QUEUE";

    private final OrderRepository orderRepository;
    private final WorkflowClient workflowClient;

    public OrderApplicationService(OrderRepository orderRepository,
                                   WorkflowClient workflowClient) {
        this.orderRepository = orderRepository;
        this.workflowClient = workflowClient;
    }

    public Order createOrder(CreateOrderRequest request, Long userId) {
        Order order = new Order();
        order.setUserId(userId);
        order.setSymbol(request.symbol);
        order.setSide(request.side);
        order.setQuantity(request.quantity);
        order.setOrderType(request.orderType);
        order.setLimitPrice(request.limitPrice);
        order.setStatus(OrderStatus.PENDING);

        Order saved = orderRepository.save(order);

        WorkflowOptions options = WorkflowOptions.newBuilder()
                .setTaskQueue(TASK_QUEUE)
                .setWorkflowId("ORDER_" + saved.getOrderId())
                .build();

        TradeOrderWorkflow workflow =
                workflowClient.newWorkflowStub(TradeOrderWorkflow.class, options);

        WorkflowClient.start(workflow::processOrder, saved.getOrderId());

        saved.setWorkflowId(options.getWorkflowId());
        return orderRepository.save(saved);
    }
}