# BeastXFit Microservices

A comprehensive fitness application built using a modern microservices architecture. BeastXFit combines a React.js frontend with multiple Spring Boot backend services, orchestrated through service discovery and an API gateway.

---

## Table of Contents

- [Project Overview](#project-overview)
- [Architecture](#architecture)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Services](#services)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Running the Application](#running-the-application)
- [Configuration](#configuration)
- [API Documentation](#api-documentation)
- [Database](#database)
- [Message Queue](#message-queue)
- [Service Discovery](#service-discovery)
- [Security](#security)
- [Development](#development)
- [Contributing](#contributing)
- [License](#license)

---

## Project Overview

BeastXFit is a fitness tracking and AI-powered workout recommendation system built with a microservices architecture. The application provides users with user management, activity tracking, AI-generated workout recommendations, and comprehensive fitness management capabilities.

**Key Features:**
- User profile management and authentication via Keycloak
- Activity tracking and management (MongoDB-based)
- AI-powered workout recommendations via Spring AI (Google Gemini)
- Real-time event streaming using Apache Kafka
- Centralized configuration management via Config Server
- API Gateway for unified access with request routing
- Service discovery and registration via Eureka
- PostgreSQL for user data and MongoDB for activity/recommendation data

---

## Architecture

BeastXFit follows a microservices architecture pattern with the following components:

```
┌────────────────────────────────────────────────────────────────────┐
│                    POSTMAN / FRONTEND (React.js)                    │
└───────────────────────┬──────────────────────────────────────────────┘
                        │
                        ▼
              ┌──────────────────┐
              │   API Gateway    │ (Port 8080)
              │  (Spring Cloud)  │
              └────────┬─────────┘
                       │
         ┌─────────────┼─────────────┐
         │             │             │
         ▼             ▼             ▼
    ┌────────────┐ ┌─────────┐ ┌──────────┐
    │ USER       │ │ACTIVITY │ │  AI      │
    │ SERVICE    │ │SERVICE  │ │SERVICE   │
    │(PostgreSQL)│ │(MongoDB)│ │(MongoDB) │
    └────┬───────┘ └────┬────┘ └────┬─────┘
         │              │           │
         └──────────┬───┴─────┬─────┘
                    │         │
                    ▼         ▼
              ┌──────────────────────────┐
              │      KAFKA TOPICS        │
              │ - activity-events        │
              │ - recommendation-events  │
              └──────────────────────────┘
                    │
         ┌──────────┴──────────┐
         ▼                     ▼
    ┌──────────┐         ┌─────────────────┐
    │ Keycloak │         │ Google's Gemini │
    │(Auth)    │         │(AI Provider)    │
    └──────────┘         └─────────────────┘

┌────────────────────────────────────────────────────────────┐
│            INFRASTRUCTURE SERVICES                         │
├────────────────────────────────────────────────────────────┤
│  Eureka Server (Port 8761) - Service Discovery             │
│  Config Server (Port 8888) - Centralized Configuration     │
└────────────────────────────────────────────────────────────┘
```

---

## Tech Stack

### Frontend
- **Framework:** React.js 19.2.0
- **Build Tool:** Vite
- **State Management:** Redux Toolkit 2.11.2
- **UI Library:** Material-UI (MUI) 7.3.7
- **HTTP Client:** Axios 1.13.2
- **Authentication:** React OAuth2 Code PKCE
- **Routing:** React Router 7.12.0
- **Styling:** Emotion

### Backend - Core Technologies
- **Language:** Java 25
- **Framework:** Spring Boot 3.5.9 / 4.0.1
- **Build Tool:** Maven
- **Spring Cloud:** 2025.0.1 / 2025.1.0

### Backend - Services & Libraries
- **Spring Cloud Config:** Centralized configuration management
- **Spring Cloud Netflix Eureka:** Service discovery and registration
- **Spring Cloud Gateway:** API Gateway with routing and request filtering
- **Spring Boot Web:** REST API development
- **Spring Data JPA:** ORM for PostgreSQL (User Service)
- **Spring Data MongoDB:** Document database access (Activity & AI Services)
- **Apache Kafka:** Event streaming and message processing
- **Spring AI:** AI-powered recommendations (Google Gemini integration)
- **Spring Security:** Resource server and JWT validation

### Authentication & Authorization
- **Keycloak:** OpenID Connect (OIDC) Identity Provider
- **OAuth2:** Protocol for secure authorization
- **JWT:** Token-based authentication

### Databases
- **PostgreSQL:** Relational database for user profiles, authentication, and user-related data
- **MongoDB:** NoSQL database for activity logs and AI recommendations

### Message Queue
- **Apache Kafka:** Event-driven architecture and asynchronous communication

### Infrastructure & Security
- **Service Discovery:** Netflix Eureka Server (Port 8761)
- **Configuration Server:** Spring Cloud Config Server (Port 8888)
- **API Gateway:** Spring Cloud Gateway with OAuth2 Resource Server (Port 8080)
- **Identity Provider:** Keycloak for user authentication
- **Message Broker:** Apache Kafka for async communication
- **Container Ready:** Configured for Docker deployment

### AI & External Services
- **Google Gemini API:** AI model for workout recommendations
- **Spring AI Framework:** Abstraction layer for AI integration

---

## Project Structure

```
beastxfitmicroservices/
├── README.md                          # This file
├── qodana.yaml                        # Code quality configuration
│
├── beastxfitfrontend/                 # React.js Frontend
│   ├── src/
│   │   ├── components/                # React components
│   │   │   ├── ActivityDetail.jsx
│   │   │   ├── ActivityForm.jsx
│   │   │   └── ActivityList.jsx
│   │   ├── services/
│   │   │   └── api.js                 # API client configuration
│   │   ├── store/                     # Redux store
│   │   │   ├── authSlice.js
│   │   │   └── store.js
│   │   ├── App.jsx
│   │   ├── main.jsx
│   │   ├── authConfig.js
│   │   ├── App.css
│   │   └── index.css
│   ├── public/                        # Static assets
│   ├── package.json
│   ├── vite.config.js                 # Vite configuration
│   ├── eslint.config.js               # ESLint configuration
│   └── index.html
│
├── gateway/                           # API Gateway (Port 8080)
│   ├── src/
│   │   ├── main/java/com/
│   │   └── resources/
│   │       └── application.yaml       # Gateway configuration
│   ├── pom.xml
│   └── HELP.md
│
├── eureka/                            # Service Discovery Server (Port 8761)
│   ├── src/
│   │   ├── main/java/com/
│   │   └── resources/
│   │       └── application.yaml
│   ├── pom.xml
│   └── HELP.md
│
├── configserver/                      # Config Server (Port 8888)
│   ├── src/
│   │   ├── main/java/com/
│   │   └── resources/
│   │       └── application.yaml
│   ├── pom.xml
│   └── HELP.md
│
├── beastxfit/                         # Main Fitness Service
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/fitness/      # Service logic
│   │   │   └── resources/
│   │   │       ├── application.yml    # Service configuration
│   │   │       ├── static/            # Static resources
│   │   │       └── templates/
│   │   └── test/java/com/
│   ├── pom.xml
│   ├── HELP.md
│   ├── mvnw & mvnw.cmd                # Maven wrapper scripts
│   └── target/                        # Compiled files
│
├── activityservice/                   # Activity Tracking Service
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/      # Service logic
│   │   │   └── resources/
│   │   │       ├── application.yml    # Service configuration
│   │   │       ├── static/            # Static resources
│   │   │       └── templates/
│   │   └── test/java/com/
│   ├── pom.xml
│   ├── HELP.md
│   ├── mvnw & mvnw.cmd                # Maven wrapper scripts
│   └── target/                        # Compiled files
│
└── aiservice/                         # AI Recommendation Service
    ├── src/
    │   ├── main/
    │   │   ├── java/com/fitness/      # Service logic
    │   │   └── resources/
    │   │       ├── application.yaml   # Service configuration
    │   │       ├── static/            # Static resources
    │   │       └── templates/
    │   └── test/java/com/
    ├── pom.xml
    ├── HELP.md
    ├── mvnw & mvnw.cmd                # Maven wrapper scripts
    └── target/                        # Compiled files
```

---

## Services

### 1. **API Gateway** (Port: 8080)
- **Role:** Single entry point for all client requests
- **Technology:** Spring Cloud Gateway
- **Features:**
  - Request routing to appropriate microservices
  - OAuth2 resource server for authentication
  - Load balancing
  - Request/Response filtering
- **Configuration:** Uses Eureka for dynamic service discovery

### 2. **Eureka Server** (Port: 8761)
- **Role:** Service discovery and registration center
- **Technology:** Netflix Eureka
- **Features:**
  - Service registration and deregistration
  - Health checks
  - Service lookup for inter-service communication
- **Configuration:** Standalone Eureka server

### 3. **Config Server** (Port: 8888)
- **Role:** Centralized configuration management
- **Technology:** Spring Cloud Config
- **Features:**
  - Externalized configuration for all services
  - Environment-specific configurations
  - Hot reload support
- **Services Connected:** All microservices pull config from here

### 4. **User Service** (Formerly BeastXFit)
- **Role:** User profile management and authentication
- **Database:** PostgreSQL
- **Key Dependencies:**
  - Spring Data JPA for ORM
  - Hibernate for database mapping
  - Spring Cloud Config client
  - PostgreSQL JDBC driver
  - Spring Security for authentication
- **Features:**
  - User profile creation and management
  - User authentication and authorization
  - User preferences and settings
  - Integration with Keycloak for OAuth2/OIDC
  - User data persistence in PostgreSQL
- **API Endpoints:** `/api/users/*`

### 5. **Activity Service**
- **Role:** Track and manage user activities with real-time event streaming
- **Database:** MongoDB
- **Key Dependencies:**
  - Spring Data MongoDB for document storage
  - Apache Kafka for event publishing
  - Eureka client for service discovery
  - Spring Cloud Config client
- **Features:**
  - Activity logging and tracking in MongoDB
  - Real-time event streaming via Kafka
  - Publishes activity events for AI and other services
  - Query and retrieval of activity history
  - Activity filtering and search capabilities
- **API Endpoints:** `/api/activities/*`

### 6. **AI Service**
- **Role:** AI-powered workout recommendations using Google Gemini
- **Database:** MongoDB
- **Key Dependencies:**
  - Spring AI framework for Gemini integration
  - Apache Kafka for consuming activity events
  - Eureka client for service discovery
  - Google Gemini API client
- **Features:**
  - Generate personalized workout recommendations using Google Gemini
  - Analyze user activity data and preferences
  - Event-driven recommendation processing
  - Store recommendations in MongoDB
  - Publish recommendation events to Kafka
- **Configuration Required:**
  - Google Gemini API key (set as environment variable)
- **API Endpoints:** `/api/recommendations/*`

### 7. **Frontend (React.js)**
- **Role:** User interface and interaction layer
- **Build Tool:** Vite
- **Key Features:**
  - Activity management (view, create, update, delete)
  - User authentication via Keycloak (OAuth2/OIDC)
  - Redux-based state management for global state
  - Material-UI for responsive and modern design
  - Axios for API communication with the Gateway
  - Protected routes based on authentication
- **Components:**
  - `ActivityList.jsx` - Display all user activities
  - `ActivityDetail.jsx` - View detailed activity information
  - `ActivityForm.jsx` - Create/edit activities
  - `authConfig.js` - Keycloak configuration
  - `api.js` - Backend API client with JWT token handling
  - Auth slice in Redux for authentication state
- **Authentication Flow:** User logs in via Keycloak → JWT token obtained → Token sent with API requests → Gateway validates token

---

## Prerequisites

### System Requirements
- **Operating System:** Windows, macOS, or Linux
- **RAM:** Minimum 8GB (recommended 16GB for all services)
- **Disk Space:** Minimum 5GB

### Software Requirements

1. **Java Development Kit (JDK)**
   - Version: Java 25 or later
   - Download from: https://www.oracle.com/java/technologies/downloads/

2. **Node.js & npm** (for frontend)
   - Version: Node.js 18.x or later
   - Download from: https://nodejs.org/

3. **Maven** (Build tool)
   - Version: 3.9.x or later
   - Download from: https://maven.apache.org/download.cgi
   - Note: Maven wrappers (`mvnw` and `mvnw.cmd`) are included in each service

4. **PostgreSQL** (Database)
   - Version: 13 or later
   - Download from: https://www.postgresql.org/download/

5. **MongoDB** (NoSQL Database)
   - Version: 5.0 or later
   - Download from: https://www.mongodb.com/try/download/community

6. **Apache Kafka** (Message Queue)
   - Version: 3.5 or later
   - Download from: https://kafka.apache.org/downloads

7. **Docker** (Optional but recommended)
   - For containerized deployment
   - Download from: https://www.docker.com/

### Environment Setup

**Windows:**
```powershell
# Set JAVA_HOME
setx JAVA_HOME "C:\Program Files\Java\jdk-25"

# Add Java to PATH
setx PATH "%PATH%;%JAVA_HOME%\bin"

# Verify installation
java -version
javac -version
```

**macOS/Linux:**
```bash
# Set JAVA_HOME
export JAVA_HOME=/usr/libexec/java_home -v 25

# Add to .bashrc or .zshrc
echo "export JAVA_HOME=$JAVA_HOME" >> ~/.bashrc
source ~/.bashrc

# Verify installation
java -version
javac -version
```

---

## Installation & Setup

### Step 1: Clone the Repository
```bash
git clone <repository-url>
cd beastxfitmicroservices
```

### Step 2: Start Infrastructure Services

#### Start PostgreSQL
```bash
# macOS (using Homebrew)
brew services start postgresql

# Windows (Command Prompt)
pg_ctl -D "C:\Program Files\PostgreSQL\15\data" start

# Linux (systemd)
sudo systemctl start postgresql
```

#### Start MongoDB
```bash
# macOS
brew services start mongodb-community

# Windows
mongod

# Linux
sudo systemctl start mongod
```

#### Start Apache Kafka
```bash
# Extract Kafka
tar -xzf kafka_2.13-3.5.0.tgz
cd kafka_2.13-3.5.0

# Terminal 1: Start Zookeeper
bin/zookeeper-server-start.sh config/zookeeper.properties

# Terminal 2: Start Kafka Broker
bin/kafka-server-start.sh config/server.properties
```

### Step 3: Build Backend Services

```bash
# Build Eureka Server
cd eureka
mvnw clean package
cd ..

# Build Config Server
cd configserver
mvnw clean package
cd ..

# Build Gateway
cd gateway
mvnw clean package
cd ..

# Build BeastXFit Service
cd beastxfit
mvnw clean package
cd ..

# Build Activity Service
cd activityservice
mvnw clean package
cd ..

# Build AI Service
cd aiservice
mvnw clean package
cd ..
```

### Step 4: Setup Frontend

```bash
cd beastxfitfrontend

# Install dependencies
npm install

# Or using yarn
yarn install
```

---

## Running the Application

### Prerequisites Before Starting
- PostgreSQL and MongoDB services must be running
- Kafka broker must be running with topics created
- Keycloak server must be running and configured
- Google Gemini API key configured for AI Service

### Start Services in Order

**Terminal 1: Eureka Server (Service Discovery)**
```bash
cd eureka
mvnw spring-boot:run
# Service will be available at http://localhost:8761
```

**Terminal 2: Config Server (Configuration Management)**
```bash
cd configserver
mvnw spring-boot:run
# Service will be available at http://localhost:8888
```

**Terminal 3: API Gateway (Main Entry Point)**
```bash
cd gateway
mvnw spring-boot:run
# Service will be available at http://localhost:8080
# Routes all requests to respective microservices
```

**Terminal 4: User Service (PostgreSQL)**
```bash
cd beastxfit
mvnw spring-boot:run
# Service will register with Eureka (name: beastxfit)
# Handles user management and authentication
# Accessible through Gateway at /api/users
```

**Terminal 5: Activity Service (MongoDB + Kafka)**
```bash
cd activityservice
mvnw spring-boot:run
# Service will register with Eureka (name: activity-service)
# Publishes events to Kafka topic: activity-events
# Accessible through Gateway at /api/activities
```

**Terminal 6: AI Service (MongoDB + Kafka + Gemini)**
```bash
cd aiservice
mvnw spring-boot:run
# Service will register with Eureka (name: ai-service)
# Consumes activity events from Kafka
# Generates recommendations using Google Gemini
# Accessible through Gateway at /api/recommendations
```

**Terminal 7: Frontend (React.js)**
```bash
cd beastxfitfrontend
npm run dev
# Application will be available at http://localhost:5173
# Redirects to Keycloak for login
```

### Access Points

| Service | URL | Purpose |
|---------|-----|---------|
| Frontend | http://localhost:5173 | React.js UI - Start here! |
| Keycloak (if running) | http://localhost:8080/auth | Identity provider & login |
| API Gateway | http://localhost:8080 | Backend API entry point (proxied to frontend) |
| Eureka | http://localhost:8761 | Service registry dashboard |
| Config Server | http://localhost:8888 | Configuration management console |

---

## Configuration

### Backend Services Configuration

Each service has a `src/main/resources/application.yml` (or `.yaml`) file:

**beastxfit/src/main/resources/application.yml (User Service)**
```yaml
spring:
  application:
    name: beastxfit
  config:
    import: optional:configserver:http://localhost:8888
  datasource:
    url: jdbc:postgresql://localhost:5432/beastxfit
    username: postgres
    password: your_password
  jpa:
    hibernate:
      ddl-auto: update
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: http://localhost:8080/auth/realms/beastxfit
```

**activityservice/src/main/resources/application.yml (Activity Service)**
```yaml
spring:
  application:
    name: activity-service
  config:
    import: optional:configserver:http://localhost:8888
  data:
    mongodb:
      uri: mongodb://localhost:27017/activity_db
  kafka:
    bootstrap-servers: localhost:9092
    producer:
      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
```

**aiservice/src/main/resources/application.yaml (AI Service)**
```yaml
spring:
  application:
    name: ai-service
  config:
    import: optional:configserver:http://localhost:8888
  data:
    mongodb:
      uri: mongodb://localhost:27017/ai_db
  kafka:
    bootstrap-servers: localhost:9092
    consumer:
      group-id: ai-service-group
      value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
  ai:
    google:
      gemini:
        api-key: ${GOOGLE_GEMINI_API_KEY}
```

**gateway/src/main/resources/application.yaml (API Gateway)**
```yaml
spring:
  application:
    name: gateway-service
  config:
    import: optional:configserver:http://localhost:8888
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: http://localhost:8080/auth/realms/beastxfit
          jwk-set-uri: http://localhost:8080/auth/realms/beastxfit/protocol/openid-connect/certs
  cloud:
    gateway:
      routes:
        - id: user-service
          uri: lb://beastxfit
          predicates:
            - Path=/api/users/**
        - id: activity-service
          uri: lb://activity-service
          predicates:
            - Path=/api/activities/**
        - id: ai-service
          uri: lb://ai-service
          predicates:
            - Path=/api/recommendations/**
```

### Environment Variables

```bash
# Google Gemini API Configuration (AI Service)
export GOOGLE_GEMINI_API_KEY=your_google_gemini_api_key

# Database Credentials
export POSTGRES_USER=postgres
export POSTGRES_PASSWORD=your_secure_password
export MONGODB_URI=mongodb://localhost:27017

# Keycloak (if running locally)
export KEYCLOAK_ADMIN=admin
export KEYCLOAK_ADMIN_PASSWORD=admin
```

### Frontend Configuration

**beastxfitfrontend/src/authConfig.js (Keycloak Configuration)**
```javascript
export const authConfig = {
  clientId: 'beastxfit-frontend',
  realm: 'beastxfit',
  authority: 'http://localhost:8080/auth/realms/beastxfit',
  redirectUri: window.location.origin,
  scopes: ['openid', 'profile', 'email'],
  responseType: 'code',
  responseMode: 'query',
  grantType: 'authorization_code',
  fetchUserInfo: true,
};
```

**beastxfitfrontend/src/services/api.js (API Client)**
```javascript
import axios from 'axios';

const API_BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';

export const apiClient = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Interceptor to add JWT token to requests
apiClient.interceptors.request.use((config) => {
  const token = localStorage.getItem('access_token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default apiClient;
```

---

## Database

### PostgreSQL Setup

```bash
# Connect to PostgreSQL
psql -U postgres

# Create database for BeastXFit
CREATE DATABASE beastxfit;

# Create user
CREATE USER beastxfit_user WITH PASSWORD 'secure_password';

# Grant privileges
ALTER ROLE beastxfit_user SET client_encoding TO 'utf8';
ALTER ROLE beastxfit_user SET default_transaction_isolation TO 'read committed';
ALTER ROLE beastxfit_user SET default_transaction_deferrable TO on;
GRANT ALL PRIVILEGES ON DATABASE beastxfit TO beastxfit_user;
```

### MongoDB Setup

```bash
# Connect to MongoDB
mongo

# Switch to beastxfit database
use beastxfit

# Create collections
db.createCollection("activities")
db.createCollection("users")
db.createCollection("workouts")

# Create indexes
db.activities.createIndex({ "userId": 1 })
db.activities.createIndex({ "timestamp": -1 })
```

### Database Schema Overview

**PostgreSQL (BeastXFit Service):**
- `users` - User profiles and account information
- `goals` - User fitness goals
- `progress` - Progress tracking data
- `subscriptions` - User subscription plans

**MongoDB (Activity Service):**
- `activities` - Detailed activity logs
- `activity_events` - Activity event stream

**MongoDB (AI Service):**
- `recommendations` - Generated workout recommendations
- `user_preferences` - AI model preferences per user

---

## Message Queue

### Apache Kafka Topics

**Topic: `activity-events`**
- **Producer:** Activity Service
- **Consumer:** AI Service, BeastXFit Service
- **Message Schema:**
  ```json
  {
    "userId": "string",
    "activityId": "string",
    "activityType": "string",
    "duration": "number",
    "calories": "number",
    "timestamp": "ISO-8601"
  }
  ```

**Topic: `recommendation-events`**
- **Producer:** AI Service
- **Consumer:** BeastXFit Service, Frontend
- **Message Schema:**
  ```json
  {
    "userId": "string",
    "recommendationId": "string",
    "workoutType": "string",
    "duration": "number",
    "difficulty": "string",
    "createdAt": "ISO-8601"
  }
  ```

### Create Kafka Topics

```bash
# Navigate to Kafka directory
cd kafka_2.13-3.5.0

# Create activity-events topic
bin/kafka-topics.sh --create --topic activity-events --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1

# Create recommendation-events topic
bin/kafka-topics.sh --create --topic recommendation-events --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1

# List all topics
bin/kafka-topics.sh --list --bootstrap-server localhost:9092
```

---

## Service Discovery

### Eureka Configuration

**eureka/src/main/resources/application.yaml**
```yaml
spring:
  application:
    name: eureka-server

server:
  port: 8761

eureka:
  instance:
    hostname: localhost
  client:
    registerWithEureka: false
    fetchRegistry: false
    serviceUrl:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
```

### Service Registration

Each microservice automatically registers with Eureka through Spring Cloud Eureka Client dependency.

### Service Instance Health

Check Eureka dashboard: http://localhost:8761

---

## Security

### Keycloak Setup & OAuth2/OIDC Integration

The application uses Keycloak as the identity provider for OAuth2/OIDC authentication:

**API Gateway - OAuth2 Resource Server Configuration**

**gateway/src/main/resources/application.yaml**
```yaml
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: http://localhost:8080/auth/realms/beastxfit
          jwk-set-uri: http://localhost:8080/auth/realms/beastxfit/protocol/openid-connect/certs
```

### Keycloak Configuration

1. **Create Realm:** `beastxfit`
2. **Create Client:** `beastxfit-frontend`
   - Client Protocol: `openid-connect`
   - Access Type: `public`
   - Valid Redirect URI: `http://localhost:5173/*`
   - Web Origins: `http://localhost:5173`
3. **Create Users:** Add test users and assign roles

### Frontend Keycloak Setup

**beastxfitfrontend/src/authConfig.js**
```javascript
export const authConfig = {
  clientId: 'beastxfit-frontend',
  realm: 'beastxfit',
  authority: 'http://localhost:8080/auth/realms/beastxfit',
  redirectUri: window.location.origin,
  scopes: ['openid', 'profile', 'email'],
  responseType: 'code',
  responseMode: 'query',
};
```

**beastxfitfrontend/src/components/Login.jsx**
```jsx
import { useAuth } from 'react-oauth2-code-pkce';

export function Login() {
  const { token, login, logout } = useAuth();
  
  return (
    <div>
      {!token ? (
        <button onClick={login}>Login with Keycloak</button>
      ) : (
        <button onClick={logout}>Logout</button>
      )}
    </div>
  );
}
```

### API Gateway Security Filter

```java
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
  
  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
    http
      .csrf().disable()
      .authorizeExchange()
      .pathMatchers("/auth/**", "/public/**").permitAll()
      .pathMatchers("/api/**").authenticated()
      .anyExchange().authenticated()
      .and()
      .oauth2ResourceServer()
      .jwt();
    
    return http.build();
  }
}
```

### Authentication Flow

1. User accesses frontend at `http://localhost:5173`
2. Unauthenticated user redirected to Keycloak login page
3. User enters credentials
4. Keycloak returns authorization code
5. Frontend exchanges code for JWT token
6. JWT token stored in browser (secure httpOnly cookie)
7. All API requests to Gateway include JWT token in Authorization header
8. Gateway validates JWT token using Keycloak's public key
9. Valid requests forwarded to respective microservices
10. Microservices can access user info from JWT claims

### Best Practices

- Use HTTPS in production (enforce SSL)
- Configure Keycloak with strong password policies
- Rotate API keys regularly
- Implement rate limiting at Gateway level
- Enable CORS only for trusted origins
- Use JWT tokens with appropriate expiration times (15-30 minutes)
- Implement refresh token rotation for longer sessions
- Validate all user inputs at Gateway and service levels
- Use MongoDB and PostgreSQL connection encryption
- Enable audit logging in Keycloak for compliance

---

## Development

### Code Structure Best Practices

**Backend Service Structure:**
```
src/main/java/com/fitness/
├── controller/           # REST Controllers
├── service/              # Business logic
├── repository/           # Data access layer
├── model/                # Entity classes
├── dto/                  # Data transfer objects
├── exception/            # Custom exceptions
├── config/               # Configuration classes
└── util/                 # Utility classes
```

**Frontend Structure:**
```
src/
├── components/           # React components
├── pages/                # Page components
├── services/             # API services
├── store/                # Redux state management
├── hooks/                # Custom React hooks
├── utils/                # Utility functions
└── styles/               # Global styles
```

### Building Services

```bash
# Build all services
for service in eureka configserver gateway beastxfit activityservice aiservice; do
  cd $service
  mvnw clean package -DskipTests
  cd ..
done

# Build frontend
cd beastxfitfrontend
npm run build
```

### Running Tests

```bash
# Run tests for a service
cd beastxfit
mvnw test

# Run tests for frontend
cd beastxfitfrontend
npm test
```

### Linting & Code Quality

**Backend:**
```bash
# Run Qodana for code quality analysis
qodana scan -i
```

**Frontend:**
```bash
# Run ESLint
npm run lint

# Fix ESLint issues
npm run lint -- --fix
```

### Development Tips

1. **Service Communication:** Use Eureka service names (e.g., `http://activity-service/api/activities`)
2. **Configuration Updates:** Use Spring Cloud Config for centralized management
3. **Event Driven:** Use Kafka for asynchronous communication
4. **Logging:** Enable debug logging in `application.yml` with `logging.level.com.fitness=DEBUG`
5. **Hot Reload:** Use Spring Boot DevTools for automatic restart on code changes

---

## Contributing

### Development Workflow

1. Create a new branch for your feature:
   ```bash
   git checkout -b feature/your-feature-name
   ```

2. Make your changes and commit:
   ```bash
   git commit -m "Add your meaningful commit message"
   ```

3. Push to your branch:
   ```bash
   git push origin feature/your-feature-name
   ```

4. Submit a pull request with description of changes

### Code Standards

- Follow Spring Framework conventions for backend
- Follow React best practices for frontend
- Write meaningful commit messages
- Include tests for new features
- Update documentation as needed

---

## License

This project is provided as-is for educational and personal use. Please review the LICENSE file for complete details.

---

## Support & Resources

### Documentation
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Cloud Documentation](https://spring.io/projects/spring-cloud)
- [React Documentation](https://react.dev)
- [Redux Documentation](https://redux.js.org)

### Tutorials
- [Spring Microservices Tutorial](https://spring.io/guides)
- [Kafka Documentation](https://kafka.apache.org/documentation/)
- [MongoDB University](https://university.mongodb.com)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)

### Troubleshooting

**Port Already in Use:**
```bash
# Windows
netstat -ano | findstr :8080

# macOS/Linux
lsof -i :8080
```

**Eureka Registration Issues:**
- Check Config Server is running
- Verify service names match configuration
- Check network connectivity

**Kafka Connection Issues:**
- Verify Kafka broker is running
- Check Kafka logs: `kafka_2.13-3.5.0/logs/`
- Verify topic creation

**Database Connection Issues:**
- Verify PostgreSQL/MongoDB services are running
- Check connection string in configuration
- Verify database and user credentials

---

## Project Maintainers

This is an active side project combining cutting-edge microservices technologies.

**Last Updated:** January 2026

---

**Happy Coding! 🚀**
