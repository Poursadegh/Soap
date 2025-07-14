# SOAP Microservices Solution

This project implements two Spring Boot microservices that communicate internally using SOAP and expose REST APIs externally.

## Architecture Overview

- **User Service**: Manages user data and exposes SOAP endpoint for internal communication
- **Profile Service**: Manages user profiles and communicates with User Service via SOAP

## Services

### User Service
- **REST Endpoints**:
  - `POST /users` - Register new user
  - `GET /users/{id}` - Get user by ID
  - `GET /users` - List all users
- **SOAP Endpoint**: `getUserById` - Internal service communication

### Profile Service
- **REST Endpoints**:
  - `POST /profiles` - Create profile (validates user via SOAP)
  - `GET /profiles/{id}` - Get profile with combined user data
- **Internal**: Communicates with User Service via SOAP

## Prerequisites

- Java 17+
- Maven 3.6+
- PostgreSQL (or H2 for development)

## Setup Instructions

### 1. Database Setup

#### Option A: PostgreSQL
```sql
-- Create databases
CREATE DATABASE user_service_db;
CREATE DATABASE profile_service_db;
```

#### Option B: H2 (Development)
Both services are configured to use H2 in-memory databases by default for development.

### 2. Build and Run Services

#### User Service
```bash
cd user-service
mvn clean install
mvn spring-boot:run
```
User Service will start on port 8080

#### Profile Service
```bash
cd profile-service
mvn clean install
mvn spring-boot:run
```
Profile Service will start on port 8081

### 3. Verify Services

#### User Service
- REST API: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html
- SOAP WSDL: http://localhost:8080/ws/users.wsdl

#### Profile Service
- REST API: http://localhost:8081
- Swagger UI: http://localhost:8081/swagger-ui.html

## API Documentation

### User Service REST APIs

#### Create User
```bash
POST http://localhost:8080/users
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john.doe@example.com"
}
```

#### Get User by ID
```bash
GET http://localhost:8080/users/{id}
```

#### Get All Users
```bash
GET http://localhost:8080/users
```

### Profile Service REST APIs

#### Create Profile
```bash
POST http://localhost:8081/profiles
Content-Type: application/json

{
  "userId": 1,
  "bio": "Software Developer",
  "location": "New York",
  "age": 30
}
```

#### Get Profile with User Data
```bash
GET http://localhost:8081/profiles/{id}
```

## SOAP Communication

The Profile Service communicates with User Service via SOAP to validate user existence before creating profiles.

### SOAP Endpoint Details
- **Service**: UserService
- **Port**: UserServicePort
- **Operation**: getUserById
- **WSDL**: http://localhost:8080/ws/users.wsdl

## Project Structure

```
soap/
├── user-service/
│   ├── src/main/java/com/example/userservice/
│   │   ├── controller/
│   │   ├── service/
│   │   ├── repository/
│   │   ├── model/
│   │   ├── dto/
│   │   └── soap/
│   ├── src/main/resources/
│   └── pom.xml
├── profile-service/
│   ├── src/main/java/com/example/profileservice/
│   │   ├── controller/
│   │   ├── service/
│   │   ├── repository/
│   │   ├── model/
│   │   ├── dto/
│   │   └── soap/
│   ├── src/main/resources/
│   └── pom.xml
└── README.md
```

## Technology Stack

- **Java**: 17+
- **Spring Boot**: 3.x
- **Spring Web**: REST APIs
- **Spring WS**: SOAP communication
- **Spring Data JPA**: Database operations
- **PostgreSQL/H2**: Database
- **Swagger/OpenAPI**: API documentation
- **Maven**: Build tool

## Development Notes

- Both services use H2 in-memory database by default
- SOAP communication is configured for internal service calls only
- REST APIs are exposed for external client consumption
- Proper error handling and validation implemented
- Clean architecture with separation of concerns
- DTOs used for data transfer between layers

## Troubleshooting

1. **Port Conflicts**: Ensure ports 8080 and 8081 are available
2. **Database Issues**: Check database connection settings in application.properties
3. **SOAP Communication**: Verify User Service is running before starting Profile Service
4. **Build Issues**: Ensure Java 17+ is installed and Maven is properly configured # Soap
