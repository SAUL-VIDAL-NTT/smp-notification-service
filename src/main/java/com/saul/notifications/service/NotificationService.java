package com.saul.notifications.service;

import com.saul.notifications.domain.Notification;
import com.saul.notifications.dto.CreateNotificationRequest;
import com.saul.notifications.dto.NotificationResponse;
import com.saul.notifications.exception.NotificationNotFoundException;
import com.saul.notifications.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

@Service
public class NotificationService {

    private final NotificationRepository repository;

    public NotificationService(NotificationRepository repository) {
        this.repository = repository;
    }

    public Mono<NotificationResponse> create(CreateNotificationRequest request) {
        String id = "notif_" + UUID.randomUUID().toString().replace("-", "").substring(0, 12);
        Notification notification = new Notification(
                id,
                request.getTitle(),
                request.getMessage(),
                request.getRecipientId(),
                request.getType(),
                Instant.now()
        );
        return repository.save(notification).map(this::toResponse);
    }

    public Mono<NotificationResponse> findById(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new NotificationNotFoundException(id)))
                .map(this::toResponse);
    }

    private NotificationResponse toResponse(Notification n) {
        return new NotificationResponse(
                n.getId(),
                n.getTitle(),
                n.getMessage(),
                n.getRecipientId(),
                n.getType(),
                n.getCreatedAt()
        );
    }
}
