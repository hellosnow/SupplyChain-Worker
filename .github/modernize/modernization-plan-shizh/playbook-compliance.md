# Playbook Compliance Report

**Plan**: modernization-plan-shizh  
**Project**: supplychain-worker  
**Playbook**: Acme Corp Modernization Playbook (v2025-01-15)

---

## Playbook Compliance

| Playbook | Rule | Status | Task |
|----------|------|--------|------|
| charter.md | Java 8, 11, 17 are EOL — upgrade required | ⚠️ PARTIAL | 001 (upgrades to Java 21; target is Java 25 — see clarifications.json) |
| charter.md | Spring Boot 2.x and 3.x must be upgraded | ⚠️ PARTIAL | 001 (upgrades to Spring Boot 3.x; target is 4.0+ — see clarifications.json) |
| charter.md | Java supply chain services must replatform to Azure Container Apps | ✅ COVERED | 007 |
| targets.md | Target Java version: 25 (latest LTS) | ⚠️ PARTIAL | 001 (upgrades to Java 21; full path to Java 25 pending clarification) |
| targets.md | Target Spring Boot version: 4.0+ | ⚠️ PARTIAL | 001 (upgrades to Spring Boot 3.x; full path to 4.0+ pending clarification) |
| targets.md | Maven 3.9+ required | ❌ NOT COVERED | - |
| targets.md | Default compute: Azure Container Apps (ACA) | ✅ COVERED | 007 |
| targets.md | All S2S communication via ServiceMesh SDK (`com.acme.mesh.ServiceMesh`) | ➖ N/A | No service-to-service HTTP calls found in codebase |
| targets.md | Azure Key Vault for credentials and secrets | ✅ COVERED | 003 |
| targets.md | Azure AD (OAuth 2.0 / OIDC) for user-facing authentication | ➖ N/A | No user-facing authentication in this worker service |
| targets.md | Managed Identity for service-to-service authentication | ✅ COVERED | 002, 003 |
| targets.md | RestTemplate → ServiceMesh SDK | ➖ N/A | `RestTemplate` not present in codebase |
| targets.md | WebClient → ServiceMesh SDK | ➖ N/A | `WebClient` not present in codebase |
| targets.md | FeignClient → ServiceMesh SDK | ➖ N/A | `FeignClient` not present in codebase |
| targets.md | OkHttp → ServiceMesh SDK | ➖ N/A | `OkHttp` not present in codebase |
| targets.md | Apache HttpClient → ServiceMesh SDK | ➖ N/A | `Apache HttpClient` not present in codebase |
| targets.md | SLF4J (`@Slf4j`, `LoggerFactory`) → InternalLogger | ✅ COVERED | 004 |
| targets.md | Log4j → InternalLogger | ➖ N/A | Log4j not present in codebase |
| targets.md | Logback → InternalLogger | ✅ COVERED | 004 (removes Logback pulled in by Spring Boot) |
| targets.md | `java.util.logging` → InternalLogger | ➖ N/A | `java.util.logging` not used in codebase |
| targets.md | `System.out.println` / `System.err.println` → InternalLogger | ➖ N/A | Not used in codebase |
| targets.md | Exception-based error handling → `Result<T>` (`com.acme.commons.Result`) | ✅ COVERED | 005 |
| targets.md | JAAS authentication → Azure AD | ➖ N/A | JAAS not used in codebase |
| targets.md | LDAP authentication → Azure AD | ➖ N/A | LDAP not used in codebase |
| targets.md | Container build image: `mcr.microsoft.com/openjdk/jdk:25-ubuntu` | ✅ COVERED | 007 |
| targets.md | Container runtime image: `mcr.microsoft.com/openjdk/jdk:25-distroless` | ✅ COVERED | 007 |
| policies.md | User-facing auth must use Azure AD with OAuth 2.0 / OIDC | ➖ N/A | No user-facing authentication in this worker service |
| policies.md | Service-to-service auth must use Managed Identity | ✅ COVERED | 002, 003 |
| policies.md | Legacy JAAS and LDAP must be migrated | ➖ N/A | JAAS/LDAP not used in codebase |
| policies.md | Sensitive values must be stored in Azure Key Vault | ✅ COVERED | 003 |
| policies.md | All S2S communication via ServiceMesh SDK (mTLS, circuit breaking, tracing) | ➖ N/A | No S2S HTTP calls in codebase |
| policies.md | All traffic must use TLS 1.2+ | ❌ NOT COVERED | No explicit task; relies on Azure Container Apps platform enforcement |
| policies.md | Data at rest must be encrypted (service-managed keys) | ❌ NOT COVERED | No explicit task; relies on Azure service defaults |
| policies.md | SOC 2 compliance for all applications | ❌ NOT COVERED | No explicit task in plan |
| policies.md | PCI-DSS compliance | ➖ N/A | Not in the Payments portfolio |
| policies.md | `RestTemplate` prohibited → ServiceMesh SDK | ➖ N/A | Not present in codebase |
| policies.md | `WebClient` prohibited → ServiceMesh SDK | ➖ N/A | Not present in codebase |
| policies.md | `FeignClient` prohibited → ServiceMesh SDK | ➖ N/A | Not present in codebase |
| policies.md | `OkHttp` prohibited → ServiceMesh SDK | ➖ N/A | Not present in codebase |
| policies.md | `Apache HttpClient` prohibited → ServiceMesh SDK | ➖ N/A | Not present in codebase |
| policies.md | SLF4J (`@Slf4j`, `LoggerFactory`) prohibited → InternalLogger | ✅ COVERED | 004 |
| policies.md | Log4j (any version) prohibited → InternalLogger | ➖ N/A | Not present in codebase |
| policies.md | Logback direct usage prohibited → InternalLogger | ✅ COVERED | 004 |
| policies.md | `java.util.logging` prohibited → InternalLogger | ➖ N/A | Not present in codebase |
| policies.md | `System.out.println` / `System.err.println` prohibited → InternalLogger | ➖ N/A | Not present in codebase |
| policies.md | JAAS prohibited → Azure AD | ➖ N/A | Not present in codebase |
| policies.md | LDAP prohibited → Azure AD | ➖ N/A | Not present in codebase |
| policies.md | Throwing exceptions for business logic flow control prohibited → `Result<T>` | ✅ COVERED | 005 |
| policies.md | `try/catch` for flow control prohibited → `Result<T>` | ✅ COVERED | 005 |
| policies.md | `@ControllerAdvice` for business exceptions prohibited → `Result<T>` | ➖ N/A | No web/controller layer in this worker service |
| policies.md | Hardcoded credentials in config files / source code prohibited → Azure Key Vault | ✅ COVERED | 003 |
| policies.md | Azure Key Vault required cloud resource for every modernized app | ✅ COVERED | 003 |
| policies.md | InternalLogger required as sole logging framework | ✅ COVERED | 004 |

