package com.example.smp.notification.repository.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import com.example.smp.notification.contants.AppConstants;
import com.example.smp.notification.models.dtos.request.NotificationRequest;
import com.example.smp.notification.repository.INotificationRepository;
import com.example.smp.types.AppHttpStatusCode;
import com.example.smp.exception.AppException;
import reactor.core.publisher.Mono;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class NotificationRepositoryImpl implements INotificationRepository {

    private final ConcurrentHashMap<String, NotificationRequest> store = new ConcurrentHashMap<>();

    @Override
    public Mono<ResponseEntity<NotificationRequest>> save(NotificationRequest notification) {
        store.put(notification.getId(), notification);
        return Mono.just(ResponseEntity.ok(notification));
    }

    @Override
    public Mono<ResponseEntity<NotificationRequest>> findById(String id) {
        NotificationRequest entity = store.get(id);
        if (entity == null) {
            return Mono.error(new AppException(
                    AppConstants.Errors.ERROR_CODE_NOT_FOUND,
                    AppConstants.Errors.ERROR_MESSAGE_NOT_FOUND,
                    AppHttpStatusCode.NOT_FOUND));
        }
        return Mono.just(ResponseEntity.ok(entity));
    }
}
