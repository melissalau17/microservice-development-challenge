# Online Marketplace Microservices

A **Spring Boot-based microservices architecture** for an online marketplace, designed for scalability, modularity, and maintainability. This project demonstrates a real-world application of microservices using Spring Cloud components.

---

## Table of Contents

1. [Architecture](#architecture)
2. [Microservices](#microservices)
3. [Technologies Used](#technologies-used)
4. [Prerequisites](#prerequisites)
5. [Setup & Installation](#setup--installation)
6. [Running the Application](#running-the-application)
7. [API Documentation](#api-documentation)
8. [Database](#database)
9. [Docker & Docker Compose](#docker--docker-compose)
10. [Project Structure](#project-structure)

---

## Architecture

The project follows a **microservices architecture** pattern with the following components:

* **API Gateway**: Routes incoming requests to appropriate services (Spring Cloud Gateway)
* **Service Discovery**: Manages and registers services dynamically (Eureka Server)
* **User Service**: Handles authentication, authorization, and user management
* **Product Service**: Manages the product catalog, including CRUD operations
* **Cart Service**: Manages shopping cart and orders
* **Database**: PostgreSQL is used for data persistence

**Ports:**

* API Gateway: `8080`
* Eureka Server: `8761`
* User Service: `8081`
* Product Service: `8082`
* Cart Service: `8083`
* PostgreSQL: `5432`

---

## Microservices

### 1. User Service

* Handles user registration, login, and profile management
* Provides JWT-based authentication for secure communication

### 2. Product Service

* CRUD operations for product catalog
* Supports product search and filtering

### 3. Cart Service

* Manages shopping cart
* Handles order creation and tracking

---

## Technologies Used

* Java 17+
* Spring Boot
* Spring Cloud (Gateway, Eureka)
* PostgreSQL
* Docker & Docker Compose
* Maven
* Swagger (API Documentation)

---

## Prerequisites

Before running the application, ensure you have the following installed:

* Java 17+
* Maven 3.6+
* Docker & Docker Compose
* Git

---

## Setup & Installation

### Clone the Repository

```bash
git clone <repository-url>
cd online-marketplace
```

### Option 1: Using Docker Compose (Recommended)

This method automatically builds and runs all services in Docker containers.

```bash
docker-compose up --build
```

### Option 2: Running Services Individually

1. Build each Spring Boot service using Maven:

```bash
mvn clean install
```

2. Run each service on its respective port:

```bash
java -jar target/<service-name>.jar
```

---

## Running the Application

* **API Gateway**: [http://localhost:8080](http://localhost:8080)
* **Eureka Dashboard**: [http://localhost:8761](http://localhost:8761)
* **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

You can access all microservices through the API Gateway.

---

## API Documentation

All microservices expose **REST APIs** documented via Swagger. Access Swagger UI through the API Gateway:

```
http://localhost:8080/swagger-ui.html
```

---

## Database

The application uses **PostgreSQL** for persistence.

**Default Configuration (Docker Compose):**

* Host: `localhost`
* Port: `5432`
* User: `postgres`
* Password: `postgres`
* Database: `marketplace_db`

You can customize database settings in the `application.yml` files of each service.

---

## Docker & Docker Compose

Docker Compose file (`docker-compose.yml`) defines all microservices and PostgreSQL. It handles:

* Building Docker images
* Starting containers in the correct order
* Networking between services

Commands:

```bash
# Build and start all services
docker-compose up --build

# Stop all services
docker-compose down
```

---

## Project Structure

```
online-marketplace/
│
├── api-gateway/          # Spring Cloud Gateway
├── service-discovery/        # Service Discovery
├── user-service/         # Authentication & User Management
├── product-service/      # Product Catalog Management
├── cart-service/         # Shopping Cart & Orders
├── docker-compose.yml    # Docker Compose configuration
└── README.md
```
