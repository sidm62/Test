# Temperature Converter & Tracking Application

**Course:** Software Engineering / Object-Oriented Programming  
**Author:** sidm62  
**Repository:** https://github.com/sidm62/Test  

---

## 1. Assignment Description

### Problem Statement
Temperature conversions across different scale units (Celsius, Fahrenheit, Kelvin) and tracking historical logs require reliable conversion algorithms, a user-friendly graphical interface, and persistent data storage. This application provides a Java-based Graphical User Interface (GUI) to perform unit conversions and manage persistent temperature logs in a database.

### Key Requirements
* **Temperature Conversion:** Convert between Celsius, Fahrenheit, and Kelvin units (e.g., `kelvinToCelsius` method).
* **Graphical User Interface (GUI):** JavaFX-based desktop application entry point initialized via `Application.launch()`.
* **Database & DAO Integration:** Store and manage historical temperature records (`TempRecord`) and temperature units (`TemperatureUnit`) using the Data Access Object (DAO) pattern.
* **CI/CD & Containerization:** Jenkins pipeline integration (`Jenkinsfile`) and Docker support (`Dockerfile`) for automated building and containerized deployment.
* **Test Coverage:** Automated unit testing suite with JUnit 5 and JaCoCo code coverage reporting.

### Specific Deliverables
* Complete JavaFX application source code (`App.java`, `Main.java`, `TemperatureConverter.java`).
* DAO layer and database connectivity implementation (`DBConnection.java`, `TempRecordDao.java`, `TemperatureUnitDAO.java`).
* Comprehensive JUnit 5 unit test suite for DAOs, business logic, and entities.
* CI/CD automation configuration files (`Dockerfile`, `Jenkinsfile`, `pom.xml`).
* Submission of the GitHub repository link in Oma.

---

## 2. Technologies & Tools Used

| Category | Technology / Tool | Usage in Project |
| :--- | :--- | :--- |
| **Programming Language** | Java (JDK 17/21) | Core business logic and entities |
| **GUI Framework** | JavaFX | Desktop user interface (`Application.launch`) |
| **Build System** | Apache Maven | Build management and dependency resolution (`pom.xml`) |
| **Database & Persistence** | JDBC / SQL | Persistent storage (`DBConnection`, `TempRecordDao`, `TemperatureUnitDAO`) |
| **Testing & Coverage** | JUnit 5 & JaCoCo | Automated unit testing and coverage reports |
| **Continuous Integration** | Jenkins | CI/CD build pipeline (`Jenkinsfile`) |
| **Containerization** | Docker | Application deployment container (`Dockerfile`) |
| **IDE / Version Control** | IntelliJ IDEA / Git | Development environment and source code management |

---

## 3. Design Approach & Implementation Method

### Architecture Overview
The application follows a modular **Model-View-Controller (MVC) / DAO** architectural pattern:
* **Entities (Model):** `TempRecord.java`, `TemperatureUnit.java`, and `TemperatureConverter.java` encapsulate data structures and conversion logic.
* **Database Layer (DAO):** `TempRecordDao.java` and `TemperatureUnitDAO.java` handle persistent operations through `DBConnection.java`.
* **View & Controller:** `App.java` and `Main.java` initialize and start the JavaFX UI lifecycle via `Application.launch()`.

### Key Implementation Decisions
1. **Application Launch Refactoring:** Refactored the entry point to invoke `Application.launch()` inside `Main.java` / `App.java` to ensure proper JavaFX runtime initialization.
2. **Temperature Conversion Routines:** `TemperatureConverter.java` houses mathematical conversion functions across unit scales (including Kelvin).
3. **Database Layer Decoupling:** Isolated raw SQL operations behind DAO interfaces and parameterized queries to prevent SQL injection vulnerabilities.

---

## 4. Testing & Quality Assurance Steps

### Automated Testing & Coverage Report
Automated unit testing is performed using **JUnit 5**, verifying conversion algorithms, entity states, and database interactions:
* `TemperatureTest.java`
* `TempRecordTest.java`
* `TemperatureUnitTest.java`
* `TempRecordDaoTest.java`
* `TemperatureUnitDAOTest.java`

Run unit tests locally and generate the JaCoCo test report:
```bash
mvn clean test

https://users.metropolia.fi/~sidiiqm/Test/target/site/jacoco/
