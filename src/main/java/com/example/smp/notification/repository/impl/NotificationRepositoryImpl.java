package com.example.smp.notification.repository.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import com.example.smp.notification.exception.ExternalServiceErrorHandler;
import com.example.smp.notification.models.dtos.response.NotificationResponse;
import com.example.smp.notification.models.dtos.validator.CreateNotificationRequestBody;
import com.example.smp.notification.models.dtos.validator.NotificationHeaders;
import com.example.smp.notification.repository.INotificationRepository;
import com.example.smp.types.AppHttpStatusCode;
import com.example.smp.exception.AppException;
import com.example.smp.notification.contants.AppConstants;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class NotificationRepositoryImpl implements INotificationRepository {

    private final ConcurrentHashMap<String, NotificationResponse> store = new ConcurrentHashMap<>();

    @Override
    public Mono<ResponseEntity<NotificationResponse>> saveNotification(
            NotificationHeaders headers, CreateNotificationRequestBody body) {
        return Mono.fromCallable(() -> {
            String id = UUID.randomUUID().toString();
            String createdAt = Instant.now().toString();
            NotificationResponse notification = NotificationResponse.builder()
                    .id(id)
                    .title(body.getTitle())
                    .message(body.getMessage())
                    .projectId(body.getProjectId())
                    .createdAt(createdAt)
                    .requestId(headers.requestId())
                    .build();
            store.put(id, notification);
            return ResponseEntity.status(HttpStatus.CREATED).body(notification);
        }).onErrorResume(ExternalServiceErrorHandler::error);
    }

    @Override
    public Mono<ResponseEntity<NotificationResponse>> findNotificationById(
            NotificationHeaders headers, String id) {
        return Mono.fromCallable(() -> {
            NotificationResponse notification = store.get(id);
            if (notification == null) {
                throw new AppException(
                        AppConstants.Errors.ERROR_CODE_NOT_FOUND,
                        AppConstants.Errors.ERROR_MESSAGE_NOT_FOUND,
                        AppHttpStatusCode.NOT_FOUND);
            }
            return ResponseEntity.ok(notification);
        }).onErrorResume(ExternalServiceErrorHandler::error);
    }
}
