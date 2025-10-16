# syntax=docker/dockerfile:1.6

FROM eclipse-temurin:17-jre-alpine
WORKDIR /workspace/app

ARG JAR_FILE=target/user-profile-service-1.0.0-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

ENV JAVA_OPTS=""
EXPOSE 8082
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /workspace/app/app.jar"]
