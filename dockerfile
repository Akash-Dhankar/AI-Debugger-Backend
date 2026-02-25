# ----------- STAGE 1: Build -----------
FROM eclipse-temurin:17-jdk-alpine AS build

WORKDIR /app

# Copy Maven wrapper and pom first (for caching dependencies)
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Give permission to mvnw
RUN chmod +x mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src src

# Build the jar
RUN ./mvnw clean package -DskipTests


# ----------- STAGE 2: Run -----------
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy built jar from previous stage
COPY --from=build /app/target/*.jar app.jar

# Render uses dynamic port
EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]