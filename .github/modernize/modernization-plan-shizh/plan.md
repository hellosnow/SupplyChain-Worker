# Modernization Plan: modernization-plan-shizh

**Project**: supplychain-worker

---

## Technical Framework

- **Language**: Java 8
- **Framework**: Spring Boot 2.7.18, Spring Framework 5.x
- **Build Tool**: Maven
- **Database**: None
- **Key Dependencies**: Spring AMQP (RabbitMQ), Lombok

---

## Overview

> This migration upgrades the SupplyChain Worker from Java 8 / Spring Boot 2.7.18 to
> Java 25 / Spring Boot 4.x and migrates its messaging infrastructure from RabbitMQ
> (Spring AMQP) to Azure Service Bus. The application currently operates as a background
> worker consuming three RabbitMQ queues with hardcoded credentials in configuration
> files. The new architecture will:
>
> - Upgrade to Java 25 and Spring Boot 4.x to address end-of-life framework issues and
>   comply with company targets (targets.md)
> - Replace RabbitMQ messaging with Azure Service Bus using passwordless Managed Identity
>   authentication, eliminating the on-premises message broker dependency
> - Move all hardcoded credentials to Azure Key Vault for centralized, secure secrets
>   management per company policy
> - Migrate logging from SLF4J and System.out.println to InternalLogger to comply with
>   company logging standards (policies.md)
> - Identify and fix all CVE vulnerabilities in project dependencies
> - Containerize the application and deploy to Azure Container Apps
>
> The migration follows a phased approach: platform upgrade first, then parallel service
> migrations and security hardening, followed by containerization and deployment to
> Azure Container Apps (ACA) per the company modernization strategy (charter.md).

---

## Migration Impact Summary

| Application         | Original Service               | New Azure Service     | Authentication     | Comments                                      |
|---------------------|--------------------------------|-----------------------|--------------------|-----------------------------------------------|
| supplychain-worker  | RabbitMQ (Spring AMQP)         | Azure Service Bus     | Managed Identity   | Migrate 3 AMQP queues to Service Bus          |
| supplychain-worker  | Hardcoded credentials in YAML  | Azure Key Vault       | Managed Identity   | Externalize RabbitMQ credentials to Key Vault |
| supplychain-worker  | SLF4J / System.out.println     | InternalLogger        | N/A                | Comply with company logging policy            |

---

## Modernization Tasks

| #   | Task                                             | Type              |
|-----|--------------------------------------------------|-------------------|
| 001 | Spring Boot 4.x and Java 25 Upgrade              | upgrade           |
| 002 | Migrate RabbitMQ AMQP to Azure Service Bus       | transform         |
| 003 | Migrate Hardcoded Credentials to Azure Key Vault | transform         |
| 004 | Migrate Logging to InternalLogger                | transform         |
| 005 | Fix CVE Vulnerabilities                          | security          |
| 006 | Containerize Application                         | containerization  |
| 007 | Deploy to Azure Container Apps                   | deployment        |

---

## Security Compliance

**Description**: Identify and fix all CVE (Common Vulnerabilities and Exposures)
vulnerabilities in project dependencies to ensure the application is free from known
security issues before deployment to Azure.

**Requirements**:
  All CVE vulnerabilities in the project's direct and transitive dependencies must be
  identified and resolved. The application must pass dependency vulnerability scanning
  with no known CVEs remaining after remediation.

**Environment Configuration**:
  Java 25, Maven 3.9+, Spring Boot 4.x (established by task 001-upgrade-spring-boot-4)

**App Scope**:
  ./ (project root)

**Skills**:
  - Skill Name: validate-cves-and-fix
    - Skill Location: builtin
