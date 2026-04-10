# Modernization Plan — SupplyChain-Worker

| Field             | Value                                                     |
|-------------------|-----------------------------------------------------------|
| **Phase**         | Planning                                                  |
| **Timestamp**     | 2026-04-10T05:10:00Z                                     |
| **Assessment**    | `.github/modernize/SupplyChain-Worker/assessment.yaml`    |
| **Tasks File**    | `.github/modernize/SupplyChain-Worker/tasks.json`         |
| **Total Tasks**   | 8                                                         |
| **Total Effort**  | 39 story points                                           |
| **Playbook**      | Not found — plan generated from assessment only           |

---

## Executive Summary

The **supplychain-worker** application is a legacy Java 8 / Spring Boot 2.7.18 background worker that consumes messages from 3 RabbitMQ queues. This plan upgrades the application to modern, Azure-ready standards through 5 phases with 8 ordered tasks.

**Key Modernization Goals:**
- ✅ Java 8 → Java 17 LTS (security + language features)
- ✅ Spring Boot 2.7.18 → 3.2.x (continued support + Spring Framework 6.x)
- ✅ RabbitMQ → Azure Service Bus (managed Azure messaging)
- ✅ Hardcoded credentials → Azure Key Vault / Managed Identity
- ✅ Dockerfile → modern Java 17 base images

---

## Phases Overview

### Phase 1 · Foundation — Java Version Upgrade
> **Blocker for all subsequent phases**

| Task | Title | Effort | Severity | Status |
|------|-------|--------|----------|--------|
| `task-1-java-upgrade` | Upgrade Java 8 → Java 17 LTS | 5 pts | mandatory | pending |

**Why first:** Spring Boot 3.x requires Java 17+. This must complete before Phase 2.

---

### Phase 2 · Framework — Spring Boot 3.x Upgrade
> Depends on Phase 1

| Task | Title | Effort | Severity | Status |
|------|-------|--------|----------|--------|
| `task-2-springboot-upgrade` | Upgrade Spring Boot 2.7.18 → 3.2.x | 8 pts | mandatory | pending |
| `task-6-validate-build` | Validate Full Build After Framework Upgrades | 2 pts | mandatory | pending |

**Key changes:** Parent POM version bump, `javax.*` → `jakarta.*` namespace migration, Spring Framework 5.x → 6.x resolved transitively.

---

### Phase 3 · Azure Migration — Messaging
> Depends on Phase 2

| Task | Title | Effort | Severity | Status |
|------|-------|--------|----------|--------|
| `task-3-rabbitmq-to-servicebus` | Migrate RabbitMQ → Azure Service Bus | 5 pts | optional | pending |
| `task-7-validate-messaging` | Validate Build After Messaging Migration | 2 pts | optional | pending |

**Queues to migrate:**
1. `order.created` — order creation notifications
2. `inventory.alert` — low stock alerts
3. `approval.pending` — approval request notifications

---

### Phase 4 · Security — Credential Management
> Depends on Phase 3

| Task | Title | Effort | Severity | Status |
|------|-------|--------|----------|--------|
| `task-4-secure-credentials` | Remove Hardcoded Credentials → Azure Key Vault | 3 pts | potential | pending |

**Current risk:** `application.yml` line 13 contains `password: guest` in plain text.

---

### Phase 5 · Containerization & Validation
> Depends on Phase 1 (Dockerfile) and Phases 2–4 (CVE check)

| Task | Title | Effort | Severity | Status |
|------|-------|--------|----------|--------|
| `task-5-update-dockerfile` | Update Dockerfile Base Images for Java 17 | 2 pts | mandatory | pending |
| `task-8-cve-check` | CVE Vulnerability Check on Final Dependencies | 1 pt | mandatory | pending |

---

## Dependency Graph

```
task-1-java-upgrade (Java 8 → 17)
├── task-2-springboot-upgrade (Spring Boot 2.7 → 3.2)
│   ├── task-6-validate-build (validate compilation)
│   └── task-3-rabbitmq-to-servicebus (RabbitMQ → Service Bus)
│       ├── task-7-validate-messaging (validate messaging)
│       └── task-4-secure-credentials (credentials → Key Vault)
│           └── task-8-cve-check (final CVE check)
└── task-5-update-dockerfile (Dockerfile → Java 17 images)
```

---

## Effort Breakdown

| Category         | Tasks | Story Points | % of Total |
|------------------|-------|--------------|------------|
| Upgrade          | 2     | 13           | 33%        |
| Migration        | 1     | 5            | 13%        |
| Security         | 1     | 3            | 8%         |
| Infrastructure   | 1     | 2            | 5%         |
| Validation       | 3     | 5            | 13%        |
| **Total**        | **8** | **28 (+11 contingency = 39)** | **100%** |

---

## Files Impacted

| File | Tasks Modifying |
|------|-----------------|
| `pom.xml` | task-1, task-2, task-3, task-4 |
| `src/main/java/.../OrderMessageListener.java` | task-2, task-3 |
| `src/main/java/.../SupplyChainWorkerApplication.java` | task-2 |
| `src/main/resources/application.yml` | task-3, task-4 |
| `Dockerfile` | task-5 |

---

## Risk Assessment

| Risk | Likelihood | Impact | Mitigation |
|------|-----------|--------|------------|
| javax → jakarta migration breaks annotations | Medium | High | Spring Boot 3.x migration guide; auto-refactor tools |
| Spring AMQP → Service Bus JMS incompatibility | Low | Medium | Spring Cloud Azure provides tested integration |
| Test failures after Spring Boot upgrade | Medium | Medium | Run tests after each phase; fix incrementally |
| Docker image size increase with Java 17 | Low | Low | Use JRE-only images (eclipse-temurin:17-jre) |

---

## Assessment Rules Addressed

| Rule ID | Title | Severity | Resolved By |
|---------|-------|----------|-------------|
| `azure-java-version-02000` | Legacy Java version | mandatory | task-1 |
| `spring-boot-to-azure-spring-boot-version-01000` | Spring Boot EOL | mandatory | task-2 |
| `spring-framework-version-01000` | Spring Framework EOL | mandatory | task-2 |
| `azure-message-queue-rabbitmq-01000` | RabbitMQ usage | optional | task-3 |
| `azure-message-queue-amqp-02000` | Spring AMQP dependency | optional | task-3 |
| `azure-password-01000` | Hardcoded credentials | potential | task-4 |

> **All 6 assessment issues (12 incidents) are covered by this plan.**
