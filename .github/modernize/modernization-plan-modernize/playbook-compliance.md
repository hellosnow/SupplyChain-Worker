## Playbook Compliance

| Playbook | Rule | Status | Task |
|----------|------|--------|------|
| charter.md | Java 8, 11, 17 are EOL for internal use | ✅ COVERED | 001 |
| charter.md | Spring Boot 2.x and 3.x must be upgraded | ❌ NOT COVERED | - |
| charter.md | Replatform to Azure Container Apps (ACA) | ❌ NOT COVERED | - |
| targets.md | Java → 25 (latest LTS) | ❌ NOT COVERED | - |
| targets.md | Spring Boot → 4.0+ | ❌ NOT COVERED | - |
| targets.md | Maven → 3.9+ | ❌ NOT COVERED | - |
| targets.md | ACA default compute for all services | ❌ NOT COVERED | - |
| targets.md | ServiceMesh SDK for all s2s communication | ✅ COVERED | N/A |
| targets.md | Azure Key Vault for credentials/secrets | ❌ NOT COVERED | - |
| targets.md | Azure AD (OAuth 2.0/OIDC) for user-facing auth | ✅ COVERED | N/A |
| targets.md | Managed Identity for s2s auth | ✅ COVERED | 002 |
| targets.md | SLF4J (@Slf4j) → InternalLogger | ❌ NOT COVERED | - |
| targets.md | Log4j → InternalLogger | ✅ COVERED | N/A |
| targets.md | Logback → InternalLogger | ✅ COVERED | N/A |
| targets.md | java.util.logging → InternalLogger | ✅ COVERED | N/A |
| targets.md | System.out/err → InternalLogger | ❌ NOT COVERED | - |
| targets.md | RestTemplate → ServiceMesh SDK | ✅ COVERED | N/A |
| targets.md | WebClient → ServiceMesh SDK | ✅ COVERED | N/A |
| targets.md | FeignClient → ServiceMesh SDK | ✅ COVERED | N/A |
| targets.md | OkHttp → ServiceMesh SDK | ✅ COVERED | N/A |
| targets.md | Apache HttpClient → ServiceMesh SDK | ✅ COVERED | N/A |
| targets.md | Exception-based error handling → Result\<T\> | ❌ NOT COVERED | - |
| targets.md | JAAS → Azure AD | ✅ COVERED | N/A |
| targets.md | LDAP → Azure AD | ✅ COVERED | N/A |
| targets.md | Build image: mcr.microsoft.com/openjdk/jdk:25-ubuntu | ❌ NOT COVERED | - |
| targets.md | Runtime image: mcr.microsoft.com/openjdk/jdk:25-distroless | ❌ NOT COVERED | - |
| policies.md | User-facing auth must use Azure AD/OIDC | ✅ COVERED | N/A |
| policies.md | S2S auth must use Managed Identity | ✅ COVERED | 002 |
| policies.md | Legacy JAAS/LDAP must be migrated | ✅ COVERED | N/A |
| policies.md | Secrets must be stored in Azure Key Vault | ❌ NOT COVERED | - |
| policies.md | Access secrets via KV starter or MI | ❌ NOT COVERED | - |
| policies.md | S2S communication via ServiceMesh SDK | ✅ COVERED | N/A |
| policies.md | All traffic must use TLS 1.2+ | ❌ NOT COVERED | - |
| policies.md | Data at rest must be encrypted | ❌ NOT COVERED | - |
| policies.md | PCI-DSS for Payments portfolio | ✅ COVERED | N/A |
| policies.md | SOC 2 compliance for all apps | ❌ NOT COVERED | - |
| policies.md | Restricted data requires customer-managed keys | ✅ COVERED | N/A |
| policies.md | Prohibited: RestTemplate | ✅ COVERED | N/A |
| policies.md | Prohibited: WebClient | ✅ COVERED | N/A |
| policies.md | Prohibited: FeignClient | ✅ COVERED | N/A |
| policies.md | Prohibited: OkHttp | ✅ COVERED | N/A |
| policies.md | Prohibited: Apache HttpClient | ✅ COVERED | N/A |
| policies.md | Prohibited: SLF4J (@Slf4j, LoggerFactory) | ❌ NOT COVERED | - |
| policies.md | Prohibited: Log4j (any version) | ✅ COVERED | N/A |
| policies.md | Prohibited: Logback (direct usage) | ✅ COVERED | N/A |
| policies.md | Prohibited: java.util.logging | ✅ COVERED | N/A |
| policies.md | Prohibited: System.out.println / System.err.println | ❌ NOT COVERED | - |
| policies.md | Prohibited: JAAS | ✅ COVERED | N/A |
| policies.md | Prohibited: LDAP | ✅ COVERED | N/A |
| policies.md | Prohibited: Exceptions for business logic flow | ❌ NOT COVERED | - |
| policies.md | Prohibited: try/catch blocks for flow control | ❌ NOT COVERED | - |
| policies.md | Prohibited: @ControllerAdvice for biz exceptions | ✅ COVERED | N/A |
| policies.md | Prohibited: Hardcoded credentials in config | ✅ COVERED | 002 |
| policies.md | Required: Azure Key Vault for secrets mgmt | ❌ NOT COVERED | - |
| policies.md | Required: InternalLogger as sole logging framework | ❌ NOT COVERED | - |
| policies.md | Coding: Result\<T\> for all error handling | ❌ NOT COVERED | - |
| policies.md | Coding: ServiceMesh SDK for all s2s comms | ✅ COVERED | N/A |
| policies.md | Coding: InternalLogger for all logging | ❌ NOT COVERED | - |
| policies.md | Coding: Externalize config; KV for secrets | ❌ NOT COVERED | - |

**COVERED: 33/59  ·  NOT COVERED: 26/59**
