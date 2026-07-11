# smp-notification-service

Microservice for creating and querying project notifications for the SDLC portal and external consumers.

## Overview

`smp-notification-service` centralizes project notification management, exposing:
- `POST /notifications` — create a new notification
- `GET /notifications/{id}` — retrieve a notification by ID

## Features

- Bearer token authentication
- Payload validation with JSR-303
- Request traceability via `X-Request-Id` / `requestId`
- In-memory persistence (production-ready for database swap via Repository pattern)
- OpenAPI 3.0 contract published at `swagger/openapi-v1.yaml`
- Corporate SMP archetype: Spring Boot 3.3.11 + WebFlux + functional Router/Handler

## Architecture

```
POST /notifications      →  NotificationRouter → NotificationHandler → NotificationServiceImpl → NotificationRepositoryImpl
GET  /notifications/{id} →  NotificationRouter → NotificationHandler → NotificationServiceImpl → NotificationRepositoryImpl
```

## Technology Stack

| Component | Version |
|-----------|---------|
| Java | 21 |
| Spring Boot | 3.3.11 |
| Spring WebFlux | reactive |
| MapStruct | 1.5.3.Final |
| Lombok | 1.18.30 |

## Related Links

- [OpenAPI Spec](openapi.yaml)
- [CHANGELOG](CHANGELOG.md)
