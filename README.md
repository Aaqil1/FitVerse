# FitVerse

FitVerse is an AI-driven personal health companion that unifies nutrition tracking, workout recommendations, conversational coaching, and observability in a single horizontally-scalable platform.

## Monorepo Structure

This repository is a Maven multi-module monorepo. Each backend microservice and the frontend will be added in subsequent phases.

```
fitverse/
├── pom.xml                     # Parent aggregator POM
├── docker-compose.yml           # Local development stack
├── Makefile                     # Common automation shortcuts
├── .env                         # Environment variables consumed by Docker & Spring
└── monitoring/
    └── prometheus.yml           # Base Prometheus configuration
```

## Requirements

- Java 17+
- Maven 3.9+
- Docker Desktop or Docker Engine 24+
- Node.js 20+ and pnpm 8+ (for the frontend)

## Quick Start

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-org/fitverse.git
   cd fitverse
   ```
2. **Build all modules** (backend services will be added in later phases)
   ```bash
   mvn clean install
   ```
3. **Start the infrastructure stack**
   ```bash
   docker compose --env-file .env up --build -d
   ```
4. **Stop the stack**
   ```bash
   docker compose --env-file .env down --remove-orphans
   ```

## Observability Endpoints

- Prometheus: [http://localhost:9090](http://localhost:9090)
- Grafana: [http://localhost:3001](http://localhost:3001)

## Default Accounts

The platform ships with the following default credentials, suitable for demos and local development:

| Role  | Email                | Password  |
|-------|----------------------|-----------|
| User  | user@fitverse.dev    | Passw0rd! |
| Coach | coach@fitverse.dev   | Passw0rd! |
| Admin | admin@fitverse.dev   | Passw0rd! |

## Development Workflow

Use the included `Makefile` for frequently used tasks:

```bash
make build           # mvn clean install
make test            # mvn verify
make docker-up       # start infra & services (when available)
make docker-down     # stop and clean containers
```

## Roadmap

Implementation will follow a multi-phase plan covering gateway, services, shared libraries, frontend, documentation, and CI/CD as described in the accompanying PRD.
