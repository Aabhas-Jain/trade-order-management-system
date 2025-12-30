package com.example.orderservice.temporal.workflow;

import com.example.orderservice.temporal.activity.*;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;

import java.time.Duration;

public class TradeOrderWorkflowImpl implements TradeOrderWorkflow {

    private String currentStatus = "PENDING";

    private final ActivityOptions defaultOptions =
            ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofSeconds(30))
                    .setRetryOptions(
                            RetryOptions.newBuilder()
                                    .setInitialInterval(Duration.ofSeconds(1))
                                    .setMaximumAttempts(3)
                                    .build()
                    )
                    .build();

    private final OrderValidationActivity validationActivity =
            Workflow.newActivityStub(OrderValidationActivity.class, defaultOptions);

    private final FraudCheckActivity fraudCheckActivity =
            Workflow.newActivityStub(FraudCheckActivity.class, defaultOptions);

    private final OrderExecutionActivity executionActivity =
            Workflow.newActivityStub(OrderExecutionActivity.class,
                    ActivityOptions.newBuilder()
                            .setStartToCloseTimeout(Duration.ofSeconds(60))
                            .setRetryOptions(
                                    RetryOptions.newBuilder()
                                            .setMaximumAttempts(5)
                                            .build()
                            )
                            .build());

    private final SettlementActivity settlementActivity =
            Workflow.newActivityStub(SettlementActivity.class,
                    ActivityOptions.newBuilder()
                            .setStartToCloseTimeout(Duration.ofSeconds(30))
                            .setRetryOptions(
                                    RetryOptions.newBuilder()
                                            .setMaximumAttempts(1) // non-retryable
                                            .build()
                            )
                            .build());

    @Override
    public void processOrder(Long orderId) {
        currentStatus = "VALIDATING";
        validationActivity.validate(orderId);

        currentStatus = "FRAUD_CHECK";
        fraudCheckActivity.check(orderId);

        currentStatus = "EXECUTING";
        executionActivity.execute(orderId);

        currentStatus = "SETTLING";
        settlementActivity.settle(orderId);

        currentStatus = "COMPLETED";
    }

    @Override
    public String getCurrentStatus() {
        return currentStatus;
    }
}