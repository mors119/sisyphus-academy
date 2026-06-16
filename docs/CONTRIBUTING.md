# Contributing

Thank you for contributing to Sisyphus Academy.

## Development Setup

### API

```bash
cd apps/api
./gradlew bootRun
```

### Web

```bash
cd apps/web
npm install
npm run dev
```

### Chrome Extension

```bash
cd apps/chrome-extension
bun install
bun run build
```

### Pull Request Process

1. Fork the repository
2. Create a feature branch
3. Commit changes
4. Open a Pull Request

### Commit Convention

Examples:

feat: add vocabulary search
fix: resolve login redirect issue
docs: update setup guide
refactor: simplify token handling

### Code Style

- Keep functions small
- Write meaningful names
- Avoid unnecessary complexity
- Add tests when possible
