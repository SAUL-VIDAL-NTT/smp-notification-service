"""Handler mínimo de notifications-service (stub de ejemplo)."""

def enqueue_notification(req: dict) -> dict:
    # TODO: validar, resolver destinatario (user-profile-service),
    # renderizar (template-service) y publicar en notifications.outbound.
    return {"status": "accepted", "id": "n-0001"}


def get_notification(notification_id: str) -> dict:
    return {"id": notification_id, "status": "delivered"}
