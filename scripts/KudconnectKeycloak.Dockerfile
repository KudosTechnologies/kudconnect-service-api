FROM maven:3.9.2-eclipse-temurin-17-alpine as build
WORKDIR kudconnect-keycloak-custom-provider
COPY kudconnect-keycloak-custom-provider .
RUN ./gradlew --refresh-dependencies assemble


FROM quay.io/keycloak/keycloak:21.1.2

ENV KEYCLOAK_HOME /opt/keycloak

#COPY custom-providers/*.jar ${KEYCLOAK_HOME}/providers/
COPY --from=build kudconnect-keycloak-custom-provider/build/libs/kudconnect-customizations.jar ${KEYCLOAK_HOME}/providers/
ADD https://repo1.maven.org/maven2/org/jboss/resteasy/resteasy-client/4.7.9.Final/resteasy-client-4.7.9.Final.jar ${KEYCLOAK_HOME}/providers/resteasy-client-4.7.9.Final.jar
ADD https://repo1.maven.org/maven2/org/jboss/resteasy/resteasy-client-api/4.7.9.Final/resteasy-client-api-4.7.9.Final.jar ${KEYCLOAK_HOME}/providers/resteasy-client-api-4.7.9.Final.jar
ADD https://repo1.maven.org/maven2/org/wildfly/client/wildfly-client-config/1.0.1.Final/wildfly-client-config-1.0.1.Final.jar ${KEYCLOAK_HOME}/providers/wildfly-client-config-1.0.1.Final.jar


USER root
RUN chmod -R g+r ${KEYCLOAK_HOME}/providers/
USER keycloak