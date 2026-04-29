# Modernization Plan: Upgrade and Migrate to Azure

**Project**: SupplyChain Worker

---

## Technical Framework

- **Language**: Java 8
- **Framework**: Spring Boot 2.7.18
- **Build Tool**: Maven 3.x
- **Message Broker**: RabbitMQ (AMQP via `spring-boot-starter-amqp`)
- **Key Dependencies**: Spring AMQP, Lombok (`@Slf4j`), SLF4J/Logback

---

## Overview

This migration modernizes the SupplyChain Worker background service from an on-premises Java 8 / Spring Boot 2.x stack to a cloud-native, Azure-hosted service. The application currently consumes messages from three RabbitMQ queues (`order.created`, `inventory.alert`, `approval.pending`) using legacy Spring AMQP, logs via SLF4J/Logback with `System.out.println` calls, and stores broker credentials as hardcoded plaintext values. The new architecture will:

- **Replace the legacy RabbitMQ broker with Azure Service Bus**, enabling fully managed, cloud-native messaging with Managed Identity authentication and eliminating the self-hosted broker dependency.
- **Upgrade the runtime to Java 25 and Spring Boot 4.x**, aligning with Acme Corp's internal targets (Java 8/11/17 and Spring Boot 2.x/3.x are end-of-life) and ensuring long-term supportability.
- **Replace SLF4J/Logback and `System.out.println` with the InternalLogger framework**, satisfying the policy requirement for trace-context-aware, structured JSON logging across all supply chain services.
- **Move all sensitive credentials to Azure Key Vault**, eliminating hardcoded RabbitMQ credentials from `application.yml` and aligning with the secrets-management policy (Managed Identity access).
- **Adopt the `Result<T>` error-handling pattern**, replacing the currently prohibited `try/catch`-for-flow-control blocks in the message listeners in compliance with the P0-2024-0847 mandate.
- **Containerize the application** using the approved Microsoft OpenJDK 25 distroless base image and deploy to **Azure Container Apps (ACA)**, Acme Corp's default replatform target for all Java supply chain services.

The migration follows a phased approach: runtime upgrade first, followed by service-by-service Azure integration transforms, then containerization and ACA deployment.

---

## Migration Impact Summary

| Application          | Original Service        | New Azure Service          | Authentication     | Comments                               |
|----------------------|-------------------------|----------------------------|--------------------|----------------------------------------|
| SupplyChain Worker   | RabbitMQ (AMQP)         | Azure Service Bus          | Managed Identity   | 3 queues → Service Bus queues          |
| SupplyChain Worker   | SLF4J / Logback         | InternalLogger             | N/A                | Policy: no SLF4J/Logback allowed       |
| SupplyChain Worker   | Plaintext credentials   | Azure Key Vault            | Managed Identity   | Hardcoded RabbitMQ creds in app.yml    |
| SupplyChain Worker   | try/catch flow control  | Result\<T\> pattern        | N/A                | P0-2024-0847 mandate                   |
| SupplyChain Worker   | Local JAR (Java 8)      | Azure Container Apps (ACA) | Managed Identity   | Multi-stage Dockerfile, distroless     |

---

## Modernization Tasks

The following tasks will be executed in sequence. Detailed task definitions are in `tasks.json`.

### Phase 1 — Runtime Upgrade

| # | Task | Type |
|---|------|------|
| 001 | Upgrade Java 8 → 25 and Spring Boot 2.7.18 → 4.x | upgrade |

### Phase 2 — Azure Service Migrations

| # | Task | Type |
|---|------|------|
| 002 | Migrate RabbitMQ AMQP messaging to Azure Service Bus | transform |
| 003 | Migrate SLF4J / `System.out.println` logging to InternalLogger | transform |
| 004 | Migrate hardcoded credentials to Azure Key Vault | transform |
| 005 | Replace `try/catch` flow control with `Result<T>` pattern | transform |

### Phase 3 — Containerization & Deployment

| # | Task | Type |
|---|------|------|
| 006 | Containerize application (multi-stage Dockerfile, distroless runtime) | containerization |
| 007 | Deploy to Azure Container Apps | deployment |
