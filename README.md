# 📓 Journal Application API

A production-grade backend for a smart personal journaling platform, built using Java 21 and Spring Boot 3 ☕.

The application provides a secure, scalable RESTful API with JWT & OAuth2 authentication, full CRUD journaling, and event-driven async processing. It integrates Weather APIs, AI-based Sentiment Analysis, Apache Kafka, Redis caching, and dynamic runtime configuration, making it a strong real-world backend system. 🚀

---

## ✨ Key Features

### 🔐 Authentication & Security
- Stateless authentication using JWT (JSON Web Tokens)
- OAuth2 login support (Google)
  - Auto user registration on first OAuth login
  - JWT issued after successful OAuth authentication
- Password hashing with BCrypt
- Role-based access control (USER, ADMIN)
- Built with Spring Security 6

---

### 📖 API Documentation
- Integrated Swagger UI (OpenAPI 3)
- Categorized endpoints: Public / Auth, User, Journal, Admin

---

### 🤖 Smart Journaling

#### 🌦 Weather Integration
- Real-time weather captured while creating journal entries
- Weather API URL fetched dynamically from MongoDB

#### 🧠 Sentiment Analysis
- Emotion detection (Happy, Sad, Angry, Anxious)
- Processed asynchronously via Kafka consumers

#### 🚀 Redis Caching
- Weather API responses cached for 5 minutes
- Reduced external API calls and improved performance

---

### ⚡ Async Processing & Scheduling
- Event-driven architecture using Apache Kafka
- Weekly sentiment summary scheduler
  - Runs every Sunday at 9:00 AM
  - Publishes summaries to Kafka
  - Sends email notifications
- Dynamic application configuration
  - Config stored in MongoDB
  - Refreshed in-memory without restart
  - Admin-controlled cache refresh

---

### 👮 Admin Capabilities
- View all registered users
- Promote users to ADMIN
- Clear application cache dynamically
- Monitor journal activity

---

## 🛠️ Tech Stack

| Layer        | Technology |
|-------------|------------|
| Language     | Java 21 |
| Framework    | Spring Boot 3.4.1 |
| Security     | Spring Security, JWT, OAuth2 |
| Database     | MongoDB |
| Cache        | Redis |
| Messaging    | Apache Kafka |
| API Docs     | SpringDoc OpenAPI |
| Build Tool   | Maven |
| Email        | Spring Mail (SMTP) |

---

## 🚀 Getting Started

### ✅ Prerequisites
- JDK 21
- Docker & Docker Compose
- MongoDB
- Redis
- Kafka & Zookeeper
- Maven
- SMTP email account
- Weather API key
- Google OAuth2 credentials

---

## ⚙️ Installation & Setup

### 1️⃣ Clone Repository
git clone https://github.com/swapnil-0229/Journal_Project.git  
cd Journal_Project

---

### 2️⃣ Start Infrastructure
Ensure MongoDB, Redis, Kafka, and Zookeeper are running:

docker-compose up -d

---

### 3️⃣ Application Configuration
Update `src/main/resources/application.properties`:

# MongoDB
spring.data.mongodb.uri=mongodb://localhost:27017/journaldb

# Redis
spring.redis.host=localhost  
spring.redis.port=6379

# Kafka
spring.kafka.bootstrap-servers=localhost:9092  
spring.kafka.consumer.group-id=weekly-sentiment-group

# JWT
jwt.secret=YOUR_SECRET_KEY  
jwt.expiration=86400000

# Email
spring.mail.host=smtp.gmail.com  
spring.mail.port=587  
spring.mail.username=YOUR_EMAIL@gmail.com  
spring.mail.password=YOUR_APP_PASSWORD  
spring.mail.properties.mail.smtp.auth=true  
spring.mail.properties.mail.smtp.starttls.enable=true

# Weather API
weather.api.key=YOUR_WEATHER_API_KEY

# OAuth2 Google
spring.security.oauth2.client.registration.google.client-id=YOUR_CLIENT_ID  
spring.security.oauth2.client.registration.google.client-secret=YOUR_CLIENT_SECRET  
spring.security.oauth2.client.registration.google.scope=openid,profile,email

---

### 4️⃣ MongoDB Dynamic Config (Mandatory)
Insert the following document into the `config_journal_app` collection:

{
  "key": "WEATHER_API",
  "value": "http://api.weatherapi.com/v1/current.json?key=<apiKey>&q=<city>"
}

---

### 5️⃣ Build & Run
./mvnw clean install  
java -jar target/journal-app-0.0.1-SNAPSHOT.jar

---

## 📄 API Documentation

Swagger UI:  
http://localhost:8080/swagger-ui/index.html

OpenAPI Spec:  
http://localhost:8080/v3/api-docs

---

## 🔐 Authentication Guide

### JWT Login
- Call POST /public/login
- Copy JWT token from response
- In Swagger UI → Authorize
- Enter:  
  Bearer <JWT_TOKEN>

---

### OAuth2 Login (Google)
- Authenticate with Google
- Callback endpoint: /auth/google/callback
- Auth code exchanged for JWT
- Use JWT for all protected APIs

---

## 🧩 API Endpoints

### 🟢 Public / Auth
- POST /public/signup – Register user
- POST /public/login – Login
- GET /public/health-check – System status
- GET /auth/google/callback – OAuth2 JWT exchange

---

### 📘 Journal
- GET /journal – Get all entries
- GET /journal/{id} – Get entry
- POST /journal – Create entry
- PUT /journal/{id} – Update entry
- DELETE /journal/{id} – Delete entry

---

### 👤 User
- GET /user/me – Current user profile
- PUT /user/update – Update profile

---

### 🔴 Admin
- GET /admin/all-users – List users
- PUT /admin/promote/{userId} – Promote to ADMIN
- GET /admin/clear-app-cache – Refresh config/cache

---

## 🧠 Architecture Highlights
- Stateless Authentication using JWT & OAuth2
- Event-Driven Architecture with Kafka
- Cache-Aside Pattern with Redis
- Asynchronous sentiment processing
- Runtime dynamic configuration without redeploy
- Layered architecture (Controller, Service, Repository)

---

## 📄 License
This project is licensed under the MIT License.

⭐ If you find this project useful, consider giving it a star!
