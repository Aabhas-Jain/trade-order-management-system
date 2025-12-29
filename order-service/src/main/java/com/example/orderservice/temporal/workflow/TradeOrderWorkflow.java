package com.example.orderservice.temporal.workflow;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;
import io.temporal.workflow.QueryMethod;

@WorkflowInterface
public interface TradeOrderWorkflow {

    @WorkflowMethod
    void processOrder(Long orderId);

    @QueryMethod
    String getCurrentStatus();
}