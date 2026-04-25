# Modernization Plan: SupplyChain Worker - Azure Migration

**Project**: SupplyChain Worker

---

## Technical Framework

- **Language**: Java 8
- **Framework**: Spring Boot 2.7.18
- **Build Tool**: Maven 3.x
- **Database**: N/A
- **Key Dependencies**: Spring AMQP (RabbitMQ), Lombok, Spring Boot Test

---

## Overview

> This migration upgrades the SupplyChain Worker from Java 8 / Spring Boot 2.7.18 to
> Java 25 / Spring Boot 4.x and migrates the messaging layer from RabbitMQ to Azure
> Service Bus. The application currently runs as a background worker listening to three
> RabbitMQ queues (order-created, inventory-alert, approval-pending). The new
> architecture will:
>
> - Upgrade the Java runtime and Spring Boot framework to Java 25 / Spring Boot 4.x,
>   eliminating end-of-life components and aligning with organisational targets
> - Replace RabbitMQ AMQP messaging with Azure Service Bus for managed, cloud-native
>   message consumption using Managed Identity authentication
> - Remediate all known CVE vulnerabilities across project dependencies
> - Containerize the application using Microsoft OpenJDK images and deploy to Azure
>   Container Apps
>
> The migration follows a phased approach: upgrade → transform → security hardening →
> containerization → deployment.

---

## Migration Impact Summary

| Application        | Original Service     | New Azure Service    | Authentication    | Comments                                                |
|--------------------|----------------------|----------------------|-------------------|---------------------------------------------------------|
| SupplyChain Worker | RabbitMQ (AMQP)      | Azure Service Bus    | Managed Identity  | Migrate Spring AMQP @RabbitListener consumers           |
| SupplyChain Worker | Local deployment     | Azure Container Apps | Managed Identity  | Containerised with Microsoft OpenJDK 25 distroless image|

---

## Migration Tasks

### Phase 1: Upgrade

| Task ID       | Description                                                                    |
|---------------|--------------------------------------------------------------------------------|
| 001-upgrade   | Upgrade Java 8 / Spring Boot 2.7.18 to Java 25 / Spring Boot 4.x              |

### Phase 2: Transform

| Task ID                                              | Description                                            |
|------------------------------------------------------|--------------------------------------------------------|
| 002-transform-migration-amqp-rabbitmq-servicebus     | Migrate RabbitMQ AMQP messaging to Azure Service Bus   |

### Phase 3: Security Compliance

**Description**: Validate and remediate all CVE vulnerabilities in project dependencies.

**Requirements**: Scan all project dependencies for known CVE vulnerabilities. Fix
identified CVEs by upgrading affected dependencies to their minimum secure versions.
Ensure the project builds and all tests pass after remediation.

**Environment Configuration**: Java 25, Maven build tool.

**App Scope**: `/` (repository root)

**Skills**:
- Skill Name: validate-cves-and-fix
  - Skill Location: builtin

| Task ID                | Description                                                          |
|------------------------|----------------------------------------------------------------------|
| 003-security-cve-fix   | Validate and fix all CVE vulnerabilities in project dependencies     |

### Phase 4: Containerization

| Task ID               | Description                                                                   |
|-----------------------|-------------------------------------------------------------------------------|
| 004-containerization  | Containerize with Microsoft OpenJDK 25 multi-stage Dockerfile                 |

### Phase 5: Deployment

| Task ID           | Description                                          |
|-------------------|------------------------------------------------------|
| 005-deployment    | Deploy SupplyChain Worker to Azure Container Apps    |
