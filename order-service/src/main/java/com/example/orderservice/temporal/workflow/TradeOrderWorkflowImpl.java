package com.example.orderservice.temporal.workflow;

public class TradeOrderWorkflowImpl implements TradeOrderWorkflow {

    private String currentStatus = "PENDING";

    @Override
    public void processOrder(Long orderId) {
        currentStatus = "WORKFLOW_STARTED";

        // Activities will add later
    }

    @Override
    public String getCurrentStatus() {
        return currentStatus;
    }
}