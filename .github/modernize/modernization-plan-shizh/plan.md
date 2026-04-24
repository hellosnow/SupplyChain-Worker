# Modernization Plan: modernization-plan-shizh

**Project**: SupplyChain Worker

---

## Technical Framework

- **Language**: Java 8 (target: Java 21)
- **Framework**: Spring Boot 2.7.18 / Spring Framework 5.x (target: Spring Boot 3.x / Spring Framework 6.x)
- **Build Tool**: Maven
- **Messaging**: RabbitMQ via Spring AMQP (target: Azure Service Bus)
- **Key Dependencies**: spring-boot-starter-amqp, lombok

---

## Overview

This migration modernizes the SupplyChain Worker from a legacy Java 8 / Spring Boot 2.7.18 application to a cloud-ready Java 21 / Spring Boot 3.x service fully integrated with Azure. The application currently uses RabbitMQ for messaging with hardcoded credentials. The new architecture will:

- Upgrade to Spring Boot 3.x and Java 21 to eliminate End-of-Support risks and security vulnerabilities from the outdated runtime
- Migrate messaging from RabbitMQ (Spring AMQP) to Azure Service Bus with managed identity authentication, eliminating hardcoded credentials
- Remediate all known CVE vulnerabilities in project dependencies to achieve a clean security posture

The migration follows a phased approach: runtime upgrade first, then service migration, then security remediation.

---

## Migration Impact Summary

| Application          | Original Service      | New Azure Service         | Authentication     | Comments                              |
|----------------------|-----------------------|---------------------------|--------------------|---------------------------------------|
| supplychain-worker   | RabbitMQ (Spring AMQP)| Azure Service Bus         | Managed Identity   | Migrate queues: order.created, inventory.alert, approval.pending |
| supplychain-worker   | Hardcoded credentials | Azure Service Bus (MI)    | Managed Identity   | Remove plaintext RabbitMQ credentials |

---

## Migration Tasks

### Task 1: Upgrade to Spring Boot 3.x and Java 21

Upgrade the project from Spring Boot 2.7.18 / Java 8 to Spring Boot 3.x / Java 21. This includes upgrading Spring Framework to 6.x, migrating from `javax.*` to `jakarta.*` namespaces, and updating the Dockerfile base image to Java 21.

### Task 2: Migrate RabbitMQ (Spring AMQP) to Azure Service Bus

Migrate the Spring AMQP / RabbitMQ messaging integration to Azure Service Bus. Replace `@RabbitListener` queue handlers (order.created, inventory.alert, approval.pending) with Azure Service Bus consumers using managed identity for credential-free authentication.

---

## Security Compliance

**Description**: Scan all project dependencies for known CVE vulnerabilities and apply fixes to achieve a clean security posture.

**Requirements**: Fix all CVE issues identified in project dependencies. The project must build and pass tests after fixes are applied.

**Environment Configuration**: Java 21, Maven (established by the upgrade task)

**App Scope**: Root project (`.`)

**Skills**:
  - Skill Name: validate-cves-and-fix
    - Skill Location: builtin

---
