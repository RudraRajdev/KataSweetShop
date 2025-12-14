#  Kata Sweet Shop – Backend API

A **Spring Boot REST API** for managing a sweet shop inventory with **JWT authentication**, **role-based authorization**, and **search functionality**.
This project was bootstrapped using Spring Initializr
(https://start.spring.io) to generate the base Spring Boot
application structure and dependency configuration.

---

## Features
- JWT Authentication
- Role-based authorization (ADMIN / USER)
- Sweet CRUD operations
- Search sweets by name, category, price range
- Purchase & restock sweets
- Centralized exception handling
- PostgreSQL + JPA
- Tested with Postman & Spring Boot Test

---

##  Tech Stack
- Java 21
- Spring Boot 
- Spring Security
- JWT (jjwt) 0.12.6
- PostgreSQL
- Hibernate
- Maven

---

##  Setup
1. Clone repo
2. Configure PostgreSQL
3. Update application.properties
4. Run:
```bash
mvn clean install
mvn spring-boot:run
```

---

##  API Endpoints
### Auth
- POST /api/auth/register
- POST /api/auth/login

### Sweets
- GET /api/sweets
- GET /api/sweets/search
- POST /api/sweets (ADMIN)
- PUT /api/sweets/{id} (ADMIN)
- DELETE /api/sweets/{id} (ADMIN)
- POST /api/sweets/{id}/purchase
- POST /api/sweets/{id}/restock (ADMIN)

---

##  My AI Usage

### Tools Used
- ChatGPT

### How Used
- 50% assistance for JWT authentication
- Test generation
- Debugging Hibernate, Spring Security issues

### Reflection
AI improved productivity and debugging speed, but all logic was reviewed and manually implemented.

---

##  Author
Rudra Rajdev
