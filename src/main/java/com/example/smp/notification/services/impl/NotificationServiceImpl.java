package com.example.smp.notification.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerResponse;
import com.example.smp.notification.models.dtos.response.NotificationResponse;
import com.example.smp.notification.models.dtos.validator.CreateNotificationRequestBody;
import com.example.smp.notification.models.dtos.validator.NotificationHeaders;
import com.example.smp.notification.repository.INotificationRepository;
import com.example.smp.notification.services.INotificationService;
import com.example.smp.notification.utils.AppUtils;
import com.example.smp.notification.utils.ResponseUtil;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class NotificationServiceImpl implements INotificationService {

    private final INotificationRepository notificationRepository;

    @Autowired
    public NotificationServiceImpl(
            @Qualifier("notificationRepository") INotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Mono<ServerResponse> createNotification(NotificationHeaders headers,
                                                   CreateNotificationRequestBody body) {
        log.info("Creating notification for projectId={}, requestId={}",
                body.getProjectId(), headers.requestId());
        return notificationRepository.saveNotification(headers, body)
                .flatMap(this::buildCreatedResponse)
                .onErrorResume(AppUtils::returnError);
    }

    @Override
    public Mono<ServerResponse> getNotificationById(NotificationHeaders headers, String id) {
        log.info("Fetching notification id={}, requestId={}", id, headers.requestId());
        return notificationRepository.findNotificationById(headers, id)
                .flatMap(this::buildResponse)
                .onErrorResume(AppUtils::returnError);
    }

    private Mono<ServerResponse> buildCreatedResponse(ResponseEntity<NotificationResponse> response) {
        NotificationResponse body = response.getBody();
        return response.getStatusCode() == HttpStatus.CREATED && body != null
                ? ResponseUtil.created(body)
                : ResponseUtil.error();
    }

    private Mono<ServerResponse> buildResponse(ResponseEntity<NotificationResponse> response) {
        NotificationResponse body = response.getBody();
        return response.getStatusCode() == HttpStatus.OK && body != null
                ? ResponseUtil.successful(body)
                : ResponseUtil.error();
    }
}
