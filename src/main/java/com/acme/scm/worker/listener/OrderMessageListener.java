package com.acme.scm.worker.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TECH DEBT:
 * - Uses RabbitMQ (should migrate to Azure Service Bus with custom messaging API)
 * - Uses SLF4J instead of InternalLogger
 */
@Component
public class OrderMessageListener {

    private static final Logger log = LoggerFactory.getLogger(OrderMessageListener.class);

    @RabbitListener(queues = "${app.messaging.queue.order-created}")
    public void handleOrderCreated(String message) {
        log.info("📬 Received order created notification");
        log.info("Message: {}", message);

        // Simulate processing
        try {
            log.info("Processing order notification...");
            Thread.sleep(1000);
            log.info("✅ Order notification email sent successfully");
        } catch (Exception e) {
            log.error("Failed to process order notification", e);
        }
    }

    @RabbitListener(queues = "${app.messaging.queue.inventory-alert}")
    public void handleInventoryAlert(String message) {
        log.warn("⚠️  Received inventory alert");
        log.warn("Message: {}", message);

        try {
            log.warn("Processing low stock alert...");
            Thread.sleep(500);
            log.warn("✅ Low stock alert notification sent to procurement team");
        } catch (Exception e) {
            log.error("Failed to process inventory alert", e);
        }
    }

    @RabbitListener(queues = "${app.messaging.queue.approval-pending}")
    public void handleApprovalPending(String message) {
        log.info("⏳ Received approval pending notification");
        log.info("Message: {}", message);

        try {
            log.info("Sending approval request to manager...");
            Thread.sleep(800);
            log.info("✅ Approval request notification sent");
        } catch (Exception e) {
            log.error("Failed to process approval notification", e);
        }
    }
}
