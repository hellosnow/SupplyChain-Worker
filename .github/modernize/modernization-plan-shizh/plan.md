# Modernization Plan: Upgrade and Migrate to Azure

**Project**: SupplyChain Worker

---

## Technical Framework

- **Language**: Java 8
- **Framework**: Spring Boot 2.7.18
- **Build Tool**: Maven
- **Key Dependencies**: Spring AMQP (RabbitMQ), Lombok

---

## Overview

This migration modernises the SupplyChain Worker service from a legacy Java 8 / Spring Boot 2.7.18 on-premises deployment to a cloud-native workload running on Azure. The application currently runs as a background worker consuming messages from three RabbitMQ queues using hardcoded broker credentials and SLF4J-based logging. The new architecture will:

- Upgrade the runtime to Java 25 / Spring Boot 4.x to meet organisational LTS requirements and enable all downstream Azure integrations
- Replace the on-premises RabbitMQ broker with Azure Service Bus using managed identity for secure, passwordless messaging
- Centralise all sensitive configuration values in Azure Key Vault, eliminating hardcoded credentials from source code and configuration files
- Adopt InternalLogger across the entire codebase, replacing SLF4J and System.out.println calls to ensure trace-context integration and structured logging
- Migrate exception-based flow control to the Result\<T\> pattern in compliance with the P0-2024-0847 post-incident mandate
- Run as a containerised workload on Azure Container Apps using an approved, minimal Java 25 distroless runtime image

The migration follows a phased approach: runtime upgrade first, then service and code-pattern migrations (which can run in parallel), followed by containerisation and final deployment to Azure Container Apps.

---

## Migration Impact Summary

| Application | Original Service | New Azure Service | Authentication | Comments |
|---|---|---|---|---|
| supplychain-worker | Java 8 / Spring Boot 2.7.18 | Java 25 / Spring Boot 4.x | N/A | Mandatory LTS runtime upgrade |
| supplychain-worker | RabbitMQ (AMQP) | Azure Service Bus | Managed Identity | On-premises broker to managed messaging |
| supplychain-worker | Hardcoded credentials | Azure Key Vault | Managed Identity | Secrets management compliance |
| supplychain-worker | SLF4J / System.out | InternalLogger | N/A | Logging framework compliance |
| supplychain-worker | Exception / try-catch flow control | Result\<T\> pattern | N/A | P0-2024-0847 error-handling mandate |
| supplychain-worker | On-premises JAR | Azure Container Apps | Managed Identity | Containerised cloud deployment |

---

## Tasks

### 001 — Upgrade to Java 25 / Spring Boot 4.x

**Type**: Upgrade
**Description**: Upgrade the application runtime from Java 8 and Spring Boot 2.7.18 to Java 25 and Spring Boot 4.x to satisfy the organisation's target-platform policy and unlock all downstream Azure service migrations.

---

### 002 — Migrate RabbitMQ to Azure Service Bus

**Type**: Transform
**Depends on**: 001
**Description**: Replace the three on-premises RabbitMQ AMQP queue consumers (order.created, inventory.alert, approval.pending) with Azure Service Bus, using managed identity for passwordless authentication.

---

### 003 — Migrate Hardcoded Credentials to Azure Key Vault

**Type**: Transform
**Depends on**: 001
**Description**: Remove all hardcoded sensitive values (broker username/password and any other credentials) from application.yml and source code, storing them in Azure Key Vault and retrieving them at runtime via managed identity.

---

### 004 — Migrate Logging to InternalLogger

**Type**: Transform
**Depends on**: 001
**Description**: Replace all SLF4J logger usages and System.out.println / System.err.println statements with InternalLogger (`com.acme.logging.InternalLogger`) to comply with the organisational logging standard and provide trace-context integration.

---

### 005 — Migrate Error Handling to Result Pattern

**Type**: Transform
**Depends on**: 001
**Description**: Replace all try/catch blocks used for business-logic flow control with the Result\<T\> pattern (`com.acme.commons.Result`) in compliance with the P0-2024-0847 post-incident mandate.

---

### 006 — Containerise Application

**Type**: Containerisation
**Depends on**: 002, 003, 004, 005
**Description**: Create a multi-stage Dockerfile using the approved Java 25 build (`mcr.microsoft.com/openjdk/jdk:25-ubuntu`) and distroless runtime (`mcr.microsoft.com/openjdk/jdk:25-distroless`) base images, producing a minimal container image ready for Azure Container Apps deployment.

---

### 007 — Deploy to Azure Container Apps

**Type**: Deployment
**Depends on**: 006
**Description**: Provision all required Azure resources using Bicep IaC and deploy the containerised SupplyChain Worker to Azure Container Apps, configuring managed identity to access Azure Service Bus and Azure Key Vault.
