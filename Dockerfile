FROM eclipse-temurin:25-jdk-alpine
WORKDIR /app
RUN apk add --no-co-cache \
    gtk+3.0 \
    glib \
    freetype \
    libx11 \
    libxext \
    librender \
    libxtst \
    mesa-gl
COPY target/Test-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]