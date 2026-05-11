# 🛠 Платформа для онлайн-замовлення та контролю виконання сервісних послуг

[![Java Version](https://img.shields.io/badge/Java-21-blue.svg)](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Build Status](https://github.com/YOUR_USERNAME/YOUR_REPO_NAME/actions/workflows/build.yml/badge.svg)](https://github.com/YOUR_USERNAME/YOUR_REPO_NAME/actions)

Комплексне рішення для автоматизації процесів замовлення сервісних послуг та моніторингу їх виконання в режимі реального часу.

---

## 🌟 Основні можливості

- 🛍 **Онлайн-замовлення:** Зручний інтерфейс для вибору послуг та оформлення заявок.
- 📉 **Контроль виконання:** Відстеження статусів замовлень (Waiting -> In Progress -> Completed).
- 👥 **Рольова модель:** 
  - **Менеджер:** Управління клієнтами, призначення майстрів, контроль за всіма замовленнями.
  - **Майстер:** Перегляд призначених робіт, зміна статусів виконання.
- 💾 **Надійне збереження даних:** Використання JDBC з пулом з'єднань HikariCP.
- 🔄 **Автоматичні міграції:** Контроль версій бази даних за допомогою Flyway.
- 🔐 **Безпека:** Захищений вхід та шифрування паролів.

---

## 🏗 Архітектура

Проєкт побудований за принципами **Layered Architecture** (Багатошарова архітектура):

1.  **UI Layer (JavaFX):** Інтерактивний графічний інтерфейс.
2.  **Controller Layer:** Обробка запитів та логіка взаємодії з UI.
3.  **Service Layer:** Бізнес-логіка програми.
4.  **Repository Layer (JDBC):** Взаємодія з базою даних MySQL.
5.  **DTO Layer:** Обмін даними між шарами без прив'язки до сутностей БД.

---

## 📥 Встановлення та запуск

### Вимоги
- **JDK 21**
- **MySQL Server**
- **Maven** (входить до складу сучасних IDE)

### Швидкий старт
1. **Клонуйте репозиторій:**
   ```bash
   git clone https://github.com/YOUR_USERNAME/YOUR_REPO_NAME.git
   cd YOUR_REPO_NAME
   ```

2. **Налаштуйте базу даних:**
   Створіть БД `mysuperproject` у вашому MySQL та оновіть пароль у файлі `src/main/resources/application.properties`.

3. **Запустіть додаток:**
   ```bash
   mvn clean package
   mvn javafx:run
   ```

---

## 📦 Створення інсталяторів

Проєкт підтримує автоматичне створення нативних інсталяторів (`.exe`, `.deb`, `.dmg`) за допомогою `jpackage`.

- **Windows:** `.\packaging\create-installer-win.ps1`
- **Linux/macOS:** `./packaging/create-installer-unix.sh`

---

## 🛤 Roadmap

- [x] Основна інфраструктура та БД
- [x] Авторизація та ролі
- [x] Управління замовленнями
- [ ] Система відгуків та рейтингів
- [ ] Генерація звітів у PDF
- [ ] Автоматичні сповіщення клієнтів

---

## 👨‍💻 Автор
[Ваше ім'я або нікнейм]

---
*Generated with ❤️ by Gemini CLI*
