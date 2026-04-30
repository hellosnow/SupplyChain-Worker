# Modernization Plan: Upgrade and Migrate to Azure

**Project**: SupplyChain Worker

**Plan Name**: modernization-plan-shizh

---

## Technical Framework

- **Language**: Java 8
- **Framework**: Spring Boot 2.7.18
- **Build Tool**: Maven 3.8
- **Message Broker**: RabbitMQ (AMQP) — 3 queues: `order.created`, `inventory.alert`, `approval.pending`
- **Key Dependencies**: spring-boot-starter-amqp, Lombok, SLF4J/Logback (via `@Slf4j`)

---

## Overview

This migration upgrades and modernizes the SupplyChain Worker service to run on Azure-native
infrastructure. The application currently runs as a Java 8 / Spring Boot 2.7.18 background
worker that listens to three RabbitMQ queues for order-created, inventory-alert, and
approval-pending events — with hardcoded broker credentials and SLF4J-based logging. The new
architecture will:

- Run on **Java 25 / Spring Boot 4.x** to meet Acme Corp's platform targets and address
  end-of-life versions (Java 8 and Spring Boot 2.x are prohibited per internal policy)
- Replace **RabbitMQ** with **Azure Service Bus** (Managed Identity authentication) for
  cloud-native, scalable messaging
- Store all sensitive configuration in **Azure Key Vault**, eliminating hardcoded credentials
  from `application.yml`
- Use **InternalLogger** (`com.acme.logging.InternalLogger`) as the sole logging framework,
  replacing SLF4J and `System.out.println` per Acme Corp policy
- Be **containerized** using approved Azure OpenJDK 25 images and **deployed to Azure Container
  Apps (ACA)** as the mandated target compute platform per the modernization charter

The migration follows a phased approach: framework upgrade first, then service-by-service
transformation, followed by containerization and deployment to Azure.

---

## Migration Impact Summary

| Application       | Original Service            | New Azure Service             | Authentication   | Comments                              |
|-------------------|-----------------------------|-------------------------------|------------------|---------------------------------------|
| SupplyChain Worker | Java 8 / Spring Boot 2.7.18 | Java 25 / Spring Boot 4.x    | N/A              | End-of-life per Acme Corp policy      |
| SupplyChain Worker | RabbitMQ (AMQP, 3 queues)  | Azure Service Bus (3 queues)  | Managed Identity | order, inventory, approval queues     |
| SupplyChain Worker | Hardcoded credentials       | Azure Key Vault               | Managed Identity | Broker credentials in application.yml |
| SupplyChain Worker | SLF4J / Logback / System.out | InternalLogger               | N/A              | Policy-mandated logging framework     |
| SupplyChain Worker | Docker (Java 8 images)      | Azure Container Apps          | Managed Identity | ACA as default compute per charter    |

---

## Tasks

### Task 1 — Spring Boot 4.x / Java 25 Upgrade

Upgrade the project from Java 8 / Spring Boot 2.7.18 to Java 25 / Spring Boot 4.x to meet
Acme Corp's platform targets. This task is a prerequisite for all subsequent transformation
tasks.

### Task 2 — Migrate RabbitMQ to Azure Service Bus

Replace the Spring AMQP / RabbitMQ messaging layer with Azure Service Bus using Spring Cloud
Azure and Managed Identity authentication. Migrate all three queue listeners
(`order.created`, `inventory.alert`, `approval.pending`) to Azure Service Bus queues.

### Task 3 — Migrate Hardcoded Credentials to Azure Key Vault

Remove hardcoded RabbitMQ credentials from `application.yml` and store them securely in Azure
Key Vault, accessed via Managed Identity using the Spring Cloud Azure Key Vault starter.

### Task 4 — Migrate Logging to InternalLogger

Replace all SLF4J (`@Slf4j`) and `System.out.println` usage with the corporate
`com.acme.logging.InternalLogger` framework as mandated by Acme Corp policy.

### Task 5 — Containerize with Azure OpenJDK 25 Images

Update the Dockerfile to use the approved `mcr.microsoft.com/openjdk/jdk:25-ubuntu` build-stage
image and `mcr.microsoft.com/openjdk/jdk:25-distroless` runtime-stage image.

### Task 6 — Deploy to Azure Container Apps

Deploy the containerized application to Azure Container Apps (ACA), the mandated default
compute platform for all Java services in the supply chain system.