---

**COVERED: 16/52  ·  PARTIAL: 4/52  ·  NOT COVERED: 4/52  ·  N/A: 28/52**

> **Note on N/A rules**: 28 rules apply to technologies (RestTemplate, WebClient, FeignClient, OkHttp, Apache HttpClient, Log4j, java.util.logging, JAAS, LDAP, PCI-DSS, user-facing auth, @ControllerAdvice) that are not present in the `supplychain-worker` codebase and therefore do not require a migration task.

---

## Gaps Requiring Attention

| # | Playbook | Gap | Recommendation |
|---|----------|-----|----------------|
| 1 | charter.md / targets.md | Java target is 25 but task 001 only upgrades to Java 21 | Resolve via `clarifications.json` — confirm whether a two-step upgrade (21 → 25) is acceptable |
| 2 | charter.md / targets.md | Spring Boot target is 4.0+ but task 001 only upgrades to 3.x | Resolve via `clarifications.json` — plan a subsequent upgrade task after 3.x stabilisation |
| 3 | targets.md | Maven version not pinned to 3.9+ | Add a task or pom.xml wrapper configuration to enforce Maven 3.9+ |
| 4 | policies.md | TLS 1.2+ enforcement not explicitly tasked | Document reliance on Azure Container Apps platform TLS termination; verify ACA configuration during deployment |
| 5 | policies.md | Data-at-rest encryption not explicitly tasked | Document reliance on Azure Service Bus and Azure Key Vault default encryption; confirm no unencrypted persistent volumes are used |
| 6 | policies.md | SOC 2 compliance not addressed by any task | Add a compliance review task or document SOC 2 controls inherited from Azure managed services |
