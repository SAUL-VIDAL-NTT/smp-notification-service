package com.example.smp.notification.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerResponse;
import com.example.smp.notification.mappers.INotificationMapper;
import com.example.smp.notification.models.dtos.request.NotificationRequest;
import com.example.smp.notification.models.dtos.response.NotificationResponse;
import com.example.smp.notification.models.dtos.validator.NotificationHeaders;
import com.example.smp.notification.models.dtos.validator.NotificationRequestBody;
import com.example.smp.notification.repository.INotificationRepository;
import com.example.smp.notification.services.INotificationService;
import com.example.smp.notification.utils.AppUtils;
import com.example.smp.notification.utils.ResponseUtil;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@Service
public class NotificationServiceImpl implements INotificationService {

    private final INotificationRepository notificationRepository;
    private final INotificationMapper notificationMapper;

    @Autowired
    public NotificationServiceImpl(
            @Qualifier("notificationRepository") INotificationRepository notificationRepository,
            INotificationMapper notificationMapper
    ) {
        this.notificationRepository = notificationRepository;
        this.notificationMapper = notificationMapper;
    }

    @Override
    public Mono<ServerResponse> createNotification(NotificationHeaders headers, NotificationRequestBody body) {
        NotificationRequest request = notificationMapper.toNotificationRequest(headers, body);
        request.setId("notif-" + UUID.randomUUID().toString().replace("-", "").substring(0, 12));
        request.setCreatedAt(Instant.now().toString());
        if (request.getRequestId() == null || request.getRequestId().isBlank()) {
            request.setRequestId(UUID.randomUUID().toString());
        }

        return notificationRepository.save(request)
                .flatMap(this::buildCreateResponse)
                .onErrorResume(AppUtils::returnError);
    }

    @Override
    public Mono<ServerResponse> getNotification(NotificationHeaders headers, String notificationId) {
        return notificationRepository.findById(notificationId)
                .flatMap(this::buildGetResponse)
                .onErrorResume(AppUtils::returnError);
    }

    private Mono<ServerResponse> buildCreateResponse(ResponseEntity<NotificationRequest> response) {
        NotificationRequest body = response.getBody();
        return response.getStatusCode() == HttpStatus.OK && body != null
                ? ResponseUtil.created(notificationMapper.toNotificationResponse(body))
                : ResponseUtil.error();
    }

    private Mono<ServerResponse> buildGetResponse(ResponseEntity<NotificationRequest> response) {
        NotificationRequest body = response.getBody();
        return response.getStatusCode() == HttpStatus.OK && body != null
                ? ResponseUtil.successful(notificationMapper.toNotificationResponse(body))
                : ResponseUtil.error();
    }
}
