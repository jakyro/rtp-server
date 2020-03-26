FROM gradle:latest AS build
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:latest

RUN mkdir /app
COPY build/libs/*.jar /app/myjar.jar

ENTRYPOINT ["java", "-jar", "/app/myjar.jar"]