package com.example.smp.notification.repository.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.smp.notification.models.dtos.request.NotificationRequest;
import com.example.smp.notification.utils.TestDataFactory;
import com.example.smp.exception.AppException;
import reactor.test.StepVerifier;

import java.util.UUID;

class NotificationRepositoryImplTest {

    private NotificationRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        repository = new NotificationRepositoryImpl();
    }

    @Test
    void save_ValidNotification_ReturnsOk() {
        NotificationRequest request = TestDataFactory.buildNotificationRequest();

        StepVerifier.create(repository.save(request))
                .expectNextMatches(response ->
                        response.getStatusCode().value() == 200
                        && response.getBody() != null
                        && response.getBody().getId().equals(request.getId()))
                .verifyComplete();
    }

    @Test
    void findById_ExistingId_ReturnsNotification() {
        NotificationRequest request = TestDataFactory.buildNotificationRequest();
        repository.save(request).block();

        StepVerifier.create(repository.findById(request.getId()))
                .expectNextMatches(response ->
                        response.getStatusCode().value() == 200
                        && response.getBody() != null
                        && response.getBody().getId().equals(request.getId()))
                .verifyComplete();
    }

    @Test
    void findById_NotExistingId_ReturnsError() {
        StepVerifier.create(repository.findById("non-existent-" + UUID.randomUUID()))
                .expectError(AppException.class)
                .verify();
    }
}
