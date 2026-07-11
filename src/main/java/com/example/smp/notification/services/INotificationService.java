package com.example.smp.notification.services;

import org.springframework.web.reactive.function.server.ServerResponse;
import com.example.smp.notification.models.dtos.validator.NotificationHeaders;
import com.example.smp.notification.models.dtos.validator.NotificationRequestBody;
import reactor.core.publisher.Mono;

public interface INotificationService {

    Mono<ServerResponse> createNotification(NotificationHeaders headers, NotificationRequestBody body);

    Mono<ServerResponse> getNotification(NotificationHeaders headers, String notificationId);
}
