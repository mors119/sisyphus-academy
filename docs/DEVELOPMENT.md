# DEVELOPMENT.md

# Development Guide

This document explains how to develop Sisyphus Academy locally.

## Project Structure

```text
backend/
apps/
  sisyphus-web/
chrome-extension/
docs/
```

---

## Backend

Start backend:

```bash
cd backend
./gradlew bootRun
```

Build backend:

```bash
./gradlew build
```

Run tests:

```bash
./gradlew test
```

---

## Frontend

Start frontend:

```bash
cd apps/sisyphus-web

npm install
npm run dev
```

Build frontend:

```bash
npm run build
```

---

## Chrome Extension

Install dependencies:

```bash
cd chrome-extension

npm install
```

Build extension:

```bash
npm run build
```

---

## Coding Guidelines

- Prefer small focused functions.
- Avoid duplicated logic.
- Use meaningful names.
- Keep business logic separated from infrastructure concerns.
- Add tests when introducing new behavior.

---

## Commit Convention

Examples:

```text
feat: add vocabulary search
fix: resolve login issue
docs: update deployment guide
refactor: simplify oauth flow
test: add email verification tests
```
