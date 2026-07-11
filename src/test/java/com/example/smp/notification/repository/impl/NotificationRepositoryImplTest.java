package com.example.smp.notification.repository.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import com.example.smp.notification.models.dtos.validator.CreateNotificationRequestBody;
import com.example.smp.notification.models.dtos.validator.NotificationHeaders;
import com.example.smp.notification.utils.TestDataFactory;
import com.example.smp.exception.AppException;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class NotificationRepositoryImplTest {

    private NotificationRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        repository = new NotificationRepositoryImpl();
    }

    @Test
    void saveNotification_ValidInput_ReturnsPersisted() {
        NotificationHeaders headers = TestDataFactory.buildHeaders();
        CreateNotificationRequestBody body = TestDataFactory.buildCreateBody();

        StepVerifier.create(repository.saveNotification(headers, body))
                .expectNextMatches(responseEntity -> {
                    assert responseEntity.getStatusCode() == HttpStatus.CREATED;
                    assert responseEntity.getBody() != null;
                    assert responseEntity.getBody().getId() != null;
                    assert TestDataFactory.TEST_TITLE.equals(responseEntity.getBody().getTitle());
                    assert TestDataFactory.TEST_MESSAGE.equals(responseEntity.getBody().getMessage());
                    assert TestDataFactory.TEST_PROJECT_ID.equals(responseEntity.getBody().getProjectId());
                    assert responseEntity.getBody().getCreatedAt() != null;
                    return true;
                })
                .verifyComplete();
    }

    @Test
    void findNotificationById_ExistingId_ReturnsNotification() {
        NotificationHeaders headers = TestDataFactory.buildHeaders();
        CreateNotificationRequestBody body = TestDataFactory.buildCreateBody();

        StepVerifier.create(
                repository.saveNotification(headers, body)
                        .flatMap(saved -> repository.findNotificationById(headers,
                                saved.getBody().getId()))
        )
                .expectNextMatches(found -> {
                    assert found.getStatusCode() == HttpStatus.OK;
                    assert found.getBody() != null;
                    assert TestDataFactory.TEST_TITLE.equals(found.getBody().getTitle());
                    return true;
                })
                .verifyComplete();
    }

    @Test
    void findNotificationById_NonExistingId_Returns404Error() {
        NotificationHeaders headers = TestDataFactory.buildHeaders();

        StepVerifier.create(repository.findNotificationById(headers, "non-existent-id"))
                .expectErrorMatches(err -> err instanceof AppException
                        && "NTF-BFF-404-001".equals(((AppException) err).getErrorCode()))
                .verify();
    }

    @Test
    void saveNotification_GeneratesUniqueIds() {
        NotificationHeaders headers = TestDataFactory.buildHeaders();
        CreateNotificationRequestBody body = TestDataFactory.buildCreateBody();

        var id1Mono = repository.saveNotification(headers, body)
                .map(r -> r.getBody().getId());
        var id2Mono = repository.saveNotification(headers, body)
                .map(r -> r.getBody().getId());

        StepVerifier.create(id1Mono.zipWith(id2Mono))
                .expectNextMatches(tuple -> !tuple.getT1().equals(tuple.getT2()))
                .verifyComplete();
    }
}
