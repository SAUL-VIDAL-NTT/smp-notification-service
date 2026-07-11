package com.example.smp.notification.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.smp.notification.models.dtos.response.NotificationResponse;
import com.example.smp.notification.models.dtos.validator.CreateNotificationRequestBody;
import com.example.smp.notification.models.dtos.validator.NotificationHeaders;
import com.example.smp.notification.repository.INotificationRepository;
import com.example.smp.notification.utils.TestDataFactory;
import com.example.smp.exception.AppException;
import com.example.smp.types.AppHttpStatusCode;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    @Mock
    private INotificationRepository notificationRepository;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Test
    void createNotification_Success_Returns201() {
        NotificationResponse response = TestDataFactory.buildNotificationResponse();
        when(notificationRepository.saveNotification(any(NotificationHeaders.class),
                any(CreateNotificationRequestBody.class)))
                .thenReturn(Mono.just(ResponseEntity.status(HttpStatus.CREATED).body(response)));

        StepVerifier.create(notificationService.createNotification(
                        TestDataFactory.buildHeaders(), TestDataFactory.buildCreateBody()))
                .expectNextMatches(sr -> sr.statusCode().value() == 201)
                .verifyComplete();
    }

    @Test
    void createNotification_RepositoryError_PropagatesError() {
        when(notificationRepository.saveNotification(any(NotificationHeaders.class),
                any(CreateNotificationRequestBody.class)))
                .thenReturn(Mono.error(new AppException("9999", "Error",
                        AppHttpStatusCode.INTERNAL_SERVER_ERROR)));

        StepVerifier.create(notificationService.createNotification(
                        TestDataFactory.buildHeaders(), TestDataFactory.buildCreateBody()))
                .expectError(AppException.class)
                .verify();
    }

    @Test
    void getNotificationById_Found_Returns200() {
        NotificationResponse response = TestDataFactory.buildNotificationResponse();
        when(notificationRepository.findNotificationById(any(NotificationHeaders.class), anyString()))
                .thenReturn(Mono.just(ResponseEntity.ok(response)));

        StepVerifier.create(notificationService.getNotificationById(
                        TestDataFactory.buildHeaders(), TestDataFactory.TEST_NOTIFICATION_ID))
                .expectNextMatches(sr -> sr.statusCode().value() == 200)
                .verifyComplete();
    }

    @Test
    void getNotificationById_NotFound_Propagates404() {
        when(notificationRepository.findNotificationById(any(NotificationHeaders.class), anyString()))
                .thenReturn(Mono.error(new AppException("NTF-BFF-404-001", "Not found",
                        AppHttpStatusCode.NOT_FOUND)));

        StepVerifier.create(notificationService.getNotificationById(
                        TestDataFactory.buildHeaders(), "non-existent-id"))
                .expectError(AppException.class)
                .verify();
    }

    @Test
    void createNotification_EmptyResponse_ReturnsError() {
        when(notificationRepository.saveNotification(any(NotificationHeaders.class),
                any(CreateNotificationRequestBody.class)))
                .thenReturn(Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .<NotificationResponse>build()));

        StepVerifier.create(notificationService.createNotification(
                        TestDataFactory.buildHeaders(), TestDataFactory.buildCreateBody()))
                .expectNextMatches(sr -> sr.statusCode().value() == 500)
                .verifyComplete();
    }
}
