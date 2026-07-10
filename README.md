# notifications-service (demo)

Microservicio de **notificaciones** de ejemplo para probar agentes de análisis
top-down y el GitHub Copilot coding agent. No contiene datos reales de ningún cliente.

## Responsabilidad
Recibe solicitudes de notificación y las publica en un topic de mensajería para
que los consumidores (email, push, SMS) las entreguen de forma asíncrona.

## API HTTP
- `POST /notifications` — encola una notificación.
- `GET /notifications/{id}` — consulta el estado de una notificación.

## Dependencias
- **HTTP (salientes):** `user-profile-service` (resolver destinatario), `template-service` (render de plantillas).
- **Mensajería:** publica en el topic `notifications.outbound`; consume de `notifications.retry`.
- **Datos:** tabla `notifications` (estado y auditoría).

Ver [SERVICE_CATALOG.md](./SERVICE_CATALOG.md) para el detalle de dependencias y blast radius.
