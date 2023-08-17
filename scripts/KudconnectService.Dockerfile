FROM maven:3.9.3-eclipse-temurin-17-alpine AS maven_builder

COPY kudconnect-service-api kudconnect-service-api
COPY kudconnect-service kudconnect-service

WORKDIR kudconnect-service-api
RUN ./gradlew publishToMavenLocal

WORKDIR ../kudconnect-service
RUN ./gradlew clean build

FROM eclipse-temurin:17.0.8_7-jre-alpine

COPY --from=maven_builder kudconnect-service/build/libs/kudconnect-service-1.0.0-SNAPSHOT.jar kudconnect-service.jar

EXPOSE 8080 5005

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /kudconnect-service.jar"]