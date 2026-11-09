FROM eclipse-temurin:25-jdk-jammy

WORKDIR /app

# Asennetaan järjestelmäriippuvuudet + Xvfb (virtuaalinäyttö) + Noto CJK -fontit
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
    xvfb \
    fonts-dejavu \
    fonts-noto-cjk \
    && rm -rf /var/lib/apt/lists/*

# Lataa OpenJFX 25 Linux SDK
RUN wget https://download2.gluonhq.com/openjfx/25/openjfx-25_linux-x64_bin-sdk.zip \
    && unzip openjfx-25_linux-x64_bin-sdk.zip \
    && rm openjfx-25_linux-x64_bin-sdk.zip

COPY target/Test-1.0-SNAPSHOT.jar app.jar

# Käynnistetään Xvfb virtuaalinäytölle :99 ja ajetaan Java-sovellus sen sisällä
ENTRYPOINT ["sh", "-c", "Xvfb :99 -screen 0 1024x768x24 & DISPLAY=:99 java --enable-native-access=javafx.graphics -Djava.library.path=/app/javafx-sdk-25/lib --module-path /app/javafx-sdk-25/lib --add-modules javafx.controls,javafx.fxml -Dprism.order=sw -jar app.jar"]