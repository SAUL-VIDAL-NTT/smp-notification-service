package com.example.smp.notification.repository.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.smp.notification.models.dtos.request.NotificationRequest;
import com.example.smp.notification.utils.TestDataFactory;
import reactor.test.StepVerifier;

class MockNotificationRepositoryTest {

    private MockNotificationRepository mockRepository;

    @BeforeEach
    void setUp() {
        mockRepository = new MockNotificationRepository();
    }

    @Test
    void save_ValidNotification_ReturnsOk() {
        NotificationRequest request = TestDataFactory.buildNotificationRequest();

        StepVerifier.create(mockRepository.save(request))
                .expectNextMatches(response ->
                        response.getStatusCode().value() == 200
                        && response.getBody() != null)
                .verifyComplete();
    }

    @Test
    void findById_AnyId_ReturnsMockNotification() {
        StepVerifier.create(mockRepository.findById("any-id"))
                .expectNextMatches(response ->
                        response.getStatusCode().value() == 200
                        && response.getBody() != null
                        && "any-id".equals(response.getBody().getId()))
                .verifyComplete();
    }
}
