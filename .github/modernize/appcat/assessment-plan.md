# Application Assessment Plan

## Overview

This assessment analyzes the **SupplyChain-Worker** Java application for Azure migration readiness using AppCAT (Azure Migrate Application and Code Assessment Tool).

## Target Azure Services

The assessment will evaluate readiness for the following Azure compute targets:

| Target | Description |
|--------|-------------|
| **Azure Kubernetes Service (AKS)** | Best practices for deploying the app to AKS |
| **Azure App Service** | Best practices for deploying the app to Azure App Service |
| **Azure Container Apps** | Best practices for deploying the app to Azure Container Apps |

## Analysis Mode

- **Mode**: `issue-only` — analyze source code to detect issues

## What Will Be Analyzed

- Java source code under `src/`
- Maven build configuration (`pom.xml`)
- Application dependencies and frameworks
- Spring Boot / RabbitMQ usage patterns

## Expected Outputs

- Assessment report (`report.json`) generated under `.github/modernize/appcat/result/`
- Issues categorized by severity and migration target
- Actionable recommendations for Azure migration
