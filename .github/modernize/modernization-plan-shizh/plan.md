# Modernization Plan: Upgrade and Migrate to Azure

**Project**: SupplyChain Worker

---

## Technical Framework

- **Language**: Java 8
- **Framework**: Spring Boot 2.7.18 / Spring Framework 5.x
- **Build Tool**: Maven 3.8
- **Database**: N/A
- **Key Dependencies**: Spring AMQP (RabbitMQ), Lombok (SLF4J via @Slf4j)

---

## Overview

> This migration modernizes the SupplyChain Worker service from a legacy Java 8 / Spring Boot 2.7.18 background worker into a cloud-native Azure-ready application. The application currently uses RabbitMQ over AMQP for message consumption, SLF4J for logging, and stores RabbitMQ credentials in plaintext configuration. The new architecture will:
>
> - Replace RabbitMQ AMQP messaging with Azure Service Bus, enabling fully managed, scalable cloud messaging with Managed Identity authentication
> - Upgrade the runtime to Java 25 and Spring Boot 4.x, eliminating end-of-life versions and aligning with the internal target platform
> - Replace SLF4J and System.out logging with InternalLogger to ensure trace-context-aware structured logging across all services
> - Remove hardcoded credentials and store secrets in Azure Key Vault, accessed via Managed Identity
> - Containerize the application with the approved Microsoft OpenJDK distroless base image and deploy to Azure Container Apps (ACA)
>
> The migration follows the Acme Corp Modernization Playbook replatform strategy, targeting Azure Container Apps as the default compute host for all Java supply chain services.

---

## Migration Impact Summary

```
| Application         | Original Service             | New Azure Service           | Authentication     | Comments                                  |
|---------------------|------------------------------|-----------------------------|--------------------|-------------------------------------------|
| SupplyChain Worker  | Java 8 / Spring Boot 2.7.18  | Java 25 / Spring Boot 4.x   | N/A                | Mandatory runtime upgrade per targets.md  |
| SupplyChain Worker  | RabbitMQ (Spring AMQP)       | Azure Service Bus           | Managed Identity   | Migrate AMQP messaging to cloud-managed   |
| SupplyChain Worker  | Hardcoded RabbitMQ creds     | Azure Key Vault             | Managed Identity   | Remove plaintext credentials per policy   |
| SupplyChain Worker  | SLF4J / System.out.println   | InternalLogger              | N/A                | Required by policies.md logging standard  |
| SupplyChain Worker  | Local JAR / Docker (Java 8)  | Azure Container Apps        | Managed Identity   | Replatform to ACA per charter strategy    |
```

---

## Upgrade Tasks

### Task 001 — Spring Boot 4.x Upgrade
Upgrade the application runtime from Java 8 / Spring Boot 2.7.18 to Java 25 / Spring Boot 4.x, including Spring Framework 7.x and Jakarta EE namespace migration (javax.* → jakarta.*).

---

## Transform Tasks

### Task 002 — Migrate RabbitMQ AMQP to Azure Service Bus
Replace Spring AMQP RabbitMQ messaging with Azure Service Bus using Spring Messaging. Update dependencies, connection configuration, and all message listener classes to use Azure Service Bus with Managed Identity authentication.

### Task 003 — Migrate Hardcoded Credentials to Azure Key Vault
Remove hardcoded plaintext RabbitMQ credentials from `application.yml` and migrate to Azure Key Vault for secure secret storage and retrieval via Managed Identity.

### Task 004 — Migrate Logging to InternalLogger
Replace all SLF4J (`@Slf4j`) usage and `System.out.println` / `System.err.println` statements with `com.acme.logging.InternalLogger` as required by the Acme Corp logging policy for trace-context-aware structured logging.

---

## Containerization Tasks

### Task 005 — Containerize Application for Azure Container Apps
Update the existing Dockerfile to use the approved Microsoft OpenJDK base images (`mcr.microsoft.com/openjdk/jdk:25-ubuntu` for build stage, `mcr.microsoft.com/openjdk/jdk:25-distroless` for runtime stage) and prepare the container image for deployment to Azure Container Apps.

---

## Deployment Tasks

### Task 006 — Deploy to Azure Container Apps
Deploy the containerized SupplyChain Worker to Azure Container Apps with Managed Identity configured for access to Azure Service Bus and Azure Key Vault.
