# User Management System

A full-stack user management application with a Spring Boot REST API backend and a React frontend. The system covers the complete user lifecycle: registration, authentication, profile management, role-based access control, multi-factor authentication, and audit logging.

## Tech Stack

**Backend**
- Java 11
- Spring Boot 2.7 (Web, Security, Data JPA, Validation, Mail)
- PostgreSQL (primary database)
- Redis (session/token cache)
- JWT (jjwt 0.11.5)
- Lombok, MapStruct
- Maven

**Frontend**
- React 19
- Redux Toolkit + redux-thunk (state management)
- React Router 7
- Material UI 6 (MUI)
- Formik + Yup (form validation)
- Axios (HTTP client)
- i18next / react-i18next (internationalization)
- qrcode.react (MFA QR code display)
- intro.js (onboarding tours)

**Infrastructure**
- Docker Compose (PostgreSQL 13, Redis 6)

## Key Features

- JWT-based authentication with token refresh and revocation
- Multi-factor authentication (TOTP) with QR code setup and backup codes
- Role and permission management with fine-grained RBAC
- Email verification and password reset flows
- User profile editing with custom field definitions (enterprise)
- Bulk user import (CSV via opencsv)
- Audit log tracking for all user and admin actions
- Notification preference management
- Internationalization support via i18next
- Session persistence with Redis-backed token storage

## Architecture Overview

```
user-management/
├── backend/                          # Spring Boot API
│   └── src/main/java/com/uxfx/usermanagement/
│       ├── controller/               # REST controllers
│       │   ├── AuthController        # Login, register, token refresh
│       │   ├── UserController        # User CRUD, import
│       │   ├── RoleController        # Role management
│       │   ├── PermissionController  # Permission management
│       │   ├── MFAController         # MFA setup and verification
│       │   ├── PasswordResetController
│       │   ├── EmailVerificationController
│       │   ├── CustomFieldController # Enterprise custom fields
│       │   └── AuditLogController
│       ├── model/                    # JPA entities (User, Role, MFA, etc.)
│       ├── security/                 # JWT provider, Spring Security config
│       ├── service/                  # Business logic
│       ├── repository/               # Spring Data JPA repositories
│       ├── dto/                      # Request/response DTOs
│       └── exception/                # Global exception handling
└── frontend/                         # React SPA
    └── src/
        ├── components/
        │   ├── admin/                # Role, permission, audit log UI
        │   ├── auth/                 # Login, registration, MFA, password reset
        │   ├── enterprise/           # Bulk user import
        │   └── profile/             # Profile edit, notification preferences
        ├── pages/                    # Route-level page components
        ├── store/                    # Redux store and slices
        └── services/                 # Axios API service layer
```

## Getting Started

### Prerequisites

- Java 11+
- Maven 3.6+
- Node.js 18+
- Docker and Docker Compose

### Infrastructure

Start PostgreSQL and Redis:

```bash
docker-compose up -d
```

### Backend

```bash
cd backend
./mvnw spring-boot:run
```

The API will be available at http://localhost:8080.

### Frontend

```bash
cd frontend
npm install
npm start
```

The React app will be available at http://localhost:3000 and proxies API calls to http://localhost:8080.

## Usage

### Backend

```bash
# Build
./mvnw clean package

# Run tests
./mvnw test

# Start with Maven wrapper
./mvnw spring-boot:run
```

### Frontend

```bash
npm start        # Development server
npm run build    # Production build
npm test         # Run tests
```

### Key API Endpoints

| Method | Path | Description |
|--------|------|-------------|
| POST | /api/auth/register | Register a new user |
| POST | /api/auth/login | Authenticate, returns JWT |
| POST | /api/auth/refresh | Refresh access token |
| POST | /api/auth/logout | Revoke token |
| GET | /api/users | List users (Admin) |
| PUT | /api/users/{id} | Update user |
| POST | /api/users/import | Bulk import users from CSV |
| GET | /api/roles | List roles |
| POST | /api/roles | Create role |
| GET | /api/permissions | List permissions |
| POST | /api/mfa/setup | Initiate MFA setup |
| POST | /api/mfa/verify | Verify TOTP code |
| GET | /api/audit-logs | Retrieve audit log |
| POST | /api/auth/forgot-password | Request password reset |
| POST | /api/auth/reset-password | Complete password reset |
