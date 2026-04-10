# Migration Plan: Secure Credentials with Azure Managed Identity

## Session Information
- **Migration Session ID:** 2bfc9493-4527-489c-83ba-ce15aba3069c
- **Task:** task-4-secure-credentials
- **Scenario:** Hardcoded credentials to Azure Key Vault / Managed Identity
- **Time of Plan Creation:** 2026-04-10
- **Branch:** appmod/java-migration-20260410130521
- **Uncommitted Changes Policy:** stash

## Project Language
- **Language:** Java
- **Framework:** Spring Boot 3.2.12
- **Build Tool:** Maven
- **JDK Version:** 17

## Migration Goal
Verify and ensure all credentials in the SupplyChain-Worker application are secured using
Azure Managed Identity. Scan for any hardcoded secrets, passwords, or tokens and migrate
them to Azure Key Vault if found. The previous task (task-3) already migrated RabbitMQ to
Azure Service Bus with Managed Identity.

## Knowledge Base Used
- **KB ID:** `plaintext-credential-to-azure-keyvault`
- **Title:** Migrate plaintext credentials to Azure Key Vault

## Pre-Migration Security Scan Results

### Java Files Scanned
| File | Hardcoded Credentials Found |
|------|----------------------------|
| `src/main/java/com/acme/scm/worker/SupplyChainWorkerApplication.java` | ❌ None |
| `src/main/java/com/acme/scm/worker/listener/OrderMessageListener.java` | ❌ None |

### Configuration Files Scanned
| File | Hardcoded Credentials Found |
|------|----------------------------|
| `src/main/resources/application.yml` | ❌ None — all sensitive values use `${ENV_VAR}` |

### Build Files Scanned
| File | Hardcoded Credentials Found |
|------|----------------------------|
| `pom.xml` | ❌ None |

## Current Managed Identity Configuration

`application.yml` already correctly uses environment variables:
```yaml
spring:
  cloud:
    azure:
      credential:
        managed-identity-enabled: true
        client-id: ${AZURE_CLIENT_ID}
      servicebus:
        entity-type: queue
        namespace: ${SERVICE_BUS_NAMESPACE}
```

## Existing Dependencies (from task-3)
- `com.azure.spring:spring-cloud-azure-starter` — provides Azure identity support
- `com.azure.spring:spring-messaging-azure-servicebus` — Service Bus messaging

## Files to Change
No file changes are required because:
1. All RabbitMQ credentials (`username: guest`, `password: guest`) were already removed in task-3
2. No hardcoded secrets, passwords, or tokens exist in any source or config files
3. The application already uses Managed Identity with environment variables

## Required Environment Variables
The following environment variables must be configured in the deployment environment:

| Variable | Description | Example |
|----------|-------------|---------|
| `AZURE_CLIENT_ID` | Managed Identity client ID (user-assigned) | `xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx` |
| `SERVICE_BUS_NAMESPACE` | Azure Service Bus namespace (without `.servicebus.windows.net`) | `my-servicebus-namespace` |

## Build Environment Settings

### JDK Settings
- **JDK Version:** 17 (LTS)
- **JAVA_HOME:** System default JDK 17
- **Need to install new JDK:** No

### Build Tool Settings
- **Build Tool:** Maven
- **Wrapper Used:** No
- **MAVEN_HOME:** System Maven installation
