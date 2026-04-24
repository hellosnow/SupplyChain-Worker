# Modernization Plan: SupplyChain Worker Migration to Azure

**Project**: SupplyChain Worker (supplychain-worker)

---

## Technical Framework

- **Language**: Java 8
- **Framework**: Spring Boot 2.7.18, Spring Framework 5.x
- **Build Tool**: Maven 3.x
- **Key Dependencies**: Spring AMQP (RabbitMQ), Lombok
- **Messaging**: RabbitMQ via Spring AMQP (`spring-boot-starter-amqp`)

---

## Overview

This migration modernizes the SupplyChain Worker service from a legacy Java 8 / Spring Boot
2.7.18 application to a cloud-native Azure-ready service running on Azure Container Apps.
The application currently uses RabbitMQ for messaging, hardcoded credentials in configuration
files, SLF4J for logging, and exception-based error-handling patterns. The new architecture
will:

- Upgrade the runtime from Java 8 / Spring Boot 2.x to Java 21 / Spring Boot 3.x to address
  end-of-life frameworks and enable Jakarta EE compatibility (a subsequent upgrade to Spring
  Boot 4.0+ / Java 25 per the organizational target may require further steps — see
  Clarifications).
- Replace RabbitMQ with Azure Service Bus authenticated via Managed Identity, eliminating
  the self-managed broker dependency.
- Move all sensitive credentials to Azure Key Vault and remove hardcoded values from
  configuration files, satisfying both the assessment findings and playbook security policy.
- Replace SLF4J (`@Slf4j`) with InternalLogger (`com.acme.logging.InternalLogger`) per
  Acme Corp coding standards, ensuring trace-context-aware structured logging.
- Replace exception-based flow control (`try/catch` for business logic) with the
  `Result<T>` pattern (`com.acme.commons.Result`) per the P0-2024-0847 mandate.
- Scan and remediate all known CVE vulnerabilities in project dependencies.
- Containerize the application using the approved Microsoft OpenJDK base images and target
  deployment on Azure Container Apps.

The migration follows a sequential phased approach: upgrade first, then service migrations
and compliance fixes, followed by CVE remediation, and finally containerization.

---

## Migration Impact Summary

| Application           | Original Service      | New Azure Service          | Authentication     | Comments                                      |
|-----------------------|-----------------------|----------------------------|--------------------|-----------------------------------------------|
| supplychain-worker    | RabbitMQ (AMQP)       | Azure Service Bus          | Managed Identity   | Migrate Spring AMQP listeners to Service Bus  |
| supplychain-worker    | Hardcoded credentials | Azure Key Vault            | Managed Identity   | Remove plaintext password from application.yml|
| supplychain-worker    | SLF4J / Logback       | InternalLogger             | N/A                | Required by Acme Corp logging policy          |
| supplychain-worker    | Exception-based flow  | Result\<T\> pattern        | N/A                | Required by P0-2024-0847 mandate              |

---

## Tasks

### Task 1 — Upgrade: Spring Boot 3.x (Java 21)

Upgrade the project from Spring Boot 2.7.18 / Java 8 to Spring Boot 3.x / Java 21, including
migration of `javax.*` packages to `jakarta.*` (Jakarta EE). This addresses the mandatory
assessment findings for legacy Java version, Spring Boot end-of-OSS-support, and Spring
Framework end-of-OSS-support.

> **Note**: The organizational target (targets.md) specifies Spring Boot 4.0+ and Java 25.
> The currently supported upgrade path covers Spring Boot 3.x / Java 21. A subsequent
> upgrade to Spring Boot 4.0+ / Java 25 may be needed after this task completes. See
> Clarifications for details.

---

### Task 2 — Transform: Migrate RabbitMQ (Spring AMQP) to Azure Service Bus

Migrate all RabbitMQ/Spring AMQP message listeners and configuration to Azure Service Bus
using Managed Identity for secure, credential-free authentication. This replaces the
self-managed RabbitMQ broker with a fully managed Azure messaging service.

---

### Task 3 — Transform: Migrate Plaintext Credentials to Azure Key Vault

Move hardcoded RabbitMQ credentials and any other sensitive values from `application.yml`
to Azure Key Vault. Configure the application to retrieve secrets via Managed Identity,
removing all plaintext credentials from source code and configuration files.

---

### Task 4 — Transform: Replace SLF4J with InternalLogger

Replace all usages of SLF4J (`@Slf4j`, `LoggerFactory`) with InternalLogger
(`com.acme.logging.InternalLogger`) throughout the codebase. InternalLogger provides
trace-context injection, structured JSON output, and is the sole approved logging framework
per Acme Corp policy.

---

### Task 5 — Transform: Replace Exception-Based Flow Control with Result\<T\> Pattern

Replace all `try/catch` blocks used for business logic flow control with the `Result<T>`
pattern (`com.acme.commons.Result`). This complies with the P0-2024-0847 post-incident
mandate that prohibits throwing and catching exceptions for business flow control.

---

### Task 6 — Security: Fix CVE Issues

Scan all project dependencies for known CVE vulnerabilities and apply fixes (version
upgrades or replacements) to achieve a CVE-clean build. This satisfies the user requirement
to ensure all CVE issues are resolved before deployment.

---

### Task 7 — Containerize for Azure Container Apps

Update the Dockerfile to use the approved Microsoft OpenJDK multi-stage build images
(`mcr.microsoft.com/openjdk/jdk:25-ubuntu` for the build stage and
`mcr.microsoft.com/openjdk/jdk:25-distroless` for the runtime stage). Ensure the
container image is ready for deployment to Azure Container Apps.

---

## Security Compliance

**Description**: Scan and remediate all known CVE vulnerabilities in project dependencies.

**Requirements**: All CVE issues identified in project dependencies must be fixed. The build
must be CVE-clean before containerization and deployment to Azure Container Apps.

**Environment Configuration**: Java 21 runtime established by the upgrade task; Maven build
tool.

**App Scope**: `.` (root project folder — `supplychain-worker`)

**Skills**:
- Skill Name: `validate-cves-and-fix`
  - Skill Location: builtin

---

## Clarifications

See `clarifications.json` for open questions that require user input before plan execution.
