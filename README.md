# 🎡 Wheel of Fortune (Spring Boot Web App)

**Wheel of Fortune** is a full-stack web application inspired by the popular TV game show. The project was created as a **portfolio project**, demonstrating backend skills in **Java + Spring Boot** and integration with a frontend application.

The application allows users to play a Wheel of Fortune–style game, manage user accounts, and work with a relational database. The project focuses on **clean architecture, testability, and real-world backend practices**.

---

## 🚀 Project Goals

* build a complete web application suitable for a developer portfolio
* gain practical experience with:

  * Java + Spring Boot
  * REST API design
  * relational databases (PostgreSQL)
  * application security (password hashing)
  * unit testing
* demonstrate the refactoring process (console application → web application)
* prepare the project for online deployment

---

## 🧩 Features

### 👤 Users

* user registration
* user login
* guest mode (no database persistence)
* secure password hashing using **BCrypt**

### 🎮 Game

* random phrase selection from the database:
  * proverbs
  * movie titles
  * actors
* classic *Wheel of Fortune* gameplay mechanics
* game logic separated from the presentation layer

### 🌐 Backend

* REST API handling:
  * users
  * gameplay
  * game data
* input validation
* centralized error handling

---

## 🛠️ Technologies

### Backend

* **Java 17+**
* **Spring Boot**
* **Spring Web**
* **Spring Data JPA**
* **Spring Security** (basic configuration)
* **Hibernate**
* **PostgreSQL**
* **BCrypt**
* **JUnit 5**

### Frontend

* **HTML, CSS**
* **HTMX** – dynamic UI interactions
* **Thymeleaf** - server-side HTML templating
* communication with the backend via REST API

---

## 🗂️ Application Architecture

* `controller` – REST API layer (HTTP)
* `service` – business logic (game and user services)
* `repository` – database access layer (JPA)
* `model` / `entity` – domain entities
* `dto` – data transfer objects
* `config` – application and security configuration
* `test` – unit tests

The architecture follows **separation of concerns** and Spring Boot best practices.

---

## ▶️ See project at:

---

## 🧪 Testing

The project includes unit tests for core business logic.

Run tests with:

```bash
./mvnw test
```

---

## 🔮 Future Improvements

* full backend–frontend integration
* game history persistence
* player rankings
* JWT-based authentication
* Docker containerization
* cloud deployment

---

## 📌 Project Status

The project is **actively developed** as part of a Java / Spring Boot backend portfolio.

---

## 📄 License

Educational project – free to use for non-commercial purposes.
