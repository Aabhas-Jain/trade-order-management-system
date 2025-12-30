package com.example.orderservice.temporal.worker;

import com.example.orderservice.temporal.activity.impl.*;
import com.example.orderservice.temporal.workflow.TradeOrderWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TemporalWorkerConfig {

    private static final String TASK_QUEUE = "TRADE_ORDER_TASK_QUEUE";

    private final WorkflowClient workflowClient;
    private final OrderValidationActivityImpl validationActivity;
    private final FraudCheckActivityImpl fraudCheckActivity;
    private final OrderExecutionActivityImpl executionActivity;
    private final SettlementActivityImpl settlementActivity;

    public TemporalWorkerConfig(
            WorkflowClient workflowClient,
            OrderValidationActivityImpl validationActivity,
            FraudCheckActivityImpl fraudCheckActivity,
            OrderExecutionActivityImpl executionActivity,
            SettlementActivityImpl settlementActivity) {

        this.workflowClient = workflowClient;
        this.validationActivity = validationActivity;
        this.fraudCheckActivity = fraudCheckActivity;
        this.executionActivity = executionActivity;
        this.settlementActivity = settlementActivity;
    }

    @PostConstruct
    public void startWorker() {
        WorkerFactory factory = WorkerFactory.newInstance(workflowClient);
        Worker worker = factory.newWorker(TASK_QUEUE);

        worker.registerWorkflowImplementationTypes(TradeOrderWorkflowImpl.class);

        worker.registerActivitiesImplementations(
                validationActivity,
                fraudCheckActivity,
                executionActivity,
                settlementActivity
        );

        factory.start();
    }
}