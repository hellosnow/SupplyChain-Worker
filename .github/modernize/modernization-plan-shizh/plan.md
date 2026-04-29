# Modernization Plan: Upgrade and Migrate to Azure

**Project**: SupplyChain Worker

---

## Technical Framework

- **Language**: Java 8
- **Framework**: Spring Boot 2.7.18
- **Build Tool**: Maven
- **Database**: N/A
- **Key Dependencies**: Spring AMQP (RabbitMQ), Lombok, SLF4J

---

## Overview

> This migration modernizes the SupplyChain Worker from a legacy Java 8 / Spring Boot 2.7.18
> background worker to a cloud-native Azure application. The application currently uses RabbitMQ
> AMQP for messaging with hardcoded credentials, SLF4J for logging, and exception-based flow
> control. The new architecture will:
>
> - Upgrade the runtime to Java 25 / Spring Boot 4.x to meet internal LTS policy requirements
>   (Java 8, 11, and 17 are end-of-life for internal use; Spring Boot 2.x must be upgraded)
> - Replace RabbitMQ messaging with Azure Service Bus using Managed Identity for secure,
>   cloud-native message processing across all three queues
> - Eliminate hardcoded credentials by storing all secrets in Azure Key Vault, accessed via
>   Managed Identity
> - Replace SLF4J and System.out.println with InternalLogger for trace-context-aware logging
>   as mandated by internal policy
> - Adopt the Result\<T\> pattern for error handling, replacing exception-based flow control
>   per post-incident mandate P0-2024-0847
> - Containerize and deploy to Azure Container Apps as the default compute platform for
>   Java services in the supply chain system
>
> The migration follows a phased approach: runtime upgrade first, then Azure service migrations,
> followed by containerization and deployment to Azure Container Apps.

---

## Migration Impact Summary

| Application        | Original Service          | New Azure Service         | Authentication   | Comments                              |
|--------------------|---------------------------|---------------------------|------------------|---------------------------------------|
| SupplyChain Worker | Java 8 / Spring Boot 2.7  | Java 25 / Spring Boot 4.x | N/A              | Internal LTS policy; 2.x end-of-life  |
| SupplyChain Worker | RabbitMQ AMQP             | Azure Service Bus         | Managed Identity | 3 queues migrated                     |
| SupplyChain Worker | Hardcoded credentials      | Azure Key Vault           | Managed Identity | RabbitMQ connection secrets           |
| SupplyChain Worker | SLF4J / System.out.println| InternalLogger            | N/A              | Policy-mandated logging framework     |
| SupplyChain Worker | Exception-based flow ctrl  | Result\<T\> pattern       | N/A              | Post-incident mandate P0-2024-0847    |
| SupplyChain Worker | Local deployment           | Azure Container Apps      | Managed Identity | Default compute strategy per charter  |
