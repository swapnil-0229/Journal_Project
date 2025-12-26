# 📓 Journal Application API

A robust backend application for a personal journaling service, built with **Java** and the **Spring Boot** framework. ☕ This project provides a secure RESTful API for user authentication and full **CRUD** (Create, Read, Update, Delete) functionality for journal entries.

It goes beyond basic journaling by integrating **External APIs for Weather**, **Sentiment Analysis** on journal entries, and automated **Email Notifications**. 🌤️📧 

**New:** Now optimized with **Redis** caching for better performance! 🚀

---

## ✨ Features

* **Secure User Authentication**: Employs **Spring Security** for user registration and login. Passwords are securely hashed using **BCrypt**. 🛡️
* **Smart Journaling**:
    * **Weather Integration**: Fetches and displays real-time weather data based on the user's location (default "Manali"). 
    * **Redis Caching**: Weather API responses are cached in **Redis** for 5 minutes to reduce external API calls and improve response times. ⚡
    * **Sentiment Analysis**: Analyzes journal entries to determine the user's weekly mood (Happy, Sad, Angry, Anxious). 🧠
* **Automated Scheduler**:
    * **Weekly Emails**: Automatically sends an email to users every Sunday with a summary of their sentiment analysis for the last 7 days. 📅
    * **Cache Management**: Periodically clears the application cache to ensure fresh configuration data. 🔄
* **Admin Dashboard**: Dedicated endpoints for admins to manage users and system configurations. 👮
* **RESTful API**: A well-structured API for managing users, journal entries, and admin tasks. 🌐
* **MongoDB Integration**: Uses **Spring Data MongoDB** for seamless interaction with a NoSQL database. 🍃
* **Health Check Endpoint**: A simple endpoint to verify the application's operational status. ❤️

---

## 🛠️ Tech Stack

* **Framework**: Spring Boot 2.7.x
* **Language**: Java 8
* **Security**: Spring Security
* **Database**: MongoDB
* **Caching**: Redis
* **Build Tool**: Apache Maven
* **Mail**: Java Mail Sender
* **Utilities**: Lombok, Spring Boot Actuator

---

## 🚀 Getting Started

Follow these instructions to get the project up and running on your local machine.

### ✅ Prerequisites

* Java Development Kit (JDK) 8 or newer
* Apache Maven
* A running instance of MongoDB
* A running instance of **Redis** (Local or Cloud)
* An SMTP account (e.g., Gmail) for sending emails
* A Weather API Key (e.g., from WeatherAPI.com)

### ⚙️ Installation & Setup

1.  **Clone the repository:**

    ```bash
    git clone <YOUR_REPOSITORY_URL>
    cd journal-app
    ```

2.  **Configure `application.properties`:**

    Create a file named `application.properties` in `src/main/resources/`. Add the following configurations:

    ```properties
    # MongoDB Connection
    spring.data.mongodb.uri=mongodb://localhost:27017/journaldb

    # Redis Configuration
    spring.redis.host=localhost
    spring.redis.port=6379
    # If using Redis Cloud, uncomment and add password:
    # spring.redis.password=YOUR_REDIS_PASSWORD

    # Server Port
    server.port=8080

    # Email Configuration (Required for Sentiment Analysis Emails)
    spring.mail.host=smtp.gmail.com
    spring.mail.port=587
    spring.mail.username=YOUR_EMAIL@gmail.com
    spring.mail.password=YOUR_APP_PASSWORD
    spring.mail.properties.mail.smtp.auth=true
    spring.mail.properties.mail.smtp.starttls.enable=true

    # Weather API Key
    weather.api.key=YOUR_WEATHER_API_KEY
    ```

3.  **Database Configuration (App Cache):**

    The application stores the external API URL in the database to allow dynamic updates without code changes. You must insert the following document into the `config_journal_app` collection in your MongoDB:

    ```json
    {
      "key": "WEATHER_API",
      "value": "[http://api.weatherapi.com/v1/current.json?key=](http://api.weatherapi.com/v1/current.json?key=)<apiKey>&q=<city>"
    }
    ```
    *(Note: `<apiKey>` and `<city>` are placeholders used by the application code.)*

4.  **Build and Run:**

    * **Using Maven Wrapper:**
        ```bash
        ./mvnw clean install  # macOS/Linux
        ./mvnw.cmd clean install # Windows
        ```

    * **Run the JAR:**
        ```bash
        java -jar target/journal-app-0.0.1-SNAPSHOT.jar
        ```

---

## 📝 API Endpoints

### ❤️ Public Endpoints

* **GET** `/public/health-check`
    * **Description**: Checks if the server is running.
    * **Response**: `OK`

* **POST** `/public/create-user`
    * **Description**: Register a new user.
    * **Body**:
        ```json
        {
            "username": "newuser",
            "password": "password123",
            "email": "user@example.com",
            "sentimentAnalysis": true
        }
        ```

### 👤 User Management (Authenticated)

* **GET** `/user`
    * **Description**: Returns a greeting message including the current weather "feels like" temperature and condition.
    * **Response**: `"Hi [username], Weather feels like 25.0 , and can be described by Sunny"`

* **PUT** `/user/update`
    * **Description**: Updates the authenticated user's credentials.
    * **Body**: `{"username": "...", "password": "..."}`

* **DELETE** `/user/delete`
    * **Description**: Deletes the authenticated user account.

### 📓 Journal Entries (Authenticated)

* **GET** `/journal`
    * **Description**: Get all journal entries for the user.

* **POST** `/journal`
    * **Description**: Create a new journal entry.
    * **Body**:
        ```json
        {
            "title": "My Day",
            "content": "Today was a good day...",
            "sentiment": "HAPPY"
        }
        ```

* **GET** `/journal/id/{myId}`
    * **Description**: Get a specific entry by ID.

* **PUT** `/journal/id/{myId}`
    * **Description**: Update a specific entry.

* **DELETE** `/journal/id/{myId}`
    * **Description**: Delete a specific entry.

### 👮 Admin Dashboard (Requires ROLE_ADMIN)

* **GET** `/admin/all-users`
    * **Description**: Retrieve a list of all users in the system.

* **POST** `/admin/add-admin`
    * **Description**: Create a new user with Admin privileges.
    * **Body**: Same as create-user.

* **GET** `/admin/clear-app-cache`
    * **Description**: Force reload of configuration data (like API URLs) from the database into the application cache.

---

## 📄 License

This project is licensed under the **MIT License**. See the `LICENSE` file for details.