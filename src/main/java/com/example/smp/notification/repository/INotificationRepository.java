package com.example.smp.notification.repository;

import org.springframework.http.ResponseEntity;
import com.example.smp.notification.models.dtos.response.NotificationResponse;
import com.example.smp.notification.models.dtos.validator.CreateNotificationRequestBody;
import com.example.smp.notification.models.dtos.validator.NotificationHeaders;
import reactor.core.publisher.Mono;

public interface INotificationRepository {

    Mono<ResponseEntity<NotificationResponse>> saveNotification(
            NotificationHeaders headers, CreateNotificationRequestBody body);

    Mono<ResponseEntity<NotificationResponse>> findNotificationById(
            NotificationHeaders headers, String id);
}
