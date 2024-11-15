FROM openjdk:17-jdk-alpine
ARG JAR_FILE=build/libs/\*.jar
COPY ${JAR_FILE} restful-0.0.1-SNAPSHOT-plain.jar
ENTRYPOINT ["java","-jar","/restful-0.0.1-SNAPSHOT-plain.jar"]