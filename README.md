# Journal Application API

A robust, scalable journaling backend built with Spring Boot, Spring Security, and MongoDB.

This project provides a secure REST-style API for journaling functions, integrates additional services like Weather and Sentiment Analysis, and supports asynchronous workflows via Kafka and Redis caching.

---

## Key Features

- **User Authentication & Authorization**
  - JWT-based stateless security
  - BCrypt hashed passwords
  - Role-based access control (User, Admin)

- **Secure & Expandable REST API**
  - Endpoints for user signup, login, journal entry CRUD
  - Swagger UI / OpenAPI documentation for easy testing

- **Smart Integrations**
  - Weather integration for attaching real-time weather to entries
  - AI-based sentiment analysis for journal text

- **Performance & Scalability**
  - Redis caching for external API responses
  - Apache Kafka-based asynchronous processing (weekly summary jobs, notifications)

- **Admin Tools**
  - View users, refresh cache, manage roles

---

## Tech Stack

| Category     | Technology |
|--------------|------------|
| Language     | Java 21 |
| Framework    | Spring Boot 3.4.1 |
| Database     | MongoDB |
| Security     | Spring Security, JWT |
| Messaging    | Apache Kafka, Zookeeper |
| Cache        | Redis |
| Documentation| SpringDoc OpenAPI (Swagger UI) |
| Build Tool   | Maven |

---

## Getting Started

### Prerequisites

- JDK 21
- Docker & Docker Compose (recommended)
- Maven
- SMTP credentials for email dispatch (example: Gmail)
- Weather API key

---

### Installation & Setup

1. **Clone the repository**
```bash
git clone https://github.com/swapnil-0229/Journal_Project.git
cd Journal_Project
```

2. **Start required services**  
Use Docker Compose to start Kafka, Zookeeper, Redis, and MongoDB:
```bash
docker-compose up -d
```

3. **Configure application properties**  
Update `src/main/resources/application.properties`:
```properties
spring.data.mongodb.uri=mongodb://localhost:27017/journaldb
spring.redis.host=localhost
spring.redis.port=6379

spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=weekly-sentiment

spring.mail.host=smtp.gmail.com
spring.mail.username=YOUR_EMAIL@gmail.com
spring.mail.password=YOUR_APP_PASSWORD

weather.api.key=YOUR_API_KEY
```

4. **Build and Run**
```bash
./mvnw clean install
java -jar target/journal-app-0.0.1-SNAPSHOT.jar
```

---

## API Documentation

After running the application:

- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

### Authentication Workflow

1. `POST /public/login` to obtain JWT
2. Use Swagger `Authorize` button and enter:
```
Bearer <your_JWT_token>
```

---

## Key Endpoints

### Public Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/public/signup` | Create a new account |
| POST | `/public/login` | Authenticate and get JWT |

### User / Journal Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/journal` | List journal entries |
| POST | `/journal` | Add entry with sentiment & weather |
| PUT | `/journal/{id}` | Update journal entry |
| DELETE | `/journal/{id}` | Delete journal entry |

### Admin Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/admin/all-users` | List all registered users |
| GET | `/admin/clear-app-cache` | Refresh application cache |

---

## Contributing

Contributions are welcome. To contribute:

1. Fork the repository
2. Create a feature-branch
3. Commit changes with clear messages
4. Open a Pull Request

Please follow common coding conventions and include tests where possible.

---

## License

This project is distributed under the **MIT License**.

---

## About

A RESTful journaling API with enhanced integrations and security — suitable for extending with front-end interfaces or using as a learning resource for modern Spring Boot architecture.
