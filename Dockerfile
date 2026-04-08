# TECH DEBT: Uses old Java 8 base image
FROM maven:3.8-openjdk-8 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:8-jre

WORKDIR /app

COPY --from=build /app/target/supplychain-worker-1.0.0-LEGACY.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
