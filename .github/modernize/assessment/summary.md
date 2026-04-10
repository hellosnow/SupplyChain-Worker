# Modernization Assessment Summary

**Target Azure Services**: Azure Kubernetes Service, Azure App Service, Azure Container Apps

## Overall Statistics

**Total Applications**: 1

**Name: supplychain-worker**
- Mandatory: 3 issues
- Potential: 1 issues
- Optional: 2 issues

> **Severity Levels Explained:**
> - **Mandatory**: The issue has to be resolved for the migration to be successful.
> - **Potential**: This issue may be blocking in some situations but not in others. These issues should be reviewed to determine whether a change is required or not.
> - **Optional**: The issue discovered is real issue fixing which could improve the app after migration, however it is not blocking.

## Applications Profile

### Name: supplychain-worker
- **JDK Version**: 8
- **Frameworks**: Spring Boot, Spring
- **Languages**: Java
- **Build Tools**: Maven

**Key Findings**:
- **Mandatory Issues (8 locations)**:
  - <!--ruleid=azure-java-version-02000-->Legacy Java version (3 locations found)
  - <!--ruleid=spring-boot-to-azure-spring-boot-version-01000-->Spring Boot Version is End of OSS Support (3 locations found)
  - <!--ruleid=spring-framework-version-01000-->Spring Framework Version End of OSS Support (2 locations found)
- **Potential Issues (1 locations)**:
  - <!--ruleid=azure-password-01000-->Password found in configuration file (1 location found)
- **Optional Issues (3 locations)**:
  - <!--ruleid=azure-message-queue-rabbitmq-01000-->Spring RabbitMQ usage found in code (1 location found)
  - <!--ruleid=azure-message-queue-amqp-02000-->Spring AMQP dependency found (2 locations found)

## Next Steps

For comprehensive migration guidance and best practices, visit:
- [GitHub Copilot modernization](https://aka.ms/ghcp-appmod)

Have questions or suggestions? [Share your feedback](https://aka.ms/ghcp-appmod/feedback)
