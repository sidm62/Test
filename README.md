# Lämpötilamuunnin ja -seurantasovellus

**Opintojakso:** Ohjelmistotuotanto / Olio-ohjelmointi  
**Tekijä:** sidm62  
**Repositorio:** https://github.com/sidm62/Test  

---

## 1. Tehtävän kuvaus

### Ongelma ja tausta
Lämpötilamuunnokset eri yksiköiden (Celsius, Fahrenheit, Kelvin) välillä sekä muunnoshistorian seuranta vaativat luotettavat muunnosalgoritmit, helppokäyttöisen käyttöliittymän ja tiedon pysyvän tallennuksen. Tämä sovellus tarjoaa Java-pohjaisen graafisen käyttöliittymän (GUI) lämpötilojen muuntamiseen sekä muunnostietojen tallentamiseen ja hallintaan tietokannassa.

### Keskeiset vaatimukset
* **Lämpötilamuunnokset:** Muunnokset Celsius-, Fahrenheit- ja Kelvin-asteiden välillä (esim. `kelvinToCelsius`-metodi).
* **Graafinen käyttöliittymä (GUI):** JavaFX-pohjainen käyttöliittymä, joka käynnistetään `Application.launch()` -metodilla.
* **Tietokanta & DAO-integraatio:** Lämpötilatietueiden (`TempRecord`) ja lämpötilayksiköiden (`TemperatureUnit`) tallennus ja hallinta DAO-mallin (Data Access Object) avulla.
* **CI/CD ja kontitus:** Jenkins-liukuhihnan tuki (`Jenkinsfile`) sekä Docker-kontitus (`Dockerfile`) automaattista kääntämistä ja ajamista varten.
* **Testaus kattavuuksineen:** Automaattinen yksikkötestaus JUnit 5 -kehyksellä ja JaCoCo-testikattavuusraportointi.

### Keskeiset tuotokset
* Täydellinen JavaFX-sovelluksen lähdekoodi (`App.java`, `Main.java`, `TemperatureConverter.java`).
* DAO- kerros ja tietokantayhteydet (`DBConnection.java`, `TempRecordDao.java`, `TemperatureUnitDAO.java`).
* Kattava JUnit 5 -yksikkötestistö DAO-luokille, liiketoimintalogikoille ja entiteeteille.
* Automaatiokonfiguraatiot (`Dockerfile`, `Jenkinsfile`, `pom.xml`).
* GitHub-repositorion palautus Omassa.

---

## 2. Käytetyt teknologiat ja työkalut

| Kategoria | Teknologia / Työkalu | Käyttökohde projektissa |
| :--- | :--- | :--- |
| **Ohjelmointikieli** | Java (JDK 17/21) | Sovelluslogiikka ja entiteetit |
| **GUI-kehys** | JavaFX | Työpöytäkäyttöliittymä (`Application.launch`) |
| **Kääntö- ja hallintatyökalu** | Apache Maven | Projektin kääntäminen ja riippuvuudet (`pom.xml`) |
| **Tietokanta & Pysyvyys** | JDBC / SQL | Tiedon tallennus (`DBConnection`, `TempRecordDao`, `TemperatureUnitDAO`) |
| **Testaus & Kattavuus** | JUnit 5 & JaCoCo | Automaattiset yksikkötestit ja kattavuusraportit |
| **Jatkuva integraatio (CI)** | Jenkins | Automaattinen rakennusliukuhihna (`Jenkinsfile`) |
| **Kontitus** | Docker | Sovelluksen kontitusympäristö (`Dockerfile`) |
| **IDE / Versiohallinta** | IntelliJ IDEA / Git | Kehitysympäristö ja versiohallinta |

---

## 3. Suunnitteluratkaisu ja toteutustapa

### Arkkitehtuuri
Sovellus noudattaa moduloidussa muodossa **Model-View-Controller (MVC) / DAO** -arkkitehtuurimallia:
* **Entiteetit (Model):** `TempRecord.java`, `TemperatureUnit.java` ja `TemperatureConverter.java` vastaavat datarakenteista ja muunnosalgoritmeista.
* **Tietokantakerros (DAO):** `TempRecordDao.java` ja `TemperatureUnitDAO.java` hoitavat tietokantaoperaatiot `DBConnection.java`-luokan läpi.
* **Käyttöliittymä ja aloituspiste (View/Controller):** `App.java` ja `Main.java` alustavat ja käynnistävät sovelluksen JavaFX:n `Application.launch()` -metodilla.

### Keskeiset toteutusratkaisut
1. **Käynnistyslogiikan refaktorointi:** Sovelluksen alustus siirrettiin käyttämään `Application.launch()`-metodia (`Main.java` / `App.java`), mikä varmistaa JavaFX-ympäristön oikeaoppisen alustuksen.
2. **Lämpötilamuunnokset:** `TemperatureConverter.java` sisältää matemaattisen muunnoslogiikan eri asteikkojen välillä (mukaan lukien Kelvin-muunnokset).
3. **Tietokantalogiikan eriytys:** SQL-kyselyt on eristetty käyttöliittymästä DAO-rajapintojen taakse, ja kyselyissä käytetään parametrisoituja lausekkeita turvallisuuden varmistamiseksi.

---

## 4. Testaus ja laadunvarmistus

### Automaattiset yksikkötestit
Yksikkötestit on toteutettu **JUnit 5** -kehyksellä, ja ne kattavat muunnoslogiikan, entiteetit sekä tietokantakyselyt:
* `TemperatureTest.java`
* `TempRecordTest.java`
* `TemperatureUnitTest.java`
* `TempRecordDaoTest.java`
* `TemperatureUnitDAOTest.java`

Aja testit ja luo JaCoCo-kattavuusraportti komennolla:
```bash
mvn clean test


https://users.metropolia.fi/~sidiiqm/Test/target/site/jacoco/
