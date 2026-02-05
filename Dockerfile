# ---- Build stage ----
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# Cache dependencies
COPY pom.xml .
RUN mvn -B dependency:go-offline

# App source
COPY src ./src

# Build jar (skip running tests)
RUN mvn -B package -DskipTests

# ---- Runtime stage ----
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy only the built artifact
COPY --from=builder /app/target/*.jar app.jar

# App port
EXPOSE 8080

# Run app
CMD ["java","-jar","app.jar"]