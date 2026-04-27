# Modernization Plan: SupplyChain Worker Azure Modernization

**Project**: supplychain-worker

---

## Technical Framework

- **Language**: Java 8
- **Framework**: Spring Boot 2.7.18, Spring Framework 5.x
- **Build Tool**: Maven
- **Database**: N/A
- **Key Dependencies**: Spring AMQP (`spring-boot-starter-amqp`), Lombok, SLF4J (via Lombok `@Slf4j`)

---

## Overview

This migration upgrades and modernizes the SupplyChain Worker for Azure deployment. The application currently runs on Java 8 with Spring Boot 2.7.18 (both end-of-life), uses RabbitMQ for messaging via Spring AMQP, relies on SLF4J/Logback for logging, and stores credentials in plaintext configuration files. The new architecture will:

- Upgrade to Java 25 and Spring Boot 4.x for a modern, supported runtime with enhanced performance and security
- Replace RabbitMQ messaging with Azure Service Bus for managed, cloud-native messaging with Managed Identity authentication
- Migrate hardcoded credentials to Azure Key Vault for centralized secure secret management
- Replace SLF4J and `System.out.println` with InternalLogger (`com.acme.logging.InternalLogger`) for trace-context-aware structured logging
- Fix all CVE vulnerabilities in project dependencies to meet security requirements
- Deploy as a containerized workload to Azure Container Apps

The migration follows a phased approach: runtime upgrade first, then service migrations, security hardening, containerization, and finally deployment to Azure Container Apps.

---

## Migration Impact Summary

| Application        | Original Service            | New Azure Service         | Authentication   | Comments                               |
|--------------------|-----------------------------|---------------------------|------------------|----------------------------------------|
| supplychain-worker | Java 8 / Spring Boot 2.7.18 | Java 25 / Spring Boot 4.x | N/A              | Full framework upgrade required        |
| supplychain-worker | RabbitMQ 3.x (Spring AMQP)  | Azure Service Bus         | Managed Identity | Migrate 3 queues to Service Bus        |
| supplychain-worker | Hardcoded credentials        | Azure Key Vault           | Managed Identity | Move credentials from application.yml  |
| supplychain-worker | SLF4J / System.out.println  | InternalLogger            | N/A              | Replace all logging with InternalLogger|

---

## Upgrade Tasks

### Task 001: Upgrade Spring Boot to 4.x (Java 25)

Upgrade the application from Spring Boot 2.7.18 / Java 8 to Spring Boot 4.x / Java 25. This includes upgrading Spring Framework from 5.x to 7.x and migrating from `javax.*` to `jakarta.*` namespaces. All Spring Boot dependencies will be updated to versions compatible with Spring Boot 4.x.

---

## Migration Tasks

### Task 002: Migrate RabbitMQ to Azure Service Bus

Migrate Spring AMQP RabbitMQ messaging to Azure Service Bus using Spring Messaging with Managed Identity authentication. Replace the `spring-boot-starter-amqp` dependency with Spring Cloud Azure Service Bus. Migrate all three queue listeners (`order-created`, `inventory-alert`, `approval-pending`) to Azure Service Bus queues or topics.

### Task 003: Migrate Hardcoded Credentials to Azure Key Vault

Migrate hardcoded RabbitMQ credentials and other sensitive configuration values from `application.yml` to Azure Key Vault. Configure the application to retrieve secrets from Azure Key Vault using Spring Cloud Azure Key Vault starter with Managed Identity.

### Task 004: Migrate SLF4J Logging to InternalLogger

Replace all SLF4J usage (Lombok `@Slf4j`, `LoggerFactory`) and `System.out.println` / `System.err.println` statements with InternalLogger (`com.acme.logging.InternalLogger`) as required by the Acme Corp Modernization Playbook. InternalLogger provides trace-context injection, team tags, and structured JSON output.

---

## Security Compliance

**Description**: Validate and fix all CVE vulnerabilities in project dependencies to meet the security requirements of the Azure migration.

**Requirements**: Scan all project dependencies for known CVE vulnerabilities. Fix all identified CVEs by upgrading affected dependencies to patched versions. Ensure no high or critical severity CVEs remain in the dependency tree after remediation.

**Environment Configuration**: Java 25, Maven 3.9+

**App Scope**: `./` (repository root)

**Skills**:
  - Skill Name: validate-cves-and-fix
    - Skill Location: builtin

---

## Containerization

Containerize the supplychain-worker application using a multi-stage Dockerfile. The build stage uses `mcr.microsoft.com/openjdk/jdk:25-ubuntu` and the runtime stage uses `mcr.microsoft.com/openjdk/jdk:25-distroless`, as specified in the Acme Corp Modernization Playbook targets. An existing `Dockerfile` is present at the repository root and will be updated to reflect the target base images and build configuration.

---

## Deployment

Deploy the containerized supplychain-worker to Azure Container Apps (ACA). Provision the required Azure resources—including an Azure Container Apps environment, Azure Service Bus namespace with queues, and Azure Key Vault—using Bicep IaC. Configure Managed Identity for all service-to-service authentication and set up appropriate scaling rules for queue-based message processing.
