FROM alpine:latest AS builder

RUN apk add openjdk17 \
    curl

WORKDIR application
ARG ARTIFACT_NAME
COPY ${ARTIFACT_NAME}.jar application.jar
RUN java -Djarmode=layertools -jar application.jar extract


FROM alpine:latest AS final
WORKDIR application

RUN apk add openjdk21 \
    curl

ARG EXPOSED_PORT
EXPOSE ${EXPOSED_PORT}