# --- STEP 1: BUILD ---
FROM maven:3.9.14-eclipse-temurin-25-alpine AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# --- STEP 2: RUNTIME ---
FROM eclipse-temurin:25-jre-alpine
WORKDIR /app
COPY --from=builder /app/target*.jar product-service.jar
ENTRYPOINT ["java", "-jar", "product-service.jar"]
