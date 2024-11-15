FROM gradle:7.6.0-jdk17 AS build

COPY . /home/gradle/project
WORKDIR /home/gradle/project

RUN gradle clean build --no-daemon

FROM openjdk:17-jdk-slim

WORKDIR /app
COPY --from=build /home/gradle/project/build/libs/*.jar restful-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/restful-0.0.1-SNAPSHOT.jar"]