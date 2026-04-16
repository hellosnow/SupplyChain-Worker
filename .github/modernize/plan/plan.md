# Modernization Plan: Java and Spring Boot Upgrade

**Project**: SupplyChain Worker

---

## Technical Framework

- **Language**: Java 8
- **Framework**: Spring Boot 2.7.18 (Spring Framework 5.x)
- **Build Tool**: Maven
- **Database**: N/A
- **Key Dependencies**: Spring Boot AMQP (RabbitMQ), Lombok

---

## Overview

This migration upgrades the SupplyChain Worker runtime to meet Acme Corp's current technology standards. The application currently runs on Java 8 and Spring Boot 2.7.18, both of which are prohibited under the internal playbook policy. The new architecture will:

- Upgrade the Java runtime from Java 8 to Java 25, satisfying the organization's minimum runtime requirement and eliminating a prohibited dependency
- Upgrade Spring Boot from 2.7.18 (2.x) to 4.0+, ensuring compliance with internal policy that prohibits Spring Boot 2.x and 3.x
- Migrate from JavaEE (`javax.*`) namespaces to Jakarta EE (`jakarta.*`) namespaces as required by Spring Boot 4.0+

The migration follows a single-phase approach focused on runtime and framework version upgrades.

---

## Migration Impact Summary

| Application        | Original Service    | New Service       | Authentication | Comments                    |
|--------------------|---------------------|-------------------|----------------|-----------------------------|
| SupplyChain Worker | Java 8              | Java 25           | N/A            | Java 8 prohibited by policy |
| SupplyChain Worker | Spring Boot 2.7.18  | Spring Boot 4.0+  | N/A            | Spring Boot 2.x prohibited  |

---

## Tasks

### Task 1: Upgrade Spring Boot to 4.0+ and Java to 25

Upgrade the project from Spring Boot 2.7.18 / Java 8 to Spring Boot 4.0+ / Java 25 to comply with organizational policies. This includes updating `pom.xml`, migrating from JavaEE (`javax.*`) to Jakarta EE (`jakarta.*`) namespaces, and ensuring all existing dependencies are compatible with the upgraded versions.
