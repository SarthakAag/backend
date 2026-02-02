# ================= BUILD STAGE =================
FROM eclipse-temurin:17-jdk-focal AS builder

WORKDIR /app

# Copy Maven files
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Download dependencies first (better caching)
RUN chmod +x mvnw && ./mvnw dependency:go-offline

# Copy source code
COPY src ./src

# Build application
RUN ./mvnw clean package -DskipTests


# ================= RUNTIME STAGE =================
FROM eclipse-temurin:17-jre-focal

WORKDIR /app

# Copy the built jar
COPY --from=builder /app/target/*.jar app.jar

# Expose Spring Boot port
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
]
