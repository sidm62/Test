FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# Asennetaan kaikki JavaFX GTK- ja X11-riippuvuudet
RUN apt-get update && apt-get install -y \
    libxext6 \
    libxrender1 \
    libxtst6 \
    libxi6 \
    libgl1-mesa-dri \
    libglx-mesa0 \
    libgl1-mesa-glx \
    libgtk-3-0 \
    libcanberra-gtk3-module \
    fonts-dejavu \
    && rm -rf /var/lib/apt/lists/*

COPY target/Test-1.0-SNAPSHOT.jar app.jar

# Asetetaan JavaFX käyttämään ohjelmistopohjaista renderöintiä (sw)
ENTRYPOINT ["java", "-Dprism.order=sw", "-Dprism.verbose=true", "-cp", "app.jar", "org.example.App"]