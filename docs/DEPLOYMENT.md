# DEPLOYMENT.md

# Deployment Guide

This document explains how to run Sisyphus Academy locally using Docker.

## Requirements

- Docker
- Docker Compose

Verify installation:

```bash
docker --version
docker compose version
```

---

## Environment Variables

Copy the example configuration:

```bash
cp .env.example .env
```

Update values as needed.

---

## Start Services

Run:

```bash
docker compose up -d
```

This starts:

- PostgreSQL
- Redis
- Backend
- Frontend
- Nginx

---

## Verify Services

Frontend:

```text
http://localhost
```

Backend API:

```text
http://localhost/api
```

Swagger UI:

```text
http://localhost/swagger-ui.html
```

---

## Stop Services

```bash
docker compose down
```

---

## Rebuild Services

```bash
docker compose up -d --build
```

---

## Database

PostgreSQL schema migrations are managed by Flyway.

Migrations run automatically during application startup.

---

## Production Deployment

Production deployments should:

- Use HTTPS
- Store secrets in environment variables
- Use managed database backups
- Restrict public database access
- Restrict public Redis access

Never commit production credentials.
