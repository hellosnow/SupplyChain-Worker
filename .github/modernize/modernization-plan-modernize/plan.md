# Modernization Plan: SupplyChain Worker Migration to Azure

**Project**: supplychain-worker

---

## Technical Framework

- **Language**: Java 8
- **Framework**: Spring Boot 2.7.18
- **Build Tool**: Maven
- **Database**: None
- **Key Dependencies**: Spring Boot Starter AMQP (RabbitMQ),
  Lombok, Spring Boot Starter Test

---

## Overview

This migration upgrades and modernizes the SupplyChain Worker
background service for Azure. The application currently runs on
Java 8 with Spring Boot 2.7.18, uses RabbitMQ for message
processing, and contains hardcoded credentials. The new
architecture will:

- Upgrade to a modern Java runtime and Spring Boot framework
  for long-term support, security patches, and ecosystem
  compatibility
- Migrate messaging from RabbitMQ to Azure Service Bus with
  Managed Identity for a fully managed, secure cloud-native
  messaging service
- Remediate all known CVE vulnerabilities to ensure a secure
  dependency baseline
- Update containerization to align with the upgraded runtime

The migration follows a phased approach: upgrade first, then
service migration, then security remediation, then
containerization.

---

## Migration Impact Summary

| Application       | Original Service | New Azure Service    | Authentication | Comments                  |
|--------------------|-----------------|----------------------|----------------|---------------------------|
| supplychain-worker | RabbitMQ (AMQP) | Azure Service Bus    | Managed Identity | Migrate all 3 queues     |
| supplychain-worker | Hardcoded creds | Azure Service Bus MI | Managed Identity | Resolved by SB migration |

---

## Task 1: Upgrade Spring Boot

**Type**: upgrade

**Description**: Upgrade Spring Boot from 2.7.18 to latest
stable 3.x, including Java 8 to 21, Spring Framework 5.x to
6.x, and javax.* to jakarta.* namespace migration.

**Requirements**:
- Upgrade Spring Boot parent from 2.7.18 to latest 3.x
- Upgrade Java from 8 to 21
- Migrate javax.* imports to jakarta.* namespace
- Update pom.xml Java version properties
- Ensure application compiles and existing tests pass

**Success Criteria**:
- Project builds successfully
- All existing unit tests pass

---

## Task 2: Migrate RabbitMQ to Azure Service Bus

**Type**: transform

**Description**: Migrate from RabbitMQ with Spring AMQP to
Azure Service Bus for fully managed cloud messaging.

**Requirements**:
- Replace Spring AMQP RabbitMQ dependencies with Azure Service
  Bus
- Migrate all 3 message listeners (order-created,
  inventory-alert, approval-pending) to Azure Service Bus
- Remove hardcoded RabbitMQ credentials from application.yml
- Use Managed Identity for authentication

**Skills**:
- Skill Name: migration-amqp-rabbitmq-servicebus
  - Skill Location: builtin

**Dependencies**: Task 1

**Success Criteria**:
- Project builds successfully
- All existing unit tests pass

---

## Security Compliance

**Type**: security

**Description**: Validate and remediate all CVE vulnerabilities
in project dependencies to ensure a secure dependency baseline.

**Requirements**:
- Scan all project dependencies for known CVE vulnerabilities
- Apply fixes for any identified CVEs
- Ensure zero known critical/high CVE issues remain

**Environment Configuration**:
- Java 21 runtime (established by Task 1)
- Maven build tool

**App Scope**: .

**Skills**:
- Skill Name: validate-cves-and-fix
  - Skill Location: builtin

**Dependencies**: Task 1, Task 2

---

## Task 4: Containerization

**Type**: containerization

**Description**: Update the existing Dockerfile to use the
upgraded Java 21 runtime images for the modernized application.

**Requirements**:
- Update build stage base image for Java 21
- Update runtime stage base image for Java 21
- Update JAR filename reference if version changed
- Ensure multi-stage build pattern is preserved

**Dockerfile Path**: Dockerfile

**Dependencies**: Security Compliance Task
