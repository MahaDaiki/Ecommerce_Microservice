# Utiliser une image de base Java
FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/usermanagement-service-0.0.1-SNAPSHOT.jar /app/usermanagement-service.jar
ENTRYPOINT ["java","-jar","/app.jar"]