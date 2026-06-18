# Modernization Plan: modernization-plan-batch

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

This migration upgrades and modernizes the SupplyChain Worker
application for Azure. The application currently runs on Java 8
with Spring Boot 2.7.18 and uses RabbitMQ for messaging with
hardcoded credentials. The new architecture will:

- Upgrade Java from 8 to 25 and Spring Boot from 2.7.18 to
  4.0+ to comply with Acme Corp internal policies
- Migrate messaging from RabbitMQ to Azure Service Bus with
  Managed Identity for secure, credential-free authentication
- Validate and remediate all CVE vulnerabilities in project
  dependencies
- Containerize the application with updated base images for
  deployment to Azure Container Apps

The migration follows a phased approach: upgrade runtime first,
then transform services to Azure equivalents, remediate security
vulnerabilities, and finally containerize for deployment.

---

## Migration Impact Summary

| Application        | Original Service   | New Azure Service     | Authentication   | Comments                           |
|--------------------|--------------------|-----------------------|------------------|------------------------------------|
| SupplyChain Worker | Java 8             | Java 25               | N/A              | Java 8 prohibited by policy        |
| SupplyChain Worker | Spring Boot 2.7.18 | Spring Boot 4.0+      | N/A              | Spring Boot 2.x prohibited         |
| SupplyChain Worker | RabbitMQ (AMQP)    | Azure Service Bus     | Managed Identity | Migrate Spring AMQP to Service Bus |

---

## Tasks

### Task 1: Upgrade Spring Boot to 4.0+ and Java to 25

Upgrade the project from Spring Boot 2.7.18 / Java 8 to
Spring Boot 4.0+ / Java 25 to comply with organizational
policies. This includes migrating from JavaEE (javax.*) to
Jakarta EE (jakarta.*) namespaces.

### Task 2: Migrate RabbitMQ to Azure Service Bus

Migrate the messaging layer from RabbitMQ (Spring AMQP) to
Azure Service Bus with Managed Identity for secure,
credential-free authentication.

---

## Security Compliance

**Description**: Validate and remediate all CVE vulnerabilities
in project dependencies to achieve a clean security posture.

**Requirements**:
  Fix all known CVE issues in project dependencies.

**Environment Configuration**:
  Runtime: Java 25, Build tool: Maven, Framework: Spring Boot 4.0+
  (established by previous upgrade task).

**App Scope**:
  Root project folder (pom.xml and all modules).

**Skills**:
  - Skill Name: validate-cves-and-fix
    - Skill Location: builtin

---

## Containerization

### Task 4: Update Dockerfile

Update the existing Dockerfile to use Java 25 base images
and prepare for Azure Container Apps deployment.

---

## Deployment

### Task 5: Deploy to Azure Container Apps

Deploy the modernized and containerized application to Azure
Container Apps as the target compute platform.
