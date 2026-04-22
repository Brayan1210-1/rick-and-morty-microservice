# -------- STAGE 1: Build --------
FROM gradle:8.14-jdk21 AS builder
WORKDIR /app
COPY . .
RUN gradle bootJar -x test

# -------- STAGE 2: Run --------
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]