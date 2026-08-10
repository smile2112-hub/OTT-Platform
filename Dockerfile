# Authors:  Aakanksha Ashok Hedau  <aakanksha0499@gmail.com>
#           Mahesh Ganesh Kajale   <maheshkajale2112@gmail.com>
# Project:  FlowFlix OTT Platform
# ─────────────────────────────────────────────
# Stage 1 – Build the Spring Boot JAR
# ─────────────────────────────────────────────
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Cache dependencies separately
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source and build (skip tests during image build)
COPY src ./src
RUN mvn package -DskipTests -B

# ─────────────────────────────────────────────
# Stage 2 – Lightweight runtime image
# ─────────────────────────────────────────────
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Create uploads directory
RUN mkdir -p /app/uploads

COPY --from=build /app/target/flowflix-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

ENV SPRING_DATASOURCE_URL=jdbc:mysql://db:3306/cinemaDB?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
ENV SPRING_DATASOURCE_USERNAME=root
ENV SPRING_DATASOURCE_PASSWORD=MyStrongPassword123!
ENV JWT_SECRET=change_me_to_secure_value

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
