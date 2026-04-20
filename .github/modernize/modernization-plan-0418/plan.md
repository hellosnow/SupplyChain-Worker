# Modernization Plan: modernization-plan-0418

**Project**: SupplyChain Worker

---

## Technical Framework

- **Language**: Java 8
- **Framework**: Spring Boot 2.7.18 (Spring Framework 5.x)
- **Build Tool**: Maven
- **Database**: N/A
- **Key Dependencies**: Spring Boot AMQP (RabbitMQ), Lombok

---

## Overview

This migration upgrades and modernizes the SupplyChain Worker application for Azure deployment. The application currently runs on Java 8 with Spring Boot 2.7.18 and uses RabbitMQ for background message processing across three queues (order-created, inventory-alert, approval-pending). The new architecture will:

- Upgrade the Java runtime from Java 8 to Java 25 and Spring Boot from 2.7.18 to 4.0+ to comply with organizational standards
- Migrate messaging from RabbitMQ with AMQP to Azure Service Bus with managed identity for secure, credential-free authentication
- Validate and remediate all CVE vulnerabilities in project dependencies
- Containerize the application using approved base images and deploy to Azure Container Apps

The migration follows a phased approach: framework upgrade, service migration, security remediation, containerization, and deployment.

---

## Migration Impact Summary

| Application        | Original Service   | New Azure Service    | Authentication   | Comments                          |
|--------------------|--------------------|----------------------|------------------|-----------------------------------|
| SupplyChain Worker | Java 8             | Java 25              | N/A              | Java 8 EOL per policy             |
| SupplyChain Worker | Spring Boot 2.7.18 | Spring Boot 4.0+     | N/A              | 2.x and 3.x prohibited            |
| SupplyChain Worker | RabbitMQ (AMQP)    | Azure Service Bus    | Managed Identity | Migrate messaging to Azure         |

---

## Tasks

### Task 1: Upgrade Spring Boot to 4.0+ and Java to 25

Upgrade the project from Spring Boot 2.7.18 / Java 8 to Spring Boot 4.0+ / Java 25 to comply with organizational policies. This includes migrating from JavaEE (javax.*) to Jakarta EE (jakarta.*) namespaces and updating all dependencies.

### Task 2: Migrate RabbitMQ to Azure Service Bus

Migrate the messaging infrastructure from RabbitMQ with AMQP to Azure Service Bus with managed identity for secure, credential-free authentication.

---

## Security Compliance

**Description**: Validate and remediate all CVE vulnerabilities in project dependencies to ensure the application is free of known security issues before deployment.

**Requirements**:
  Fix all CVE issues in project dependencies

**Environment Configuration**:
  Java 25, Maven, Spring Boot 4.0+

**App Scope**:
  Root project (pom.xml)

**Skills**:
  - Skill Name: validate-cves-and-fix
    - Skill Location: builtin

---

## Containerization

### Task 4: Update Dockerfile

Update the existing Dockerfile to use the organization's approved container base images for Java 25 and support Azure Container Apps deployment.

---

## Deployment

### Task 5: Deploy to Azure Container Apps

Deploy the containerized SupplyChain Worker application to Azure Container Apps, the organization's default compute platform for Java services.
