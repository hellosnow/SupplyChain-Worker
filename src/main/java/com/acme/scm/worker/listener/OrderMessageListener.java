package com.acme.scm.worker.listener;

import com.azure.spring.messaging.implementation.annotation.EnableAzureMessaging;
import com.azure.spring.messaging.servicebus.implementation.core.annotation.ServiceBusListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * TECH DEBT:
 * - Uses SLF4J instead of InternalLogger
 */
@Slf4j
@Component
@EnableAzureMessaging
public class OrderMessageListener {

    @ServiceBusListener(destination = "${app.messaging.queue.order-created}")
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

    @ServiceBusListener(destination = "${app.messaging.queue.inventory-alert}")
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

    @ServiceBusListener(destination = "${app.messaging.queue.approval-pending}")
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
