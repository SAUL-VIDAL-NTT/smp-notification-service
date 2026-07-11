package com.example.smp.notification.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import com.example.smp.notification.mappers.INotificationMapper;
import com.example.smp.notification.models.dtos.request.NotificationRequest;
import com.example.smp.notification.models.dtos.response.NotificationResponse;
import com.example.smp.notification.models.dtos.validator.NotificationHeaders;
import com.example.smp.notification.models.dtos.validator.NotificationRequestBody;
import com.example.smp.notification.repository.INotificationRepository;
import com.example.smp.notification.utils.TestDataFactory;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    @Mock
    private INotificationRepository notificationRepository;

    @Mock
    private INotificationMapper notificationMapper;

    private NotificationServiceImpl notificationService;

    @BeforeEach
    void setUp() {
        notificationService = new NotificationServiceImpl(notificationRepository, notificationMapper);
    }

    @Test
    void createNotification_ValidRequest_Returns201Response() {
        NotificationHeaders headers = TestDataFactory.buildHeaders();
        NotificationRequestBody body = TestDataFactory.buildRequestBody();
        NotificationRequest request = TestDataFactory.buildNotificationRequest();
        NotificationResponse response = NotificationResponse.builder()
                .id(request.getId())
                .title(request.getTitle())
                .message(request.getMessage())
                .projectId(request.getProjectId())
                .requestId(request.getRequestId())
                .build();

        when(notificationMapper.toNotificationRequest(any(NotificationHeaders.class), any(NotificationRequestBody.class)))
                .thenReturn(request);
        when(notificationMapper.toNotificationResponse(any(NotificationRequest.class)))
                .thenReturn(response);
        when(notificationRepository.save(any(NotificationRequest.class)))
                .thenReturn(Mono.just(ResponseEntity.ok(request)));

        StepVerifier.create(notificationService.createNotification(headers, body))
                .expectNextMatches(serverResponse -> serverResponse.statusCode().value() == 201)
                .verifyComplete();
    }

    @Test
    void getNotification_ExistingId_Returns200Response() {
        NotificationHeaders headers = TestDataFactory.buildHeaders();
        NotificationRequest request = TestDataFactory.buildNotificationRequest();
        NotificationResponse response = NotificationResponse.builder()
                .id(request.getId())
                .title(request.getTitle())
                .build();

        when(notificationRepository.findById(any(String.class)))
                .thenReturn(Mono.just(ResponseEntity.ok(request)));
        when(notificationMapper.toNotificationResponse(any(NotificationRequest.class)))
                .thenReturn(response);

        StepVerifier.create(notificationService.getNotification(headers, request.getId()))
                .expectNextMatches(serverResponse -> serverResponse.statusCode().value() == 200)
                .verifyComplete();
    }

    @Test
    void getNotification_NotExistingId_ReturnsError() {
        NotificationHeaders headers = TestDataFactory.buildHeaders();

        when(notificationRepository.findById("not-exist"))
                .thenReturn(Mono.error(new RuntimeException("Not found")));

        StepVerifier.create(notificationService.getNotification(headers, "not-exist"))
                .expectError()
                .verify();
    }
}
