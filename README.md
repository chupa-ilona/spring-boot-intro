

<div align="center">
  <h1>🚀 Spring Boot Intro API</h1>

  <p align="center">
    <b>A secure RESTful web application built with Spring Boot to demonstrate modern Java backend development practices.</b>
    <br />
    <a href="http://localhost:8080/swagger-ui/index.html">
      <strong>Explore the API Documentation »</strong>
    </a>
  </p>

  <p align="center">
    <img src="https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 17"/>
    <img src="https://img.shields.io/badge/Spring_Boot-3.2.5-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" alt="Spring Boot"/>
    <img src="https://img.shields.io/badge/MySQL-8-4479A1?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL"/>
    <img src="https://img.shields.io/badge/Security-JWT-black?style=for-the-badge&logo=jsonwebtokens&logoColor=white" alt="JWT"/>
    <img src="https://img.shields.io/badge/Liquibase-2962FF?style=for-the-badge&logo=liquibase&logoColor=white" alt="Liquibase"/>
    <img src="https://img.shields.io/badge/CI-GitHub_Actions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white" alt="GitHub Actions"/>
  </p>
</div>

---

<details open>
<summary><b>📑 Table of Contents</b></summary>

1. [About The Project](#-about-the-project)
2. [Technologies & Tools](#️-technologies--tools)
3. [Core Features](#-core-features)
4. [Project Structure](#-project-structure)
5. [API Endpoints Reference](#-api-endpoints-reference)
6. [Getting Started](#️-getting-started)
7. [Testing](#-testing)
8. [Code Quality](#-code-quality)
9. [Future Improvements](#-future-improvements)

</details>

## 🌟 About The Project

Spring Boot Intro API is a RESTful web application designed to demonstrate the implementation of modern backend development practices using the Spring ecosystem.

The project focuses on building secure APIs with JWT authentication, database version control, validation, exception handling, automated testing, and clean layered architecture.

It was developed as part of the Spring Boot learning program and serves as a portfolio project showcasing practical Java backend development skills.

---

## 🛠️ Technologies & Tools

| Category                | Technology                                        |
| :---------------------- | :------------------------------------------------ |
| **Core**                | Java 17, Spring Boot 3.2.5                        |
| **Database & ORM**      | MySQL, Spring Data JPA, Hibernate                 |
| **Database Migrations** | Liquibase                                         |
| **Security**            | Spring Security, JWT                              |
| **Validation**          | Jakarta Bean Validation                           |
| **Mapping**             | MapStruct                                         |
| **Documentation**       | Swagger / OpenAPI                                 |
| **Testing**             | JUnit 5, Testcontainers, Spring Security Test, H2 |
| **Utilities**           | Lombok                                            |
| **CI/CD**               | GitHub Actions                                    |
| **Code Quality**        | Checkstyle                                        |

---

## 🚀 Core Features

✅ User registration

✅ User authentication using JWT

✅ Role-based authorization

✅ Secure password storage

✅ Request validation

✅ Global exception handling

✅ Database migrations with Liquibase

✅ Interactive Swagger documentation

✅ DTO pattern and entity mapping using MapStruct

✅ Layered architecture implementation

✅ Automated testing with Testcontainers

---

## 📂 Project Structure

```text
src
├── main
│   ├── java
│   │   └── spring.springbootintro
│   │       ├── config
│   │       ├── controller
│   │       ├── dto
│   │       ├── exception
│   │       ├── mapper
│   │       ├── model
│   │       ├── repository
│   │       ├── security
│   │       ├── service
│   │       ├── validation
│   │       └── SpringBootIntroApplication
│   └── resources
│       ├── db.changelog
│       └── application.properties
└── test
```

---

## 📡 API Endpoints Reference

> Full API documentation is available via Swagger UI after starting the application.

| HTTP Method | Endpoint             | Description                        | Access |
| :---------- | :------------------- | :--------------------------------- | :----- |
| `POST`      | `/auth/registration` | Register a new user                | Public |
| `POST`      | `/auth/login`        | Authenticate and receive JWT token | Public |

Protected endpoints require a valid JWT token:

```http
Authorization: Bearer your_jwt_token
```

---

## ⚙️ Getting Started

### Prerequisites

* Java 17+
* Maven 3.9+
* MySQL Server

### Clone the Repository

```bash
git clone https://github.com/chupa-ilona/spring-boot-intro.git
cd spring-boot-intro
```

### Configure Database

Update the database settings in `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Build the Project

```bash
mvn clean install
```

### Run the Application

```bash
mvn spring-boot:run
```

The application will start at:

```text
http://localhost:8087
```

Swagger UI:

```text
http://localhost:8087/swagger-ui/index.html
```

---

## 🧪 Testing

Run all tests:

```bash
mvn test
```

The project includes:

* Unit Tests
* Integration Tests
* Testcontainers
* H2 Database
* Spring Security Test support

---

## 📋 Code Quality

The project follows clean code practices and includes:

* Checkstyle validation during build
* Layered architecture
* DTO pattern
* Separation of concerns
* GitHub Actions continuous integration

---

## 🔮 Future Improvements

* Refresh Token support
* Email verification
* Password reset functionality
* User profile management
* Increased test coverage
* Docker support

---

## 👩‍💻 Author

**Ilona Chupa**

GitHub: https://github.com/chupa-ilona

---
