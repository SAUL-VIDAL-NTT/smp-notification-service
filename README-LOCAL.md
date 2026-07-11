# README-LOCAL — smp-notification-service

Quick-start guide for running `smp-notification-service` on your local machine.

## Prerequisites

- Java 21 (Corretto recommended)
- Maven 3.9+
- JFrog Artifactory credentials (for corporate libraries)

## 1. Configure credentials

Copy `.env.local` (already created) and fill in your JFrog credentials:

```bash
url_artifactory=https://artifactory.example.com
user_artifactory=your-jfrog-user
password_artifactory=your-jfrog-password
PROFILE=test
MOCKS=mock,mock-notification
```

> **Note:** `.env.local` is gitignored — never commit it.

## 2. Start the service

**Linux / macOS:**
```bash
chmod +x run-local.sh
./run-local.sh
```

**Windows:**
```cmd
run-local.cmd
```

The service will start on `http://localhost:8080` using the `test` profile with mocks enabled.

## 3. Smoke test

Once the service is running, run the smoke tests:

**Linux / macOS:**
```bash
chmod +x verify-local.sh
./verify-local.sh
```

**Windows:**
```cmd
verify-local.cmd
```

## 4. Key endpoints

| Method | Path                   | Description                        |
|--------|------------------------|------------------------------------|
| POST   | /notifications         | Create a new project notification  |
| GET    | /notifications/{id}    | Retrieve a notification by ID      |
| GET    | /health/liveness       | Liveness probe                     |
| GET    | /health/readiness      | Readiness probe                    |
| GET    | /metrics               | Prometheus metrics                 |

## 5. Example cURL — Create notification

```bash
curl -X POST http://localhost:8080/notifications \
  -H "Content-Type: application/json" \
  -H "consumerId: SMP" \
  -H "sessionId: my-session-id" \
  -H "sessionAppId: APP_SMP" \
  -H "customerId: customer-001" \
  -H "deviceId: device-001" \
  -H "deviceType: AND" \
  -H "Authorization: Bearer my-token" \
  -H "apiVersion: v1" \
  -H "subscription-key: my-sub-key" \
  -H "X-Request-Id: req-001" \
  -d '{"title":"Project milestone updated","message":"The project moved to In Progress","projectId":"KAN-100","severity":"medium"}'
```

## 6. Troubleshooting

| Issue | Fix |
|-------|-----|
| `Could not resolve placeholder` | Check `.env.local` credentials and `PROFILE=test` |
| `401 Unauthorized` | Ensure `Authorization: Bearer <token>` header is present |
| `400 Bad Request` | Ensure `title`, `message`, and `projectId` are in the request body |
| `404 Not Found` | The notification ID does not exist (GET endpoint) |
| Port 8080 in use | Set `SERVER_PORT=8081` in `.env.local` and re-run |
