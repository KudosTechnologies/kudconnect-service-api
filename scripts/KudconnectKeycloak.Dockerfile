FROM maven:3.9.2-eclipse-temurin-17-alpine as build
WORKDIR kudconnect-keycloak-custom-provider
COPY kudconnect-keycloak-custom-provider .
RUN ./gradlew --refresh-dependencies assemble

FROM quay.io/keycloak/keycloak:22.0.1

ENV KEYCLOAK_HOME /opt/keycloak

COPY --from=build kudconnect-keycloak-custom-provider/build/libs/kudconnect-customizations.jar ${KEYCLOAK_HOME}/providers/

USER root
RUN chmod -R g+r ${KEYCLOAK_HOME}/providers/
USER keycloak