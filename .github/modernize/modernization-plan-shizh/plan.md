# Modernization Plan: SupplyChain Worker – Azure Migration

**Project**: SupplyChain Worker (`supplychain-worker`)

---

## Technical Framework

- **Language**: Java 8
- **Framework**: Spring Boot 2.7.18, Spring Framework 5.3.x
- **Build Tool**: Maven 3
- **Database**: N/A
- **Key Dependencies**: Spring AMQP (RabbitMQ), Lombok, spring-boot-starter-test

---

## Overview

> This migration modernizes the SupplyChain Worker service from a legacy Java 8 / Spring Boot 2.7.18 stack to Java 25 / Spring Boot 4.x and migrates its messaging and secrets infrastructure to Azure-managed services, then deploys it as a containerized workload on Azure Container Apps (ACA).
>
> The application currently uses RabbitMQ (AMQP) for message-driven order processing, stores broker credentials as plaintext in `application.yml`, and runs on an end-of-life Java runtime with an unsupported Spring Boot version.  The new architecture will:
>
> - **Upgrade the runtime** to Java 25 and Spring Boot 4.x to eliminate end-of-life vulnerabilities and enable modern cloud-native APIs.
> - **Replace RabbitMQ** with Azure Service Bus for fully managed, cloud-native messaging with Managed Identity authentication.
> - **Remove plaintext credentials** from configuration files by migrating secrets to Azure Key Vault accessed via Spring Cloud Azure and Managed Identity.
> - **Remediate all CVE vulnerabilities** found in project dependencies.
> - **Containerize and deploy** the service to Azure Container Apps using official Microsoft OpenJDK 25 distroless images.
>
> The migration follows a phased approach: runtime upgrade first, then Azure service integrations, then security hardening (CVE fix), and finally containerization and deployment.

---

## Migration Impact Summary

| Application           | Original Service         | New Azure Service          | Authentication     | Comments                                     |
|-----------------------|--------------------------|----------------------------|--------------------|----------------------------------------------|
| supplychain-worker    | RabbitMQ (AMQP/Spring)   | Azure Service Bus          | Managed Identity   | Migrate all 3 listener queues                |
| supplychain-worker    | Plaintext in app.yml     | Azure Key Vault            | Managed Identity   | Remove hardcoded RabbitMQ credentials        |
| supplychain-worker    | Local container runtime  | Azure Container Apps (ACA) | Managed Identity   | Containerize with mcr.microsoft.com/openjdk  |

---

## Upgrade Tasks

### Task 1 – Spring Boot 4.x Upgrade (Java 25)

Upgrade the project from Spring Boot 2.7.18 / Java 8 to Spring Boot 4.x / Java 25 / Spring Framework 7.x to address all end-of-life framework and runtime issues identified in the assessment.

---

## Migration Tasks

### Task 2 – Migrate RabbitMQ AMQP Messaging to Azure Service Bus

Migrate all Spring AMQP / RabbitMQ messaging code and configuration to Azure Service Bus using Spring Cloud Azure Service Bus AMQP starter with Managed Identity authentication. Covers the three existing listener queues (`order-created`, `inventory-alert`, `approval-pending`).

### Task 3 – Migrate Plaintext Credentials to Azure Key Vault

Remove hardcoded credentials from `application.yml` and all source files. Store and access sensitive values (broker credentials, connection strings) through Azure Key Vault using Spring Cloud Azure Key Vault starter and Managed Identity.

---

## Security Compliance

**Description**: Validate and fix all CVE vulnerabilities found in project dependencies to ensure the modernized application is free of known security issues before deployment.

**Requirements**: Scan all project dependencies for known CVEs and upgrade or replace affected packages until no CVE issues remain.

**Environment Configuration**: Java 25; Maven build tool set up by the Spring Boot 4.x upgrade task.

**App Scope**: Repository root (`.`)

**Skills**:
- Skill Name: validate-cves-and-fix
  - Skill Location: builtin

---

## Containerization

### Task 5 – Containerize the Application

Create a multi-stage `Dockerfile` using the official Microsoft OpenJDK 25 images to package the application for deployment to Azure Container Apps.

- Build image: `mcr.microsoft.com/openjdk/jdk:25-ubuntu`
- Runtime image: `mcr.microsoft.com/openjdk/jdk:25-distroless`

---

## Deployment

### Task 6 – Deploy to Azure Container Apps

Deploy the containerized `supplychain-worker` to Azure Container Apps using Bicep IaC. Provision or reuse an Azure Container Apps environment and configure the app with Managed Identity to access Azure Service Bus and Azure Key Vault.
