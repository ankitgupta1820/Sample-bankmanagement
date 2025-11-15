# Build stage
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Give execute permission (IMPORTANT on Render)
RUN chmod +x mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the JAR file
COPY --from=build /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
