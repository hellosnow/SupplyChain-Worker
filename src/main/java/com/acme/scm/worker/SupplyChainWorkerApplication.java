package com.acme.scm.worker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Supply Chain Management System - Background Worker
 *
 * TECH DEBT SUMMARY:
 * - Java 17 LTS ✅
 * - Spring Boot 3.x ✅ (migrated from 2.7.18)
 * - Azure Service Bus ✅ (migrated from legacy message broker)
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
