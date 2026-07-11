package com.example.smp.notification.repository;

import org.springframework.http.ResponseEntity;
import com.example.smp.notification.models.dtos.request.NotificationRequest;
import reactor.core.publisher.Mono;

public interface INotificationRepository {
    Mono<ResponseEntity<NotificationRequest>> save(NotificationRequest notification);
    Mono<ResponseEntity<NotificationRequest>> findById(String id);
}
