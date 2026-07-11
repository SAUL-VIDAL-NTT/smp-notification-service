package com.example.smp.notification.services;

import org.springframework.web.reactive.function.server.ServerResponse;
import com.example.smp.notification.models.dtos.validator.CreateNotificationRequestBody;
import com.example.smp.notification.models.dtos.validator.NotificationHeaders;
import reactor.core.publisher.Mono;

public interface INotificationService {

    Mono<ServerResponse> createNotification(NotificationHeaders headers, CreateNotificationRequestBody body);

    Mono<ServerResponse> getNotificationById(NotificationHeaders headers, String id);
}
