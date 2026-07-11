# Running smp-notification-service Locally

## Prerequisites
- Java 21
- Maven 3.9+
- JFrog Artifactory credentials

## Quick Start

1. **Configure `.env.local`**: Copy and fill the credentials:
   ```bash
   # .env.local is gitignored — never commit it
   JFROG_USER=<your-user>
   JFROG_PASSWORD=<your-password>
   ```

2. **Run with mocks** (no external dependencies needed):
   ```bash
   ./run-local.sh
   ```
   On Windows:
   ```cmd
   run-local.cmd
   ```

3. **Smoke test**:
   ```bash
   ./verify-local.sh
   ```
   Note: Replace `<fill-test-token>` with a valid Bearer JWT before running.

## Available Endpoints

| Method | URL | Description |
|--------|-----|-------------|
| POST | http://localhost:8080/notifications | Create notification |
| GET | http://localhost:8080/notifications/{id} | Get notification by ID |
| GET | http://localhost:8080/health/liveness | Liveness probe |
| GET | http://localhost:8080/health/readiness | Readiness probe |
| GET | http://localhost:8080/metrics | Prometheus metrics |

## Troubleshooting

- **Port 8080 in use**: Change `server.port` in `application.yaml` or set env var `SERVER_PORT=8081`
- **Bean creation errors**: Ensure `PROFILE=test` is set so `application-ci.yaml` is loaded
- **Missing Artifactory deps**: Check `settings.xml` has correct repo URLs and credentials in `~/.m2/settings.xml`
