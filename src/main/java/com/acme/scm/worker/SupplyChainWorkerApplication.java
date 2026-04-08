package com.acme.scm.worker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Supply Chain Management System - Background Worker
 *
 * TECH DEBT SUMMARY:
 * - Java 8 (should be Java 17 LTS)
 * - Spring Boot 2.7.18 (should be Spring Boot 3.x)
 * - RabbitMQ 3.6 (should migrate to Azure Service Bus with custom messaging API)
 * - SLF4J logging (should use InternalLogger)
 */
@SpringBootApplication
@EnableScheduling
public class SupplyChainWorkerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SupplyChainWorkerApplication.class, args);
        System.out.println("========================================");
        System.out.println("Supply Chain Worker Started");
        System.out.println("Listening for messages...");
        System.out.println("========================================");
    }
}
