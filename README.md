# Trade Order Management System

## Overview
This project is a simplified Trade Order Management System built using Java,
Spring Boot, Microservices, PostgreSQL, and Temporal.

The system demonstrates:
- Asynchronous order processing using Temporal workflows
- Clear order lifecycle management
- Separation of concerns using microservices
- Basic JWT-based API security
- Docker-based infrastructure setup

---

## Architecture
The system consists of two main microservices.

### Order Service
- Accepts trade orders via REST APIs
- Orchestrates order lifecycle using Temporal workflows
- Persists order state and order history
- Secured using JWT authentication

### Market Data Service
- Publishes mock market prices periodically
- Exposes APIs to fetch market prices and market status

---

## Technology Stack
- Java 17
- Spring Boot
- Spring Data JPA
- Spring Security (JWT)
- Temporal Workflow Engine
- PostgreSQL
- Docker & Docker Compose

---

## Infrastructure Setup
PostgreSQL and Temporal Server are started using Docker Compose,
as recommended in the assignment documentation.

### Start Infrastructure
```bash
docker-compose up -d
```

### Services
- PostgreSQL: localhost:5432
- Temporal Server: localhost:7233
- Temporal UI: http://localhost:8233

---

## Database
Hibernate is configured with `ddl-auto=update`, so all required tables
are automatically created on application startup.

- Order Service Database: trade_db
- Temporal Databases: temporal, temporal_visibility

---

## Temporal Workflow
Order processing is implemented using Temporal workflows and activities.

### Workflow Steps
1. Order validation
2. Fraud check
3. Order execution
4. Settlement

Each step is implemented as a Temporal activity with retry policies.

---

## APIs

### Authentication
Generate JWT token:
```http
POST /api/v1/auth/token?username=test-user
```

Use the token in secured APIs:
```http
Authorization: Bearer <token>
```

---

### Order Service APIs
Create Order:
```http
POST /api/v1/orders
```

Get Order:
```http
GET /api/v1/orders/{orderId}
```

Get Order History:
```http
GET /api/v1/orders/{orderId}/history
```

---

### Market Data Service APIs
Get Market Price:
```http
GET /api/v1/market/price/{symbol}
```

Get Market Status:
```http
GET /api/v1/market/status
```

---

## Running Services Locally
1. Start infrastructure:
```bash
docker-compose up -d
```

2. Start Order Service:
- Run OrderServiceApplication

3. Start Market Data Service:
- Run MarketDataServiceApplication

---

## Security
Order Service APIs are secured using JWT-based authentication.

A simple authentication endpoint is provided to generate tokens for testing.
This lightweight security setup is sufficient for demonstrating
authentication and authorization in the context of this assignment.

---

## Assumptions & Limitations
- Market prices are mocked using a scheduler
- Authentication is simplified and does not include user persistence
- The system focuses on workflow orchestration and backend design,
  not real-world trading or financial accuracy

---

## Submission Notes
The project follows the recommended Docker-based Temporal setup.
Local execution may depend on Docker environment configuration,
but workflow orchestration, retries, and order lifecycle logic
are fully implemented and documented.

---

## Author
Aabhas Jain  