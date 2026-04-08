# SupplyChain Worker

Background worker service for Supply Chain Management System.

## Features

- Consumes RabbitMQ messages
- Processes order notifications
- Handles inventory alerts
- Manages approval workflows

## Tech Stack

- Java 8
- Spring Boot 2.7.18
- RabbitMQ

## Tech Debt

This worker demonstrates legacy messaging patterns that should be migrated to Azure Service Bus.
