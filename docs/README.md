# smp-notification-service

## Overview
The `smp-notification-service` is a lightweight reactive REST microservice built on Spring Boot 3.3.11 WebFlux.
It enables the **Portal SDLC** to create and query persistent notifications associated with project progress.

## Endpoints

| Method | Path | Description |
|--------|------|-------------|
| POST | `/notifications` | Create a new notification |
| GET | `/notifications/{id}` | Retrieve a notification by ID |

## Architecture
Follows the ExampleCorp/SMP corporate archetype:
- **API Layer**: Functional Router + Handler (no `@RestController`)
- **Domain Layer**: `Notification` aggregate
- **Repository Layer**: In-memory persistent store (`ConcurrentHashMap`)
- **Security**: Bearer JWT authentication on all endpoints
- **Traceability**: `X-Request-Id` header propagation

## Technology Stack
- Java 21
- Spring Boot 3.3.11
- Spring WebFlux (reactive)
- MapStruct 1.5.3
- Lombok 1.18.30

## Running Locally
See [README-LOCAL.md](../README-LOCAL.md) for local setup instructions.

## OpenAPI Contract
Available at [openapi.yaml](./openapi.yaml) or [swagger/openapi-v1.yaml](../swagger/openapi-v1.yaml).
