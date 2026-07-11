# CHANGELOG

## [1.0.0] - 2026-07-11

### Added
- Initial implementation of `smp-notification-service`
- `POST /notifications` endpoint to create and persist notifications
- `GET /notifications/{id}` endpoint to retrieve notifications by ID
- Bearer JWT authentication on all endpoints
- `X-Request-Id` header traceability
- OpenAPI 3.0.3 contract (`swagger/openapi-v1.yaml`)
- In-memory persistence layer with `ConcurrentHashMap`
- Mock repository for CI testing (`MockNotificationRepository`)
- Unit tests with >80% coverage
- Corporate archetype: Router → Handler → Service → Repository
- Helm values for dev / uat / prd environments
- GitHub Actions CI/CD pipelines
