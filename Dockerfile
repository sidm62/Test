FROM eclipse-temurin:25-jdk-alpine
WORKDIR /app
COPY target/Test-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]