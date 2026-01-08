# 📓 Journal Application API

A robust backend application for a personal journaling service, built with **Java 21** and **Spring Boot 3**. ☕ This project provides a secure RESTful API for user authentication and full **CRUD** (Create, Read, Update, Delete) functionality for journal entries.

It goes beyond basic journaling by integrating **External APIs for Weather**, **Sentiment Analysis**, **Kafka Messaging**, and **Redis Caching** for a high-performance, scalable architecture. 🚀

---

## ✨ Features

* **🔐 Secure Authentication**: 
    * Implements **Stateless Authentication** using **JWT (JSON Web Tokens)**.
    * Passwords are securely hashed using **BCrypt**.
    * Role-based access control (User vs. Admin).

* **📖 API Documentation**: 
    * Integrated **Swagger UI (OpenAPI 3)** for interactive API exploration and testing.
    * Neatly categorized endpoints (Public, User, Journal, Admin).

* **🤖 Smart Journaling**:
    * **Weather Integration**: Real-time weather tracking stored with entries.
    * **Sentiment Analysis**: AI-powered analysis of journal entries (Happy, Sad, Angry, Anxious).
    * **Redis Caching**: Weather API responses are cached for 5 minutes to optimize performance and reduce external calls.

* **⚡ Async Processing & Scheduling**:
    * **Event-Driven Architecture**: Uses **Apache Kafka** to decouple sentiment analysis processing.
    * **Weekly Reports**: A scheduler fetches weekly summaries every Sunday at 9:00 AM, pushes them to a Kafka topic, and sends email notifications. 📧
    * **Dynamic Config**: Application settings can be updated in the database and refreshed in-memory without restarting.

* **👮 Admin Dashboard**: 
    * Manage users, add new admins, and refresh application configuration (App Cache) dynamically.

---

## 🛠️ Tech Stack

* **Language**: Java 21
* **Framework**: Spring Boot 3.4.1
* **Database**: MongoDB (NoSQL)
* **Caching**: Redis
* **Messaging**: Apache Kafka & Zookeeper
* **Documentation**: SpringDoc OpenAPI (Swagger UI)
* **Security**: Spring Security & JWT
* **Build Tool**: Apache Maven

---

## 🚀 Getting Started

### ✅ Prerequisites

* Java Development Kit (JDK) 21
* Docker & Docker Compose (Recommended for running Kafka, Zookeeper, Redis)
* Maven
* SMTP account (e.g., Gmail) for emails
* Weather API Key (e.g., from weatherapi.com)

### ⚙️ Installation & Setup

1.  **Clone the repository:**
    ```bash
    git clone <YOUR_REPOSITORY_URL>
    cd journal-app
    ```

2.  **Start Infrastructure (Docker):**
    Use the provided `docker-compose.yml` to start Kafka and Zookeeper. Ensure Redis and MongoDB are also running.
    ```bash
    docker-compose up -d
    ```

3.  **Configure `application.properties`:**
    Update `src/main/resources/application.properties` with your credentials:

    ```properties
    # Database
    spring.data.mongodb.uri=mongodb://localhost:27017/journaldb

    # Redis
    spring.redis.host=localhost
    spring.redis.port=6379

    # Kafka
    spring.kafka.bootstrap-servers=localhost:9092
    spring.kafka.consumer.group-id=weekly-sentiment-group

    # Email
    spring.mail.host=smtp.gmail.com
    spring.mail.username=YOUR_EMAIL@gmail.com
    spring.mail.password=YOUR_APP_PASSWORD

    # Weather API Key
    weather.api.key=YOUR_API_KEY
    ```

4.  **Database Configuration (Critical Step):**
    The application fetches the Weather API URL dynamically from MongoDB. You **must** insert the following document into the `config_journal_app` collection in your MongoDB database:

    ```json
    {
      "key": "WEATHER_API",
      "value": "[http://api.weatherapi.com/v1/current.json?key=](http://api.weatherapi.com/v1/current.json?key=)<apiKey>&q=<city>"
    }
    ```
    *The application will automatically replace `<apiKey>` and `<city>` at runtime.*

5.  **Build and Run:**
    ```bash
    # Using Maven Wrapper
    ./mvnw clean install
    
    # Run the application
    java -jar target/journal-app-0.0.1-SNAPSHOT.jar
    ```

---

## 📄 API Documentation (Swagger UI)

This project includes **Swagger UI** for easy API testing and visualization.

* **URL**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
* **JSON Spec**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

### How to Authenticate in Swagger:
1.  Call the **/public/login** endpoint to get a JWT token.
2.  Click the **Authorize** button at the top right of the Swagger page.
3.  Enter the token in the format: `Bearer <your_token>`.
4.  Now you can test protected endpoints (User, Journal, Admin).

---

## 📝 Key API Endpoints

### 🟢 Public
* `POST /public/signup`: Register a new user.
* `POST /public/login`: Authenticate and receive a JWT.

### 👤 User & Journal
* `GET /journal`: Fetch all journal entries.
* `POST /journal`: Create an entry with sentiment & weather data.
* `PUT /user/update`: Update profile credentials.

### 🔴 Admin
* `GET /admin/all-users`: View all registered users.
* `GET /admin/clear-app-cache`: Refresh dynamic configuration.

---

## 📄 License
This project is licensed under the **MIT License**.