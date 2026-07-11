package com.example.smp.notification.handlers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.reactive.function.server.MockServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import com.example.smp.notification.models.dtos.validator.NotificationHeaders;
import com.example.smp.notification.models.dtos.validator.NotificationRequestBody;
import com.example.smp.notification.services.INotificationService;
import com.example.smp.notification.utils.RequestHelper;
import com.example.smp.notification.utils.TestDataFactory;
import com.example.smp.notification.utils.ValidationBuilder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationHandlerTest {

    @Mock
    private RequestHelper requestHelper;

    @Mock
    private INotificationService iNotificationService;

    private NotificationHandler notificationHandler;

    @BeforeEach
    void setUp() {
        notificationHandler = new NotificationHandler(requestHelper, iNotificationService);
    }

    @Test
    void createNotification_ValidRequest_Returns201() {
        NotificationHeaders headers = TestDataFactory.buildHeaders();
        NotificationRequestBody body = TestDataFactory.buildRequestBody();

        ValidationBuilder<NotificationHeaders> vb = new ValidationBuilder<>(requestHelper, null, Mono.just(headers));

        when(requestHelper.of(any())).thenReturn(
                new ValidationBuilder<>(requestHelper, MockServerRequest.builder().build(), Mono.just(headers)));
        when(iNotificationService.createNotification(any(NotificationHeaders.class), any(NotificationRequestBody.class)))
                .thenReturn(ServerResponse.status(HttpStatus.CREATED).build());

        MockServerRequest serverRequest = MockServerRequest.builder()
                .header("consumerId", "SMP")
                .header("sessionId", "session-123")
                .header("Authorization", "Bearer test-token")
                .body(Mono.just(body));

        StepVerifier.create(notificationHandler.createNotification(serverRequest))
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void getNotificationById_ValidRequest_Returns200() {
        NotificationHeaders headers = TestDataFactory.buildHeaders();

        when(requestHelper.of(any())).thenReturn(
                new ValidationBuilder<>(requestHelper, MockServerRequest.builder().build(), Mono.just(headers)));
        when(iNotificationService.getNotification(any(NotificationHeaders.class), any(String.class)))
                .thenReturn(ServerResponse.ok().build());

        MockServerRequest serverRequest = MockServerRequest.builder()
                .header("consumerId", "SMP")
                .header("Authorization", "Bearer test-token")
                .pathVariable("id", "notif-001")
                .build();

        StepVerifier.create(notificationHandler.getNotificationById(serverRequest))
                .expectNextCount(1)
                .verifyComplete();
    }
}
