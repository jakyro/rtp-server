FROM gradle:4.7.0-jdk8-alpine AS build
WORKDIR /home/gradle/src
RUN gradle build --no-daemon
RUN ls
FROM openjdk:11
# COPY src /src
# RUN gradlew clean build
# RUN javac src/main/java/main.Main.java -d bin
# CMD java -cp bin com.main.Main
# ENTRYPOINT ["java","main.Main"]