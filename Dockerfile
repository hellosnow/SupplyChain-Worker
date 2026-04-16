FROM mcr.microsoft.com/openjdk/jdk:25-ubuntu AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

FROM mcr.microsoft.com/openjdk/jdk:25-distroless

WORKDIR /app

COPY --from=build /app/target/supplychain-worker-1.0.0-LEGACY.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
