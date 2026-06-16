# Sisyphus Academy

Sisyphus Academy is a self-hosted knowledge capture platform built with Spring Boot, React, a Chrome extension, PostgreSQL, Redis, and Docker.

The project is designed with a clear separation between reusable platform infrastructure and academy-specific business logic.

---

# High Level Architecture

```text
┌─────────────────────────┐
│ Web App / Extension     │
└───────────┬─────────────┘
            │
            ▼
┌─────────────────────────┐
│ Nginx Gateway           │
└───────────┬─────────────┘
            │
            ▼
┌─────────────────────────┐
│ Spring Boot Backend     │
└───────┬───────┬─────────┘
        │       │
        │       │
        ▼       ▼
 PostgreSQL   Redis
```

---

# API Architecture

The API application lives in `apps/api` and is divided into two conceptual layers.

## Platform Layer

Reusable infrastructure that can be adopted by other projects.

### Authentication

- JWT authentication
- Access token management
- Refresh token management

### OAuth

- Google Login
- Naver Login
- Kakao Login

### Email Verification

- Verification email generation
- Verification code validation
- Redis-based expiration management

### File Management

- File upload
- Image serving
- Storage abstraction

### API Documentation

- OpenAPI
- Swagger UI

### Infrastructure

- PostgreSQL
- Redis
- Flyway
- Docker

---

## Domain Layer

Business logic specific to Sisyphus Academy.

### User

Responsible for:

- Account management
- Profile management
- User settings

### Vocabulary

Responsible for:

- Vocabulary registration
- Vocabulary management
- Search and filtering

### Wordbook

Responsible for:

- Wordbook creation
- Wordbook management
- Vocabulary grouping

### Learning

Responsible for:

- Learning sessions
- Progress tracking
- Review workflows

### Statistics

Responsible for:

- Learning history
- Progress analysis
- User achievements

---

# Web Architecture

The web application lives in `apps/web` and follows a feature-oriented structure.

```text
src/
├── app/
├── features/
└── components/
```

Guidelines:

- Business logic belongs to features.
- Routing and application bootstrapping belong to `app/`.
- Shared UI components belong to `components/`.
- Feature modules should avoid direct coupling.

---

# Chrome Extension Architecture

The browser extension lives in `apps/chrome-extension`.

Its code is organized around WXT entrypoints such as:

- `entrypoints/background.ts`
- `entrypoints/popup/`
- `public/`

---

# Infrastructure

## Containers

- web
- api
- postgres
- redis
- nginx

Managed using Docker Compose.

---

# Design Principles

## Separation of Concerns

Platform functionality must remain independent from academy-specific business logic.

## Environment Driven Configuration

No production URL, secret, or environment-specific configuration should be committed.

All runtime configuration must come from environment variables.

## Open Source Friendly

The project should be executable locally with:

```bash
docker compose up -d
```

after copying:

```bash
.env.example
→ .env
```

without requiring code modifications.

---

# Future Direction

The long-term goal is to keep Sisyphus Academy as a concrete implementation while extracting reusable platform components where appropriate.

Potential extraction candidates:

- Authentication module
- OAuth integration module
- Email verification module
- File management module
- Docker deployment templates
- CI/CD templates
