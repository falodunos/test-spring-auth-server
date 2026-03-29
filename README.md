# Spring Boot Authentication & Authorization System

## Overview

This project demonstrates a **modular authentication and authorization system** built with Spring Boot.

It uses **JWT (JSON Web Token)** for stateless authentication and is designed with a **reusable starter module**.

---

## Project Structure
sbsc-test-authorization-service/
├── core-security-starter/ # Reusable security library
├── sample-application/ # Demo application using the starter

---

## Features

- JWT-based authentication
- Role-based authorization (RBAC)
- Stateless security (no sessions)
- Standardized JSON error responses
- Clean modular architecture

---

## Authentication Flow

1. User sends login request with username & password
2. Server validates credentials
3. Server returns a JWT token
4. Client sends token in request header:

Authorization: Bearer <TOKEN>
5. JWT is validated for every request

---

## API Endpoints

| Endpoint | Description | Access |
|----------|-------------|--------|
| `/api/public/health` | Health check | Public |
| `/api/public/auth/login` | Login endpoint | Public |
| `/api/user/me` | Get current user | Authenticated |
| `/api/admin/users` | List users | ADMIN only |

---

## 🧪 Example Requests

### 🔹 Login

```bash
curl -X POST http://localhost:8080/api/public/auth/login \
-H "Content-Type: application/json" \
-d '{"username":"admin","password":"password"}'
```

### 🔹Access Protected Endpoint
```bash
curl http://localhost:8080/api/user/me \
  -H "Authorization: Bearer <TOKEN>"
```

---

### Configuration

```yaml
security:
  jwt:
    secret: <base64-encoded-secret>
    expiration: 3600000
```
---

###  How to Run

```bash
mvn clean install
mvn spring-boot:run -pl sample-application
```
---

### Running Tests
```bash
mvn test
```
