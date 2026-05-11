# 🛠 Online Ordering and Service Performance Control Platform

[![Java Version](https://img.shields.io/badge/Java-21-blue.svg)](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Build Status](https://github.com/YOUR_USERNAME/YOUR_REPO_NAME/actions/workflows/build.yml/badge.svg)](https://github.com/YOUR_USERNAME/YOUR_REPO_NAME/actions)

A comprehensive solution for automating service ordering processes and monitoring performance in real-time.

---

## 🌟 Key Features

- 🛍 **Online Ordering:** User-friendly interface for selecting services and submitting requests.
- 📉 **Performance Control:** Real-time tracking of order statuses (Waiting -> In Progress -> Completed).
- 👥 **Role-Based Access:** 
  - **Manager:** Client management, master assignment, and overall order oversight.
  - **Master:** View assigned tasks and update execution statuses.
- 💾 **Reliable Data Storage:** JDBC integration with HikariCP connection pooling.
- 🔄 **Automated Migrations:** Database version control powered by Flyway.
- 🔐 **Security:** Secure authentication and password hashing using BCrypt.

---

## 🏗 Architecture

The project follows **Layered Architecture** principles:

1.  **UI Layer (JavaFX):** Interactive graphical user interface.
2.  **Controller Layer:** Request handling and UI interaction logic.
3.  **Service Layer:** Business logic implementation.
4.  **Repository Layer (JDBC):** Database interaction with MySQL.
5.  **DTO Layer:** Decoupled data transfer between layers.

---

## 📥 Installation & Setup

### Prerequisites
- **JDK 21**
- **MySQL Server**
- **Maven** (bundled with modern IDEs)

### Quick Start
1. **Clone the repository:**
   ```bash
   git clone https://github.com/YOUR_USERNAME/YOUR_REPO_NAME.git
   cd YOUR_REPO_NAME
   ```

2. **Database Configuration:**
   Create a database named `mysuperproject` in MySQL and update the credentials in `src/main/resources/application.properties`.

3. **Run the Application:**
   ```bash
   mvn clean package
   mvn javafx:run
   ```

---

## 📦 Creating Installers

The project supports automated generation of native installers (`.exe`, `.deb`, `.dmg`) via `jpackage`.

- **Windows:** `.\packaging\create-installer-win.ps1`
- **Linux/macOS:** `./packaging/create-installer-unix.sh`

---

## 🛤 Roadmap

- [x] Basic infrastructure and DB setup
- [x] Authentication and roles
- [x] Order management system
- [ ] Review and rating system
- [ ] PDF report generation
- [ ] Automated client notifications

---

## 👨‍💻 Author
[Your Name or Nickname]

---
*Generated with ❤️ by Gemini CLI*
