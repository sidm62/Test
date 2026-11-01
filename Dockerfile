FROM eclipse-temurin:25-jdk-jammy

WORKDIR /app


RUN apt-get update && apt-get install -y \
    wget \
    unzip \
    libxext6 \
    libxrender1 \
    libxtst6 \
    libxi6 \
    libgl1-mesa-dri \
    libglx-mesa0 \
    libgtk-3-0 \
    fonts-dejavu \
    && rm -rf /var/lib/apt/lists/*


RUN wget https://download2.gluonhq.com/openjfx/25/openjfx-25_linux-x64_bin-sdk.zip \
    && unzip openjfx-25_linux-x64_bin-sdk.zip \
    && mv javafx-sdk-* javafx-sdk-25 \
    && rm openjfx-25_linux-x64_bin-sdk.zip

COPY target/Test-1.0-SNAPSHOT.jar app.jar


ENTRYPOINT ["java", "-Djava.library.path=/app/javafx-sdk-25/lib", "--module-path", "/app/javafx-sdk-25/lib", "--add-modules", "javafx.controls,javafx.fxml", "-Dprism.order=sw", "-jar", "app.jar"]