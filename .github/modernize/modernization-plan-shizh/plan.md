# Modernization Plan: Upgrade and Migrate to Azure

**Project**: SupplyChain Worker

---

## Technical Framework

- **Language**: Java 8
- **Framework**: Spring Boot 2.7.18
- **Build Tool**: Maven
- **Messaging**: RabbitMQ (Spring AMQP) — 3 queues: order-created, inventory-alert, approval-pending
- **Key Dependencies**: spring-boot-starter-amqp, Lombok (@Slf4j)

---

## Overview

> This migration modernizes the SupplyChain Worker service to run on Azure. The application currently runs on Java 8 and Spring Boot 2.7.18, uses RabbitMQ AMQP for message processing, relies on SLF4J for logging, and stores credentials as plaintext in configuration files. The new architecture will:
>
> - Replace RabbitMQ AMQP messaging with Azure Service Bus, using Managed Identity for passwordless authentication, eliminating on-premises broker dependencies
> - Upgrade the runtime to Java 25 and Spring Boot 4.x to meet company end-of-life targets and enable cloud-native features
> - Replace SLF4J and System.out.println with InternalLogger to comply with the company logging policy and enable trace-context-aware logging
> - Move sensitive configuration values to Azure Key Vault in line with the company secrets management policy
> - Replace exception-based flow control with the Result\<T\> pattern per company coding standards
> - Containerize with up-to-date base images and deploy to Azure Container Apps (ACA) as the standard compute platform
>
> The migration follows a phased approach: runtime upgrade first, then service migrations, then containerization and deployment.

---

## Migration Impact Summary

| Application         | Original Service    | New Azure Service       | Authentication     | Comments                              |
|---------------------|---------------------|-------------------------|--------------------|---------------------------------------|
| SupplyChain Worker  | Java 8              | Java 25                 | N/A                | Includes Spring Boot 4.x upgrade      |
| SupplyChain Worker  | Spring Boot 2.7.18  | Spring Boot 4.x         | N/A                | Jakarta EE namespace migration        |
| SupplyChain Worker  | RabbitMQ AMQP       | Azure Service Bus       | Managed Identity   | 3 queues migrated to SB entities      |
| SupplyChain Worker  | SLF4J / println     | InternalLogger          | N/A                | Company logging policy compliance     |
| SupplyChain Worker  | Plaintext creds     | Azure Key Vault         | Managed Identity   | Secrets policy compliance             |
| SupplyChain Worker  | try/catch control   | Result\<T\> pattern     | N/A                | P0-2024-0847 coding standard          |
| SupplyChain Worker  | Dockerfile (Java 8) | Dockerfile (Java 25)    | N/A                | Multi-stage, distroless runtime image |
| SupplyChain Worker  | On-premises deploy  | Azure Container Apps    | Managed Identity   | Default compute per charter           |

---

## Migration Tasks

| # | Task | Type | Skill |
|---|------|------|-------|
| 001 | Upgrade to Java 25 and Spring Boot 4.x | upgrade | — |
| 002 | Migrate RabbitMQ AMQP to Azure Service Bus | transform | migration-amqp-rabbitmq-servicebus |
| 003 | Migrate logging to InternalLogger | transform | — |
| 004 | Migrate plaintext credentials to Azure Key Vault | transform | migration-plaintext-credential-to-azure-keyvault |
| 005 | Migrate exception-based flow control to Result\<T\> pattern | transform | — |
| 006 | Containerize with Java 25 base images | containerization | — |
| 007 | Deploy to Azure Container Apps | deployment | — |
