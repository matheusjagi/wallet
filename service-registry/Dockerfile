# AS <NAME> to name this stage as maven
FROM maven:3.8.6 AS maven
LABEL MAINTAINER="jagi.matheus@gmail.com"

WORKDIR /usr/src/app
COPY . /usr/src/app
# Compile and package the application to an executable JAR
RUN mvn package

# For Java 17,
FROM amazoncorretto:17-alpine-jdk

ARG JAR_FILE=service-registry-1.0.0.jar

WORKDIR /opt/app

COPY --from=maven /usr/src/app/target/${JAR_FILE} /opt/app/

ENTRYPOINT ["java","-jar","service-registry-1.0.0.jar"]

