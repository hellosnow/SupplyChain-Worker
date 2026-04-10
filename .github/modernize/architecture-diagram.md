# Architecture Diagram

SupplyChain Worker is a Spring Boot background worker that listens to RabbitMQ message queues and processes supply chain events such as order notifications, inventory alerts, and approval requests.

## Application Architecture

```mermaid
flowchart TD
    subgraph External["External Message Broker"]
        RMQ["RabbitMQ\nHost: rabbitmq:5672\nCredentials: guest/guest"]
        Q1["Queue: order.created"]
        Q2["Queue: inventory.alert"]
        Q3["Queue: approval.pending"]
        RMQ --> Q1
        RMQ --> Q2
        RMQ --> Q3
    end

    subgraph Worker["SupplyChain Worker\nSpring Boot 2.7.18 / Java 8\nPort: 8082"]
        APP["SupplyChainWorkerApplication\nSpring Boot Entry Point\nEnableScheduling"]
        subgraph Listeners["Message Listeners\nSpring AMQP / RabbitListener"]
            L1["handleOrderCreated\nProcesses order notifications\nSends email confirmations"]
            L2["handleInventoryAlert\nProcesses low-stock alerts\nNotifies procurement team"]
            L3["handleApprovalPending\nProcesses approval requests\nNotifies managers"]
        end
        APP --> Listeners
    end

    subgraph Logging["Observability"]
        LOG["SLF4J / Logback\nApplication logs at INFO level"]
    end

    Q1 -->|"AMQP consume"| L1
    Q2 -->|"AMQP consume"| L2
    Q3 -->|"AMQP consume"| L3

    L1 -->|"log events"| LOG
    L2 -->|"log events"| LOG
    L3 -->|"log events"| LOG
```
