package com.example.smp.notification.repository.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import com.example.smp.notification.utils.TestDataFactory;
import reactor.test.StepVerifier;

class MockNotificationRepositoryTest {

    private MockNotificationRepository mockRepository;

    @BeforeEach
    void setUp() {
        mockRepository = new MockNotificationRepository();
    }

    @Test
    void saveNotification_ReturnsMockedResponse() {
        StepVerifier.create(mockRepository.saveNotification(
                        TestDataFactory.buildHeaders(), TestDataFactory.buildCreateBody()))
                .expectNextMatches(r -> {
                    assert r.getStatusCode() == HttpStatus.CREATED;
                    assert r.getBody() != null;
                    assert r.getBody().getId() != null;
                    return true;
                })
                .verifyComplete();
    }

    @Test
    void findNotificationById_ReturnsPersistedOrMockFallback() {
        StepVerifier.create(mockRepository.findNotificationById(
                        TestDataFactory.buildHeaders(), "some-id"))
                .expectNextMatches(r -> {
                    assert r.getStatusCode() == HttpStatus.OK;
                    assert r.getBody() != null;
                    return true;
                })
                .verifyComplete();
    }
}
