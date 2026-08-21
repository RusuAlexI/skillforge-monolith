FROM eclipse-temurin:11-jre
WORKDIR /app
COPY target/skill-service-1.0.0.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]