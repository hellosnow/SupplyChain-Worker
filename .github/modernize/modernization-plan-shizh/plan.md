# Modernization Plan: Upgrade and Migrate to Azure

**Project**: SupplyChain Worker

---

## Technical Framework

- **Language**: Java 8
- **Framework**: Spring Boot 2.7.18
- **Build Tool**: Maven 3.8
- **Database**: N/A
- **Key Dependencies**: Spring AMQP (RabbitMQ), Lombok (SLF4J via @Slf4j)

---

## Overview

This migration upgrades the SupplyChain Worker application from its legacy Java 8 /
Spring Boot 2.7.18 stack to a modern cloud-native architecture on Azure. The application
currently processes supply chain messages from three RabbitMQ queues (order-created,
inventory-alert, approval-pending) with hardcoded credentials and uses SLF4J for logging.
The new architecture will:

- Upgrade the runtime from Java 8 / Spring Boot 2.7.18 to Java 25 / Spring Boot 4.x,
  meeting the internal EOL policy for Java 8 and Spring Boot 2.x
- Replace RabbitMQ with Azure Service Bus for managed cloud messaging with Managed
  Identity authentication, eliminating the on-premises broker dependency
- Externalize all hardcoded credentials to Azure Key Vault for secure, policy-compliant
  secrets management
- Replace prohibited SLF4J and `System.out.println` usage with InternalLogger
  (`com.acme.logging.InternalLogger`) for trace-context-aware structured logging
- Replace exception-based error handling with the `Result<T>` pattern
  (`com.acme.commons.Result`) per the P0-2024-0847 post-incident mandate
- Containerize the application and deploy to Azure Container Apps (ACA) as the target
  compute platform per the default replatform strategy

The migration follows a phased approach: framework upgrade first, then parallel service
migrations, then containerization and deployment to ACA.

---

## Migration Impact Summary

| Application        | Original Service              | New Azure Service            | Authentication   | Comments                    |
|--------------------|-------------------------------|------------------------------|------------------|-----------------------------|
| SupplyChain Worker | Spring Boot 2.7.18 / Java 8   | Spring Boot 4.0+ / Java 25   | N/A              | Framework + runtime upgrade |
| SupplyChain Worker | RabbitMQ (AMQP)               | Azure Service Bus            | Managed Identity | 3 queues migrated           |
| SupplyChain Worker | Hardcoded RabbitMQ creds      | Azure Key Vault              | Managed Identity | Externalize all secrets      |
| SupplyChain Worker | SLF4J / System.out.println    | InternalLogger               | N/A              | Policy: prohibited logging  |
| SupplyChain Worker | Exception-based error flow    | Result\<T\> pattern          | N/A              | Policy: P0-2024-0847 mandate|
| SupplyChain Worker | Local container (Java 8)      | Azure Container Apps (ACA)   | Managed Identity | Replatform to ACA           |

---

## Migration Tasks

The following tasks are tracked in `tasks.json`. Tasks are executed in the listed order,
with each task depending on the successful completion of its predecessors.

| #  | Task ID                                               | Type             | Description                                                         |
|----|-------------------------------------------------------|------------------|---------------------------------------------------------------------|
| 1  | 001-upgrade-spring-boot-4x                            | upgrade          | Upgrade Java 8 / Spring Boot 2.7.18 to Java 25 / Spring Boot 4.x   |
| 2  | 002-transform-migration-rabbitmq-to-servicebus        | transform        | Migrate RabbitMQ AMQP messaging to Azure Service Bus (MI)           |
| 3  | 003-transform-migration-credentials-to-azure-keyvault | transform        | Migrate hardcoded credentials to Azure Key Vault                    |
| 4  | 004-transform-migration-logging-to-internallogger     | transform        | Replace SLF4J and System.out.println with InternalLogger            |
| 5  | 005-transform-migration-error-handling-to-result      | transform        | Replace try/catch error handling with Result\<T\> pattern           |
| 6  | 006-containerization                                  | containerization | Update Dockerfile to Java 25 / Microsoft OpenJDK base images        |
| 7  | 007-deployment-azure-container-apps                   | deployment       | Deploy SupplyChain Worker to Azure Container Apps via Bicep          |
