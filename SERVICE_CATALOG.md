# Catálogo de servicio: notifications-service

| Campo | Valor |
|---|---|
| id | notifications-service |
| equipo | @platform/notifications |
| criticidad | alta |

## Dependencias salientes (HTTP)
- user-profile-service  (GET /users/{id})
- template-service      (POST /render)

## Mensajería
- Publica: `notifications.outbound`
- Consume: `notifications.retry`

## Consumidores (dependen de este servicio)
- email-dispatcher   (consume notifications.outbound)
- push-dispatcher    (consume notifications.outbound)
- sms-dispatcher     (consume notifications.outbound)

## Blast radius (si notifications-service cae)
Impacto directo: los 3 dispatchers dejan de recibir trabajo → sin entregas.
Impacto indirecto: flujos de onboarding y alertas que disparan notificaciones.
