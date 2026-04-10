# Architecture Diagram

This diagram illustrates the high-level architecture of the SupplyChain Worker, a Spring Boot background worker that processes supply chain events from a RabbitMQ message broker.

## Application Architecture

```mermaid
flowchart TD
    subgraph Producers["External Message Producers"]
        P1["Order Service"]
        P2["Inventory Service"]
        P3["Approval Service"]
    end

    subgraph Broker["Message Broker - RabbitMQ 3.6\nHost: rabbitmq  Port: 5672"]
        Q1["Queue: order.created"]
        Q2["Queue: inventory.alert"]
        Q3["Queue: approval.pending"]
    end

    subgraph Worker["SupplyChain Worker - Spring Boot 2.7.18 / Java 8"]
        App["SpringApplication\nEnableScheduling"]
        subgraph Listeners["Message Listeners - Spring AMQP"]
            L1["OrderMessageListener\nhandleOrderCreated"]
            L2["OrderMessageListener\nhandleInventoryAlert"]
            L3["OrderMessageListener\nhandleApprovalPending"]
        end
        Log["SLF4J / Logback\nLogging"]
    end

    subgraph Notifications["Downstream Notifications"]
        N1["Order Confirmation Email"]
        N2["Procurement Team Alert"]
        N3["Manager Approval Request"]
    end

    P1 -- "publishes" --> Q1
    P2 -- "publishes" --> Q2
    P3 -- "publishes" --> Q3

    Q1 -- "RabbitListener" --> L1
    Q2 -- "RabbitListener" --> L2
    Q3 -- "RabbitListener" --> L3

    App --> Listeners
    Listeners --> Log

    L1 -- "sends" --> N1
    L2 -- "sends" --> N2
    L3 -- "sends" --> N3
```
