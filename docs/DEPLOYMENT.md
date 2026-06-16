# Deployment Guide

This document explains how to run Sisyphus Academy with Docker for both local development and image-based deployment.

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

This uses `docker-compose.yml`, which builds `apps/api` and `apps/web` from the local source tree.

This starts:

- PostgreSQL
- Redis
- API
- Web
- Nginx

---

## Verify Services

Web:

```text
http://localhost
```

API:

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

For image-based deployment, use:

```bash
docker compose -f docker-compose.prod.yml up -d
```

`docker-compose.prod.yml` does not build local sources. It pulls the image references declared through `BACKEND_IMAGE`, `WEB_IMAGE`, and `NGINX_IMAGE`.

Production deployments should:

- Use HTTPS
- Store secrets in environment variables
- Use managed database backups
- Restrict public database access
- Restrict public Redis access

Never commit production credentials.
