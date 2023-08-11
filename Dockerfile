FROM maven:3.9.3-eclipse-temurin-17-alpine AS maven_builder
# Copy the Gradle wrapper files into the container
COPY kudconnect-service-api/gradlew .
COPY kudconnect-service-api/gradle gradle

# Copy the Gradle build files into the container
COPY kudconnect-service-api/build.gradle .
COPY kudconnect-service-api/settings.gradle .

# Copy apiDef
COPY kudconnect-service-api/spec spec

# Build and publish the api JAR
RUN ./gradlew clean build
RUN ./gradlew publishToMavenLocal

# Copy the Gradle wrapper files into the container
COPY kudconnect-service/gradlew .
COPY kudconnect-service/gradle gradle

# Copy the Gradle build files into the container
COPY kudconnect-service/build.gradle .
COPY kudconnect-service/gradle.properties .
COPY kudconnect-service/settings.gradle .

# Copy the source code and resources into the container
COPY kudconnect-service/src src

# Build the application with Gradle
RUN ./gradlew clean build -x test

FROM eclipse-temurin:17.0.8_7-jre-alpine

# Install cURL
RUN apk --no-cache add curl

# Copy the Spring Boot application JAR into the container
COPY --from=maven_builder /build/libs/kudconnect-service-1.0.0-SNAPSHOT.jar kudconnect-service.jar

EXPOSE 8080
EXPOSE 5005

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /kudconnect-service.jar"]