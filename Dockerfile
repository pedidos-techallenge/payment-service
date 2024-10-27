# syntax=docker/dockerfile:1
FROM maven:3.9.9-amazoncorretto-21 AS builder
WORKDIR /tmp/app

RUN ls

COPY payment/pom.xml ./pom.xml
COPY payment/src ./src


RUN --mount=type=cache,target=/root/.m2 mvn install -DskipTests

FROM amazoncorretto:21

WORKDIR /workspace
EXPOSE 8080 8000

COPY --from=builder /tmp/app/target/*.jar app.jar
RUN chmod +x app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]