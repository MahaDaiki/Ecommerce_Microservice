# Utiliser une image de base Java
FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/product-service.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]