package com.example.orderservice.controller;

import com.example.orderservice.controller.dto.CreateOrderRequest;
import com.example.orderservice.controller.dto.CreateOrderResponse;
import com.example.orderservice.domain.entity.Order;
import com.example.orderservice.repository.OrderHistoryRepository;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.service.OrderApplicationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderApplicationService orderService;
    private final OrderRepository orderRepository;
    private final OrderHistoryRepository historyRepository;

    public OrderController(OrderApplicationService orderService,
                           OrderRepository orderRepository,
                           OrderHistoryRepository historyRepository) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
        this.historyRepository = historyRepository;
    }

    @PostMapping
    public CreateOrderResponse create(@RequestBody CreateOrderRequest request) {
        // TEMP userId (JWT comes later)
        Long userId = 1L;

        Order order = orderService.createOrder(request, userId);

        CreateOrderResponse response = new CreateOrderResponse();
        response.orderId = order.getOrderId();
        response.status = order.getStatus().name();
        response.createdAt = order.getCreatedAt();
        response.workflowId = order.getWorkflowId();

        return response;
    }

    @GetMapping("/{orderId}")
    public Order getOrder(@PathVariable Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @GetMapping
    public Page<Order> getOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @GetMapping("/{orderId}/history")
    public Object getHistory(@PathVariable Long orderId) {
        return historyRepository.findByOrderIdOrderByChangedAtAsc(orderId);
    }
}