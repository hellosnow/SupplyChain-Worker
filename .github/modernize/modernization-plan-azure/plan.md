# Modernization Plan: modernization-plan-azure

**Project**: SupplyChain Worker

---

## Technical Framework

- **Language**: Java 8
- **Framework**: Spring Boot 2.7.18
- **Build Tool**: Maven 3.x
- **Messaging**: RabbitMQ (Spring AMQP)
- **Key Dependencies**: Spring Boot Starter, Spring Boot Starter AMQP, Lombok

---

## Overview

This migration upgrades and modernizes the SupplyChain Worker application for Azure. The application currently runs on Java 8 with Spring Boot 2.7.18 and uses RabbitMQ for message processing. The modernization will:

- Upgrade the runtime to Java 21 and Spring Boot 3.x for long-term support and security
- Migrate messaging from RabbitMQ to Azure Service Bus for a fully managed cloud-native messaging service
- Validate and fix all CVE vulnerabilities to ensure a secure deployment
- Update the container image to align with the upgraded Java runtime

The migration follows a phased approach: upgrade first, then migrate services, validate security, and update containerization.

---

## Migration Impact Summary

| Application        | Original Service | New Azure Service     | Authentication     | Comments                          |
|--------------------|------------------|-----------------------|--------------------|-----------------------------------|
| supplychain-worker | RabbitMQ (AMQP)  | Azure Service Bus     | Managed Identity   | Migrate all message listeners     |

---

## Upgrade

**Task**: Upgrade to Spring Boot 3.x

**Description**: Upgrade the application from Java 8 / Spring Boot 2.7.18
to Java 21 / Spring Boot 3.x, including Spring Framework 6.x and
Jakarta EE namespace migration (javax.* → jakarta.*).

**Requirements**:
  Upgrade Spring Boot from 2.7.18 to latest 3.x. This includes
  upgrading Java from 8 to 21, Spring Framework from 5.x to 6.x, and
  migrating from javax.* to jakarta.* namespaces. Update Maven
  compiler settings and all affected dependencies accordingly.

**App Scope**: .

---

## Migration

**Task**: Migrate RabbitMQ to Azure Service Bus

**Description**: Migrate the messaging infrastructure from RabbitMQ
(Spring AMQP) to Azure Service Bus, replacing hardcoded RabbitMQ
credentials with managed identity authentication.

**Requirements**:
  Migrate all RabbitMQ message listeners to Azure Service Bus.
  Replace Spring AMQP / RabbitMQ dependencies with Azure Service Bus
  Spring integration. Update message listener annotations and
  configuration. Remove hardcoded RabbitMQ credentials from
  application.yml. Configure queues: order.created, inventory.alert,
  approval.pending.

**Skills**:
  - Skill Name: migration-amqp-rabbitmq-servicebus
    - Skill Location: builtin

**App Scope**: .

---

## Security Compliance

**Description**: Validate and remediate all CVE vulnerabilities in
project dependencies to ensure a secure deployment to Azure.

**Requirements**:
  Scan all project dependencies for known CVE vulnerabilities and
  apply fixes. Ensure the final artifact has no known critical or
  high severity CVEs after the upgrade and migration tasks are
  complete.

**Environment Configuration**:
  Java 21 runtime established by the upgrade task. Maven build tool.

**App Scope**: .

**Skills**:
  - Skill Name: validate-cves-and-fix
    - Skill Location: builtin

---

## Containerization

**Task**: Update Dockerfile for Java 21

**Description**: Update the existing Dockerfile to use Java 21 base
images, replacing the legacy Java 8 build and runtime images.

**Requirements**:
  Update the multi-stage Dockerfile to use Maven with JDK 21 for the
  build stage and Eclipse Temurin JDK 21 JRE for the runtime stage.
  Update the output JAR filename if the version changes.

**Dockerfile**: ./Dockerfile (existing, to be updated)

**App Scope**: .
