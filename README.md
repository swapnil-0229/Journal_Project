📓 Journal Application API
==========================

A production-grade backend for a smart personal journaling platform, built using **Java 21** and **Spring Boot 3**. ☕

The application provides a secure, scalable RESTful API with **JWT & OAuth2 authentication**, full CRUD journaling, and **event-driven async processing**. It integrates Weather APIs, AI-based Sentiment Analysis, Apache Kafka, Redis caching, and dynamic runtime configuration, making it a strong real-world backend system. 🚀

✨ Key Features
--------------

### 🔐 Authentication & Security

*   **Stateless authentication** using JWT (JSON Web Tokens).
    
*   **OAuth2 login support (Google):**
    
    *   Auto user registration on first OAuth login.
        
    *   JWT issued after successful OAuth authentication.
        
*   **Password hashing** with BCrypt.
    
*   **Role-based access control** (USER, ADMIN).
    
*   Built with **Spring Security 6**.
    

### 📖 API Documentation

*   Integrated **Swagger UI** (OpenAPI 3).
    
*   Categorized endpoints: Public / Auth, User, Journal, Admin.
    

### 🤖 Smart Journaling

*   **Weather Integration:**
    
    *   Real-time weather captured while creating journal entries.
        
    *   Weather API URL fetched dynamically from MongoDB.
        
*   **Sentiment Analysis:**
    
    *   Emotion detection (Happy, Sad, Angry, Anxious).
        
    *   Processed asynchronously via Kafka consumers.
        
*   **Redis Caching:**
    
    *   Weather API responses cached for 5 minutes.
        
    *   Reduced external API calls and improved performance.
        

### ⚡ Async Processing & Scheduling

*   **Event-driven architecture** using Apache Kafka.
    
*   **Weekly sentiment summary scheduler:**
    
    *   Runs every Sunday at 9:00 AM.
        
    *   Publishes summaries to Kafka.
        
    *   Sends email notifications.
        
*   **Dynamic application configuration:**
    
    *   Config stored in MongoDB.
        
    *   Refreshed in-memory without restart.
        
    *   Admin-controlled cache refresh.
        

### 👮 Admin Capabilities

*   View all registered users.
    
*   Promote users to admin.
    
*   Clear application cache dynamically.
    
*   Monitor journal activity.
    

🛠️ Tech Stack
--------------

Layer

Technology

**Language**

Java 21

**Framework**

Spring Boot 3.4.1

**Security**

Spring Security, JWT, OAuth2

**Database**

MongoDB

**Cache**

Redis

**Messaging**

Apache Kafka

**API Docs**

SpringDoc OpenAPI

**Build Tool**

Maven

**Email**

Spring Mail (SMTP)

🚀 Getting Started
------------------

### ✅ Prerequisites

*   JDK 21
    
*   Docker & Docker Compose
    
*   MongoDB
    
*   Redis
    
*   Kafka & Zookeeper
    
*   Maven
    
*   SMTP email account
    
*   Weather API key
    
*   Google OAuth2 credentials
    

### ⚙️ Installation & Setup

#### 1️⃣ Clone Repository

Plain textANTLR4BashCC#CSSCoffeeScriptCMakeDartDjangoDockerEJSErlangGitGoGraphQLGroovyHTMLJavaJavaScriptJSONJSXKotlinLaTeXLessLuaMakefileMarkdownMATLABMarkupObjective-CPerlPHPPowerShell.propertiesProtocol BuffersPythonRRubySass (Sass)Sass (Scss)SchemeSQLShellSwiftSVGTSXTypeScriptWebAssemblyYAMLXML`   git clone [https://github.com/swapnil-0229/Journal_Project.git](https://github.com/swapnil-0229/Journal_Project.git)  cd Journal_Project   `

#### 2️⃣ Start Infrastructure

Ensure MongoDB, Redis, Kafka, and Zookeeper are running.

Plain textANTLR4BashCC#CSSCoffeeScriptCMakeDartDjangoDockerEJSErlangGitGoGraphQLGroovyHTMLJavaJavaScriptJSONJSXKotlinLaTeXLessLuaMakefileMarkdownMATLABMarkupObjective-CPerlPHPPowerShell.propertiesProtocol BuffersPythonRRubySass (Sass)Sass (Scss)SchemeSQLShellSwiftSVGTSXTypeScriptWebAssemblyYAMLXML`   docker-compose up -d   `

#### 3️⃣ Application Configuration

Update src/main/resources/application.properties with your credentials:

