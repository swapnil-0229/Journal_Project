# 📓 Journal Application API

A robust backend application for a personal journaling service, built with **Java** and the **Spring Boot** framework. ☕ This project provides a secure RESTful API for user authentication and full **CRUD** (Create, Read, Update, Delete) functionality for journal entries, using **MongoDB** as the database. 🔐

---

## ✨ Features

* **Secure User Authentication**: Employs **Spring Security** for user registration and login. Passwords are securely hashed using **BCrypt**. 🛡️
* **RESTful API**: A well-structured API for managing users and their journal entries. 🌐
* **User-Specific Data**: Journal entries are linked to individual user accounts, ensuring data privacy. 👤
* **MongoDB Integration**: Uses **Spring Data MongoDB** for seamless interaction with a NoSQL database. 🍃
* **Transactional Operations**: Ensures data integrity when saving or deleting entries linked to a user. 🔄
* **Health Check Endpoint**: A simple endpoint to verify the application's operational status. ❤️

---

## 🛠️ Tech Stack

* **Framework**: Spring Boot
* **Language**: Java 8
* **Security**: Spring Security
* **Database**: Spring Data MongoDB
* **Build Tool**: Apache Maven
* **Utilities**: Lombok

---

## 🚀 Getting Started

Follow these instructions to get the project up and running on your local machine for development and testing.

### ✅ Prerequisites

Before you begin, ensure you have the following installed:

* Java Development Kit (JDK) 8 or newer
* Apache Maven
* A running instance of MongoDB

### ⚙️ Installation & Setup

1.  **Clone the repository:**

    ```bash
    git clone <YOUR_REPOSITORY_URL>
    cd journal-app
    ```

2.  **Create the `application.properties` file:**

    For security, the configuration file containing database credentials is not included in the repository. You must create it manually.

    Create a new file named `application.properties` inside the `src/main/resources/` directory.

    Add the following configuration, replacing the placeholder with your MongoDB connection string and desired database name.

    ```properties
    # MongoDB Connection String
    spring.data.mongodb.uri=mongodb://localhost:27017/journaldb

    # Server Port (Optional)
    server.port=8080
    ```

3.  **Build the project using the Maven Wrapper:**

    This command will download dependencies and compile the source code.

    * For **macOS/Linux**:

        ```bash
        ./mvnw clean install
        ```

    * For **Windows**:

        ```bash
        ./mvnw.cmd clean install
        ```

4.  **Run the application:**

    ```bash
    java -jar target/journal-app-0.0.1-SNAPSHOT.jar
    ```

    The application will start, and the API will be accessible at `http://localhost:8080`.

---

## 📝 API Endpoints

The following are the primary endpoints exposed by the application.

### ❤️ Health Check

* **GET** `/public/health-check`

    **Description**: A simple endpoint to confirm that the API is running.

    **Response**: `OK`

### 👤 User Management

* **POST** `/public/create_user`

    **Description**: Creates a new user. The user object should be sent in the request body.

    **Request Body**:

    ```json
    {
      "username": "newuser",
      "password": "password123"
    }
    ```

* **PUT** `/user/update`

    **Description**: Updates the currently authenticated user's details.

    **Request Body**:

    ```json
    {
      "username": "updateduser",
      "password": "newpassword123"
    }
    ```
* **DELETE** `/user/delete`

    **Description**: Deletes the currently authenticated user.


### 📓 Journal Entries

**Authentication is required for these endpoints.**

* **GET** `/journal`

    **Description**: Retrieves all journal entries for the authenticated user.

* **POST** `/journal`

    **Description**: Creates a new journal entry for the authenticated user.

    **Request Body**:

    ```json
    {
      "title": "My First Entry",
      "content": "This is the content of my journal entry."
    }
    ```

* **GET** `/journal/id/{myId}`

    **Description**: Retrieves a single journal entry by its unique ID, belonging to the authenticated user.

* **PUT** `/journal/id/{myId}`

    **Description**: Updates an existing journal entry by its ID, belonging to the authenticated user.

    **Request Body**:

    ```json
    {
      "title": "Updated Title",
      "content": "This is the updated content."
    }
    ```

* **DELETE** `/journal/id/{myId}`

    **Description**: Deletes a journal entry by its ID, belonging to the authenticated user.

---

## 📄 License

This project is licensed under the **MIT License**. See the `LICENSE` file for details.