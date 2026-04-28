# Modernization Plan: SupplyChain Worker – Upgrade and Azure Migration

**Project**: SupplyChain Worker (`com.acme.scm:supplychain-worker`)

---

## Technical Framework

- **Language**: Java 8
- **Framework**: Spring Boot 2.7.18, Spring Framework 5.x
- **Build Tool**: Maven
- **Database**: N/A (messaging background worker)
- **Key Dependencies**: Spring AMQP, RabbitMQ Client, Lombok, SLF4J (via `@Slf4j`)

---

## Overview

This migration upgrades and replatforms the SupplyChain Worker service to Azure. The
application currently runs on end-of-life Java 8 with Spring Boot 2.7.18, uses RabbitMQ
AMQP for messaging, contains hardcoded credentials in configuration files, and uses the
SLF4J logging framework in violation of internal observability policy. The new architecture
will:

- Upgrade to Java 25 and Spring Boot 4.x to eliminate end-of-life runtimes and address
  mandatory assessment findings
- Replace RabbitMQ AMQP messaging with Azure Service Bus (managed identity) for
  fully managed, cloud-native message handling
- Remove hardcoded credentials from configuration and store them in Azure Key Vault
  accessed via managed identity
- Replace SLF4J logging with `com.acme.logging.InternalLogger` to comply with the
  internal observability policy
- Remediate all CVE vulnerabilities in Maven dependencies
- Containerize the application and deploy to Azure Container Apps (ACA) following the
  Acme Corp replatform strategy

The migration follows a phased approach: upgrade runtime first, transform to Azure
services, harden security, containerize, then deploy.

---

## Migration Impact Summary

| Application          | Original Service              | New Azure Service         | Authentication    | Comments                              |
|----------------------|-------------------------------|---------------------------|-------------------|---------------------------------------|
| supplychain-worker   | Java 8 / Spring Boot 2.7.18   | Java 25 / Spring Boot 4.x | N/A               | Mandatory; runtime EOL per assessment |
| supplychain-worker   | RabbitMQ AMQP (3 queues)      | Azure Service Bus         | Managed Identity  | order-created, inventory-alert, approval-pending queues |
| supplychain-worker   | Hardcoded credentials (yml)   | Azure Key Vault           | Managed Identity  | Remove RabbitMQ username/password     |
| supplychain-worker   | SLF4J (`@Slf4j`)              | InternalLogger            | N/A               | Policy: InternalLogger required       |
| supplychain-worker   | Maven dependencies (CVEs)     | Patched dependencies      | N/A               | Fix all CVE vulnerabilities           |
| supplychain-worker   | JAR deployment                | Azure Container Apps      | Managed Identity  | Replatform strategy per playbook      |

---

## Modernization Tasks

### Task 1 – Upgrade Spring Boot to 4.x (Java 25)

Upgrade the application runtime from Java 8 / Spring Boot 2.7.18 to Java 25 / Spring Boot
4.x, including Spring Framework 7.x and Jakarta EE namespace migration (`javax.*` →
`jakarta.*`). This resolves assessment findings: `azure-java-version-02000`,
`spring-boot-to-azure-spring-boot-version-01000`, and `spring-framework-version-01000`.

### Task 2 – Migrate RabbitMQ AMQP to Azure Service Bus

Replace the Spring AMQP / RabbitMQ messaging layer with Azure Service Bus. Migrate all
three queue consumers (`order-created`, `inventory-alert`, `approval-pending`) to Azure
Service Bus using managed identity authentication. Resolves assessment findings:
`azure-message-queue-amqp-02000` and `azure-message-queue-rabbitmq-01000`.

### Task 3 – Migrate Hardcoded Credentials to Azure Key Vault

Remove hardcoded RabbitMQ credentials from `application.yml` and store them in Azure Key
Vault. Access secrets via managed identity. Resolves assessment finding:
`azure-password-01000`. Required by internal secrets management policy.

### Task 4 – Migrate SLF4J Logging to InternalLogger

Replace all SLF4J usage (including Lombok `@Slf4j`) with `com.acme.logging.InternalLogger`
in all application components. Required by internal observability policy (SLF4J is a
prohibited technology).

### Task 5 – Security: Fix CVE Vulnerabilities

Scan all Maven dependencies for known CVE vulnerabilities and upgrade or replace any
vulnerable dependencies to remediate all critical and high-severity CVEs.

### Task 6 – Containerize Application

Create a production-ready multi-stage Dockerfile using the approved base images
(`mcr.microsoft.com/openjdk/jdk:25-ubuntu` for build,
`mcr.microsoft.com/openjdk/jdk:25-distroless` for runtime) to containerize the
application for ACA deployment.

### Task 7 – Deploy to Azure Container Apps

Deploy the containerized SupplyChain Worker to Azure Container Apps. Provision required
Azure resources (Container Apps environment, Azure Service Bus namespace, Azure Key Vault)
using Bicep and configure managed identity bindings.

---

## Security Compliance

**Description**: Scan and remediate all known CVE vulnerabilities in project dependencies,
ensuring no critical or high-severity vulnerabilities remain before deployment.

**Requirements**: All CVE issues in Maven dependencies must be identified and fixed.
The build and all tests must pass after fixes.

**Environment Configuration**: Java 25, Maven.

**App Scope**: Repository root (`.`)

**Skills**:
  - Skill Name: `validate-cves-and-fix`
    - Skill Location: `builtin`
