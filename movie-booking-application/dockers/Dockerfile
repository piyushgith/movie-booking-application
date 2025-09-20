# =========================
# 1. Build Stage
# =========================
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy pom + maven wrapper first (cache dependencies)
COPY pom.xml mvnw ./
COPY .mvn .mvn

# Make maven wrapper executable and download dependencies
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Copy source and build
COPY src ./src
RUN ./mvnw clean package -DskipTests

# =========================
# 2. Runtime Stage
# =========================
FROM openjdk:21-jdk-slim

WORKDIR /app

# Create non-root user for security
RUN groupadd -r appgroup && useradd -r -g appgroup appuser

# Copy only the jar from build stage
COPY --from=builder /app/target/*.jar app.jar

# Create directory for H2 DB files with proper permissions
RUN mkdir -p /app/data && chown -R appuser:appgroup /app

# Expose application port
EXPOSE 4000

# Environment variables for Spring Boot H2 configuration
#ENV SPRING_PROFILES_ACTIVE=docker \
#    SERVER_PORT=4000 \
#    SPRING_DATASOURCE_URL=jdbc:h2:file:/app/data/moviedb;AUTO_SERVER=TRUE;DB_CLOSE_ON_EXIT=FALSE \
#    SPRING_DATASOURCE_DRIVER_CLASS_NAME=org.h2.Driver \
#    SPRING_DATASOURCE_USERNAME=sa \
#    SPRING_DATASOURCE_PASSWORD=password \
#    SPRING_H2_CONSOLE_ENABLED=true \
#    SPRING_H2_CONSOLE_PATH=/h2-console \
#    SPRING_JPA_DATABASE_PLATFORM=org.hibernate.dialect.H2Dialect \
#    SPRING_JPA_HIBERNATE_DDL_AUTO=update \
#    SPRING_JPA_SHOW_SQL=false \
#    JAVA_OPTS="-Xms256m -Xmx512m"

# Switch to non-root user
USER appuser

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:4000/actuator/health || exit 1

# Run the application with JVM optimizations
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]