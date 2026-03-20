FROM eclipse-temurin:21-jdk-alpine
LABEL authors="melis"

WORKDIR /app

COPY target/config-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]
