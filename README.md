 # Online Marketplace Microservices

A Spring Boot-based microservices architecture for an online marketplace.

## Architecture

- **API Gateway**: Spring Cloud Gateway (Port 8080)
- **Service Discovery**: Eureka Server (Port 8761)
- **User Service**: Authentication & User Management (Port 8081)
- **Product Service**: Product Catalog Management (Port 8082)
- **Cart Service**: Shopping Cart & Orders (Port 8083)
- **Database**: PostgreSQL (Port 5432)

## Prerequisites

- Java 17+
- Maven 3.6+
- Docker & Docker Compose
- Git

## Quick Start

### Option 1: Docker Compose (Recommended)

```bash
# Clone the repository
git clone <repository-url>
cd online-marketplace

# Build and run all services
docker-compose up --build

# Access the application
# API Gateway: http://localhost:8080
# Eureka Dashboard: http://localhost:8761
# Swagger UI: http://localhost:8080/swagger-ui.html