#FROM registry.access.redhat.com/ubi9 AS ubi-micro-build
#RUN mkdir -p /mnt/rootfs
#RUN dnf install --installroot /mnt/rootfs curl iputils nc vi --releasever 9 --setopt install_weak_deps=false --nodocs -y; dnf --installroot /mnt/rootfs clean all

FROM maven:3.9.4-eclipse-temurin-17-alpine as build
WORKDIR kudconnect-keycloak-custom-provider
COPY kudconnect-keycloak-custom-provider .
RUN ./gradlew --refresh-dependencies assemble

FROM quay.io/keycloak/keycloak:22.0.1

ENV KEYCLOAK_HOME /opt/keycloak

COPY --from=build kudconnect-keycloak-custom-provider/build/libs/kudconnect-customizations.jar ${KEYCLOAK_HOME}/providers/

USER root
RUN chmod -R g+r ${KEYCLOAK_HOME}/providers/
USER keycloak

#COPY --from=ubi-micro-build /mnt/rootfs /