Plain textANTLR4BashCC#CSSCoffeeScriptCMakeDartDjangoDockerEJSErlangGitGoGraphQLGroovyHTMLJavaJavaScriptJSONJSXKotlinLaTeXLessLuaMakefileMarkdownMATLABMarkupObjective-CPerlPHPPowerShell.propertiesProtocol BuffersPythonRRubySass (Sass)Sass (Scss)SchemeSQLShellSwiftSVGTSXTypeScriptWebAssemblyYAMLXML`   # MongoDB  spring.data.mongodb.uri=mongodb://localhost:27017/journaldb  # Redis  spring.redis.host=localhost  spring.redis.port=6379  # Kafka  spring.kafka.bootstrap-servers=localhost:9092  spring.kafka.consumer.group-id=weekly-sentiment-group  # JWT  jwt.secret=YOUR_SECRET_KEY  jwt.expiration=86400000  # Email  spring.mail.host=smtp.gmail.com  spring.mail.port=587  spring.mail.username=YOUR_EMAIL@gmail.com  spring.mail.password=YOUR_APP_PASSWORD  spring.mail.properties.mail.smtp.auth=true  spring.mail.properties.mail.smtp.starttls.enable=true  # Weather API  weather.api.key=YOUR_WEATHER_API_KEY  # OAuth2 Google  spring.security.oauth2.client.registration.google.client-id=YOUR_CLIENT_ID  spring.security.oauth2.client.registration.google.client-secret=YOUR_CLIENT_SECRET  spring.security.oauth2.client.registration.google.scope=openid,profile,email   `

#### 4️⃣ MongoDB Dynamic Config (Mandatory)

Insert the following document into the config\_journal\_app collection in MongoDB to enable dynamic weather fetching:

Plain textANTLR4BashCC#CSSCoffeeScriptCMakeDartDjangoDockerEJSErlangGitGoGraphQLGroovyHTMLJavaJavaScriptJSONJSXKotlinLaTeXLessLuaMakefileMarkdownMATLABMarkupObjective-CPerlPHPPowerShell.propertiesProtocol BuffersPythonRRubySass (Sass)Sass (Scss)SchemeSQLShellSwiftSVGTSXTypeScriptWebAssemblyYAMLXML`   {    "key": "WEATHER_API",    "value": "[http://api.weatherapi.com/v1/current.json?key=](http://api.weatherapi.com/v1/current.json?key=)&q="  }   `

#### 5️⃣ Build & Run

Plain textANTLR4BashCC#CSSCoffeeScriptCMakeDartDjangoDockerEJSErlangGitGoGraphQLGroovyHTMLJavaJavaScriptJSONJSXKotlinLaTeXLessLuaMakefileMarkdownMATLABMarkupObjective-CPerlPHPPowerShell.propertiesProtocol BuffersPythonRRubySass (Sass)Sass (Scss)SchemeSQLShellSwiftSVGTSXTypeScriptWebAssemblyYAMLXML`   ./mvnw clean install  java -jar target/journal-app-0.0.1-SNAPSHOT.jar   `

📄 API Documentation
--------------------

You can access the full API documentation via Swagger UI once the application is running:

*   **Swagger UI:** [http://localhost:8080/swagger-ui/index.html](https://www.google.com/search?q=http://localhost:8080/swagger-ui/index.html)
    
*   **OpenAPI Spec:** [http://localhost:8080/v3/api-docs](https://www.google.com/search?q=http://localhost:8080/v3/api-docs)
    

🔐 Authentication Guide
-----------------------

### JWT Login

1.  Call POST /public/login with credentials.
    
2.  Copy the JWT token from the response.
    
3.  In Swagger UI, click **Authorize**.
    
4.  Bearer
    

### OAuth2 Login (Google)

1.  Authenticate with Google (client-side or via URL).
    
2.  The callback endpoint /auth/google/callback exchanges the auth code for a JWT.
    
3.  Use the returned JWT for subsequent requests.
    

🧩 API Endpoints
----------------

### 🟢 Public / Auth

*   POST /public/signup - Register a new user
    
*   POST /public/login - Login with username/password
    
*   GET /public/health-check - Check system status
    
*   GET /auth/google/callback - **(New)** Google OAuth2 callback to exchange code for JWT
    

### 📘 Journal

*   GET /journal - Get all entries for logged-in user
    
*   GET /journal/{id} - Get specific entry
    
*   POST /journal - Create new entry
    
*   PUT /journal/{id} - Update entry
    
*   DELETE /journal/{id} - Delete entry
    

### 👤 User

*   GET /user/me - Get current user profile
    
*   PUT /user/update - Update user details
    

### 🔴 Admin

*   GET /admin/all-users - List all users
    
*   PUT /admin/promote/{userId} - Promote a user to ADMIN
    
*   GET /admin/clear-app-cache - Trigger dynamic config refresh
    

🧠 Architecture Highlights
--------------------------

*   **Stateless Authentication:** Uses JWT & OAuth2.
    
*   **Event-Driven:** Kafka-based processing for scalability.
    
*   **Cache-Aside:** Redis strategy to reduce database load.
    
*   **Async Processing:** Sentiment analysis runs in the background.
    
*   **Dynamic Config:** Runtime configuration refresh without redeploy.
    
*   **Layered Architecture:** Clean separation of concerns (Controller, Service, Repository).
    

📄 License
----------

This project is licensed under the MIT License.