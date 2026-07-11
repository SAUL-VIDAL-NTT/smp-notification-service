# Changelog

## [1.0.0] - 2026-07-11

### Added
- Initial implementation of `smp-notification-service` (HU KAN-2)
- `POST /notifications` — create project notification with validation, persistence, and traceability
- `GET /notifications/{id}` — retrieve notification by ID with 404 handling
- Bearer token authentication on all endpoints
- Request traceability via `X-Request-Id` / `requestId`
- In-memory persistence layer (ConcurrentHashMap) with Repository pattern for easy swap
- OpenAPI 3.0 contract (`swagger/openapi-v1.yaml`)
- Corporate SMP archetype: Spring Boot 3.3.11 + WebFlux + functional Router/Handler
- Helm values for DEV, UAT, PRD environments
- GitHub Actions CI/CD workflows (ci, cicd-dev, cd-uat, cd-prd, rollback-prd, rollback-prev)
- Azure Pipelines integration with SonarCloud enabled
- Backstage catalog-info.yaml
- Unit tests for Handler, Service, Repository, Router, Mapper, and Config layers
