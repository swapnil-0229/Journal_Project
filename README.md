# 📓 Journal Application API  
### Enterprise-Grade Backend System (Java 21 • Spring Boot • Kafka • Redis)

A **production-ready, scalable backend** for a smart personal journaling platform, designed to demonstrate **modern backend engineering practices** including **secure authentication**, **event-driven architecture**, **asynchronous processing**, and **runtime configuration management**.

This project reflects **real-world system design**, not a CRUD demo.

---

## 🚀 Why This Project Stands Out

✔ Built with **Java 21 & Spring Boot 3**  
✔ Implements **JWT & OAuth2 authentication (Google)**  
✔ Uses **Apache Kafka** for async, event-driven workflows  
✔ Integrates **Redis caching** to optimize external API calls  
✔ Supports **dynamic runtime configuration** without redeployments  
✔ Designed with **clean architecture & separation of concerns**

---

## 🧠 System Design & Architecture

### 🔐 Authentication & Security
- Stateless **JWT-based authentication**
- **OAuth2 (Google Login)** with auto user provisioning
- Secure password storage using **BCrypt**
- **Role-based access control** (USER / ADMIN)
- Implemented using **Spring Security 6**

---

### ⚡ Event-Driven & Asynchronous Processing
- **Apache Kafka** used for:
  - Background sentiment analysis
  - Weekly sentiment summary generation
- Decoupled producers and consumers for scalability
- Prevents blocking user-facing API requests

---

### 🤖 Smart Features (Real-World Integrations)

#### 🌦 Weather-Aware Journaling
- Captures **real-time weather data** on journal creation
- External Weather API calls optimized using **Redis cache**
- Cache TTL: **5 minutes**

#### 😊 AI-Based Sentiment Analysis
- Emotion classification:
  - Happy
  - Sad
  - Angry
  - Anxious
- Executed asynchronously via Kafka consumers
- Weekly aggregated sentiment reports

---

### 🔁 Dynamic Runtime Configuration
- Application config stored in **MongoDB**
- In-memory refresh without application restart
- Admin-controlled cache & config reload
- Demonstrates **zero-downtime configuration management**

---

### 👮 Admin & Observability Features
- View and manage all registered users
- Promote users to ADMIN role
- Clear application cache dynamically
- Monitor journal activity

---

## 🛠️ Tech Stack (Industry-Relevant)

| Category | Technology |
|--------|-----------|
| Language | Java 21 |
| Framework | Spring Boot 3.4.1 |
| Security | Spring Security, JWT, OAuth2 |
| Database | MongoDB |
| Cache | Redis |
| Messaging | Apache Kafka |
| API Docs | Swagger / OpenAPI 3 |
| Build Tool | Maven |
| Email | SMTP (Spring Mail) |
| Auth Provider | Google OAuth2 |

---

## 📄 API Documentation
- **Swagger UI:**  
  `http://localhost:8080/swagger-ui/index.html`
- **OpenAPI Spec:**  
  `http://localhost:8080/v3/api-docs`

---

## 🧩 Key API Endpoints (Overview)

### Public / Auth
- `POST /public/signup`
- `POST /public/login`
- `GET /auth/google/callback`

### Journal
- `POST /journal`
- `GET /journal`
- `PUT /journal/{id}`
- `DELETE /journal/{id}`

### Admin
- `GET /admin/all-users`
- `PUT /admin/promote/{userId}`
- `GET /admin/clear-app-cache`

---

## 🧪 Engineering Practices Demonstrated

- Stateless API design
- Clean layered architecture
- Cache-aside strategy with Redis
- Async messaging with Kafka
- Secure authentication workflows
- External API integration
- Runtime configuration management
- Production-grade error handling

---

## 🏁 Quick Setup (For Reviewers)

```bash
git clone https://github.com/swapnil-0229/Journal_Project.git
cd Journal_Project
docker-compose up -d
./mvnw clean install
java -jar target/journal-app-0.0.1-SNAPSHOT.jar


📄 License
----------

This project is licensed under the MIT License.