# 🛠 Платформа для онлайн-замовлення та контролю виконання сервісних послуг

[![Java Version](https://img.shields.io/badge/Java-21-blue.svg)](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Build Status](https://github.com/hitoshi-aa/online-service-platform/actions/workflows/build.yml/badge.svg)](https://github.com/hitoshi-aa/online-service-platform/actions)

Комплексне рішення для автоматизації процесів замовлення сервісних послуг та моніторингу їх виконання в режимі реального часу. Поєднує потужність **Spring Boot** на бекенді та інтерактивність **JavaFX** на фронтенді.

---

## 🌟 Основні можливості

- 🛍 **Онлайн-замовлення:** Зручний інтерфейс для вибору послуг та миттєвого оформлення заявок.
- 📉 **Контроль виконання:** Відстеження статусів замовлень у реальному часі (`Waiting` -> `In Progress` -> `Completed`).
- 👥 **Рольова модель доступу:** 
  - **Менеджер:** Повний контроль над базою клієнтів, призначення майстрів на замовлення та управління каталогом послуг.
  - **Майстер:** Особистий кабінет для перегляду призначених завдань та оновлення статусів їх виконання.
- 💾 **Надійне збереження даних:** Пряма взаємодія з БД через JDBC з використанням пулу з'єднань **HikariCP**.
- 🔄 **Версіонування бази даних:** Автоматичне керування схемою БД за допомогою **Flyway**.
- 🔐 **Безпека:** Захищений вхід з шифруванням паролів за алгоритмом **BCrypt**.

---

## 🏗 Архітектура

Проєкт реалізований з використанням багатошарової архітектури (**Layered Architecture**):

1.  **UI Layer (JavaFX):** Графічний інтерфейс користувача (FXML + CSS).
2.  **Controller Layer:** Обробка дій користувача та зв'язок між UI та бізнес-логікою.
3.  **Service Layer:** Реалізація основної бізнес-логіки та правил системи.
4.  **Repository Layer (JDBC):** Шар доступу до даних, оптимізований для роботи з MySQL.
5.  **DTO Layer:** Об'єкти передачі даних для забезпечення незалежності шарів програми.

---

## 📥 Встановлення та налаштування

### Вимоги
- **JDK 21** (рекомендується GraalVM або OpenJDK)
- **MySQL Server 8.0+**
- **Maven** (вбудований у більшість сучасних IDE)

### Швидкий старт
1. **Клонуйте репозиторій:**
   ```bash
   git clone https://github.com/hitoshi-aa/online-service-platform.git
   cd online-service-platform
   ```

2. **Налаштуйте базу даних:**
   - Створіть базу даних у MySQL:
     ```sql
     CREATE DATABASE mysuperproject;
     ```
   - Оновіть дані підключення (username/password) у файлі:
     `src/main/resources/application.properties`

3. **Запустіть додаток:**
   ```bash
   mvn clean package
   mvn javafx:run
   ```

---

## 📦 Створення нативних інсталяторів

Проєкт підтримує автоматичне створення інсталяторів для різних ОС за допомогою `jpackage`.

- **Windows:** запустіть `.\packaging\create-installer-win.ps1` (створить `.exe`).
- **Linux/macOS:** запустіть `./packaging/create-installer-unix.sh` (створить `.deb`, `.rpm` або `.dmg`).

*Для Windows потрібен встановлений [WiX Toolset](https://wixtoolset.org/releases/).*

---

## 🛤 План розвитку (Roadmap)

- [x] Розробка ядра системи та схеми БД
- [x] Система авторизації та розподіл ролей
- [x] Функціонал створення та видалення замовлень
- [x] Автоматизація збірки (GitHub Actions)
- [ ] Система відгуків та клієнтських рейтингів
- [ ] Генерація звітів про виконані роботи у форматі PDF
- [ ] Темна тема для інтерфейсу JavaFX

---

## 👨‍💻 Автор
hitoshi-aa

---
*Generated with ❤️ by Gemini CLI*
