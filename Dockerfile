FROM openjdk:11.0.11-jdk-oracle
MAINTAINER Achiley Dias
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]


