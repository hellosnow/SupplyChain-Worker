# Modernization Plan: modernization-plan-0420

**Project**: SupplyChain Worker

---

## Technical Framework

- **Language**: Java 8
- **Framework**: Spring Boot 2.7.18
- **Build Tool**: Maven
- **Messaging**: RabbitMQ (AMQP)
- **Key Dependencies**: Spring AMQP, Lombok, SLF4J

---

## Overview

> This migration upgrades and modernizes the SupplyChain Worker
> background service for Azure. The application currently runs
> on Java 8 with Spring Boot 2.7.18, uses RabbitMQ for
> messaging with hardcoded credentials, and employs prohibited
> logging and error handling patterns. The new architecture
> will:
>
> - Upgrade to Java 25 with Spring Boot 4.0+ per
>   organizational target framework requirements
> - Migrate from RabbitMQ to Azure Service Bus with Managed
>   Identity for credential-free authentication
> - Adopt InternalLogger and Result\<T\> pattern per
>   organizational playbook guardrails
> - Resolve all known CVE vulnerabilities in dependencies
> - Update containerization for Azure Container Apps
>   deployment
>
> The migration follows a phased approach: upgrade runtime
> first, then transform services and code patterns, fix
> security vulnerabilities, and finally update
> containerization.

---

## Migration Impact Summary

| Application        | Original Service   | New Azure Service   | Authentication   | Comments                   |
|--------------------|--------------------|---------------------|------------------|----------------------------|
| SupplyChain Worker | RabbitMQ (AMQP)    | Azure Service Bus   | Managed Identity | Migrate 3 message queues   |
| SupplyChain Worker | SLF4J / Lombok     | InternalLogger      | N/A              | Per playbook policy        |
| SupplyChain Worker | Exception handling | Result\<T\> pattern | N/A              | Per playbook policy        |
| SupplyChain Worker | Hardcoded creds    | Managed Identity    | Managed Identity | Eliminated via Service Bus |

---

## Upgrade

### Task 001: Upgrade to Spring Boot 4.0+ (Java 25)

**Description**: Upgrade from Spring Boot 2.7.18 (Java 8) to
Spring Boot 4.0+ (Java 25) including Jakarta EE namespace
migration (javax.* to jakarta.*).

---

## Transform

### Task 002: Migrate RabbitMQ to Azure Service Bus

**Description**: Migrate from RabbitMQ AMQP messaging to Azure
Service Bus with Managed Identity authentication, covering all
three queues (order.created, inventory.alert,
approval.pending).

**Skills**:
- migration-amqp-rabbitmq-servicebus (builtin)

### Task 003: Migrate Logging to InternalLogger

**Description**: Replace SLF4J/Lombok logging and
System.out.println with InternalLogger per organizational
policy.

### Task 004: Migrate Error Handling to Result\<T\> Pattern

**Description**: Replace exception-based try-catch error
handling with Result\<T\> pattern per organizational policy.

---

## Security Compliance

**Description**: Validate and fix all CVE vulnerabilities in
project dependencies to achieve zero known vulnerabilities.

**Requirements**: Scan all dependencies for known CVEs and
fix all identified vulnerabilities.

**Environment Configuration**: Java 25, Maven,
Spring Boot 4.0+

**App Scope**: Root project (pom.xml)

**Skills**:
- validate-cves-and-fix (builtin)

---

## Containerization

### Task 006: Update Dockerfile for Azure Container Apps

**Description**: Update Dockerfile to use Java 25 base images
(mcr.microsoft.com/openjdk/jdk:25-ubuntu for build,
mcr.microsoft.com/openjdk/jdk:25-distroless for runtime)
targeting Azure Container Apps deployment.
