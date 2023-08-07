FROM maven:3.9.2-eclipse-temurin-17-alpine as maven_builder

WORKDIR /kudconnect-service-api

# Copy the Gradle wrapper files into the container
COPY gradlew .
COPY gradle gradle

# Copy the Gradle build files into the container
COPY build.gradle .
COPY settings.gradle .

# Copy apiDef
COPY spec spec
# Copy the source code and resources into the container
COPY src src

RUN ./gradlew build
RUN ./gradlew publishToMavenLocal

# Set the working directory inside the container
WORKDIR /kudconnect-service

# Copy the Gradle wrapper files into the container
COPY gradlew .
COPY gradle gradle

# Copy the Gradle build files into the container
COPY build.gradle .
COPY gradle.properties .
COPY settings.gradle .

# Copy the source code and resources into the container
COPY src src

# Build the application with Gradle
RUN ./gradlew build -x test

FROM eclipse-temurin:17.0.7_7-jdk-alpine

COPY --from=maven_builder /kudconnect-service/build/kudconnect-service-1.0.0-SNAPSHOT.jar kudconnect-service.jar
# Copy the Spring Boot application JAR into the container
#COPY build/libs/kudconnect-service-1.0.0-SNAPSHOT.jar kudconnect-service.jar

EXPOSE 8080
EXPOSE 5005

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /kudconnect-service.jar"]