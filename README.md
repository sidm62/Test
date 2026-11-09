# Temperature Converter & Tracking Application

**Course:** Software Engineering / Object-Oriented Programming  
**Author:** sidm62  
**Repository:** https://github.com/sidm62/Test  
**Docker Hub Image:** https://hub.docker.com/r/sidiiqm/temperature-converter  

---

## 1. Assignment Description

### Problem Statement
Temperature conversions across different scale units (Celsius, Fahrenheit, Kelvin) and tracking historical logs require reliable conversion algorithms, a user-friendly graphical interface, and persistent data storage. This application provides a Java-based Graphical User Interface (GUI) to perform unit conversions and manage persistent temperature logs in a database with full multi-language localization (fi, en, ja, sv, ar).

### Key Requirements
* **Temperature Conversion:** Convert between Celsius, Fahrenheit, and Kelvin units (e.g., `kelvinToCelsius` method).
* **Graphical User Interface (GUI):** JavaFX-based desktop application supporting dynamic language switching and Right-to-Left (RTL) layout support for Arabic.
* **Database & DAO Integration:** Store and manage historical temperature records (`TempRecord`) and temperature units (`UnitDTO`) using the Data Access Object (DAO) pattern.
* **CI/CD & Containerization:** Jenkins pipeline integration (`Jenkinsfile`), Docker support (`Dockerfile`), and Kubernetes deployment (`minikube`) for automated containerized deployment.
* **Test Coverage:** Automated unit testing suite with JUnit 5 and JaCoCo code coverage reporting.

### Specific Deliverables
* Complete JavaFX application source code (`App.java`, `Main.java`, `TempRecord.java`, `UnitDTO.java`).
* DAO layer and database connectivity implementation (`DBConnection.java`, `TempRecordDao.java`, `init.sql`).
* Localization resource bundles (`MessagesBundle_*.properties` for `fi`, `en`, `ja`, `sv`, `ar`).
* Comprehensive JUnit 5 unit test suite for DAOs, business logic, and entities.
* CI/CD automation configuration files (`Dockerfile`, `Jenkinsfile`, `deployment.yaml`, `pom.xml`).
* Submission of the GitHub repository link in Oma.

---

## 2. Technologies & Tools Used

| Category | Technology / Tool | Usage in Project |
| :--- | :--- | :--- |
| **Programming Language** | Java (JDK 17) | Core business logic, DAO implementation, and UI |
| **GUI Framework** | JavaFX 25 | Desktop user interface and RTL orientation handling |
| **Build System** | Apache Maven | Build management, dependency resolution, and JaCoCo plugin |
| **Database & Persistence** | MariaDB / JDBC | Persistent storage (`DBConnection`, `TempRecordDao`, `init.sql`) |
| **Testing & Coverage** | JUnit 5 & JaCoCo | Automated unit testing and coverage reporting |
| **Continuous Integration** | Jenkins | CI/CD build pipeline (`Jenkinsfile`) |
| **Containerization** | Docker | Application deployment container with X11 GUI support (`Dockerfile`) |
| **Orchestration** | Kubernetes / Minikube | Container deployment management and pod orchestration |
| **IDE / Version Control** | IntelliJ IDEA / Git | Development environment and source code management |

---

## 3. Design Approach & Implementation Method

### Architecture Overview
The application follows a modular **Model-View-Controller (MVC) / DAO** architectural pattern:
* **Entities (Model / DTO):** `TempRecord.java` and `UnitDTO.java` encapsulate data structures and transfer localized database values.
* **Database Layer (DAO):** `TempRecordDao.java` handles persistent operations through `DBConnection.java` using environment-aware host configuration (`host.docker.internal`).
* **View & Controller:** `Main.java` initializes the JavaFX UI lifecycle via `Application.launch()`, updates static texts via `ResourceBundle`, and manages `NodeOrientation`.

### Key Implementation Decisions
1. **Application Launch Refactoring:** Refactored the entry point to invoke `Application.launch()` inside `Main.java` / `App.java` to ensure proper JavaFX runtime initialization.
2. **Dynamic Localization:** Static UI components load via standard Java `ResourceBundle` files (`MessagesBundle`), while dynamic unit labels and tooltips are fetched dynamically from the localized database tables.
3. **Arabic Right-to-Left (RTL) Layout:** Automatically changes node layout direction using `root.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT)` whenever the Arabic locale (`ar`) is selected.

---

## 4. Testing & Quality Assurance Steps

### Automated Testing & Coverage Report
Automated unit testing is performed using **JUnit 5**, verifying conversion algorithms, entity states, DAO operations, and database connections:
* `TemperatureTest.java`
* `TempRecordTest.java`
* `TemperatureUnitTest.java`
* `TempRecordDaoTest.java`
* `TemperatureUnitDAOTest.java`

Run unit tests locally and generate the JaCoCo test report:
```bash
# Run tests
mvn clean test

# Generate JaCoCo coverage report
mvn jacoco:report

```
### 5. Database Localization Strategy

### Chosen Approach: Translation Table Pattern
To support dynamic, scalable multi-language localization (FI, EN, JA, SV, AR) for database-stored entities like temperature units and UI strings, we implemented the Separate Translation Table Approach.

Instead of adding language-specific columns to the base entity table (e.g., name_fi, name_ja), this design separates core domain entity metadata from localized textual representation.

Architectural Advantages & Justification
High Scalability & Maintainability: Adding support for new languages (e.g., French or Chinese) requires zero schema modifications or DDL migrations—simply insert new rows into the translation table.

Storage & Query Efficiency: Keeps the core temperature_units table minimal and fast to index.

Data Integrity: Cascade deletions (ON DELETE CASCADE) automatically clean up translation records when a unit is removed. The composite unique constraint (unique_unit_lang) prevents duplicate translation entries for the same language.

Full CJK & UTF-8 Compatibility: Built with utf8mb4 encoding and utf8mb4_unicode_ci collation to natively support multi-byte CJK (Japanese), Arabic, and Nordic characters across all GUI environments.
### Database Schema Structure

```sql
-- Base table for unit entities
CREATE TABLE temperature_units (
    id INT AUTO_INCREMENT PRIMARY KEY,
    unit_code VARCHAR(10) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Translation table for localized descriptions
CREATE TABLE temperature_unit_translations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    unit_id INT NOT NULL,
    lang VARCHAR(5) NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    FOREIGN KEY (unit_id) REFERENCES temperature_units(id) ON DELETE CASCADE,
    UNIQUE KEY unique_unit_lang (unit_id, lang)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
