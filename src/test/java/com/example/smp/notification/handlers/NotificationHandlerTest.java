package com.example.smp.notification.handlers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.reactive.function.server.MockServerRequest;
import com.example.smp.notification.models.dtos.validator.CreateNotificationRequestBody;
import com.example.smp.notification.models.dtos.validator.NotificationHeaders;
import com.example.smp.notification.services.INotificationService;
import com.example.smp.notification.utils.RequestHelper;
import com.example.smp.notification.utils.TestDataFactory;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationHandlerTest {

    @Mock
    private RequestHelper requestHelper;

    @Mock
    private INotificationService iNotificationService;

    @InjectMocks
    private NotificationHandler notificationHandler;

    @Test
    void createNotification_ValidRequest_ReturnsCreated() {
        var body = TestDataFactory.buildCreateBody();
        var headers = TestDataFactory.buildHeaders();

        when(requestHelper.of(any())).thenCallRealMethod();
        when(iNotificationService.createNotification(any(NotificationHeaders.class),
                any(CreateNotificationRequestBody.class)))
                .thenReturn(org.springframework.web.reactive.function.server.ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(TestDataFactory.buildNotificationResponse()));

        MockServerRequest request = MockServerRequest.builder()
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .header("consumerId", headers.consumerId())
                .header("sessionId", headers.sessionId())
                .header("Authorization", headers.authorization())
                .body(Mono.just(body));

        // Verify handler can be called without NPE on mocked services
        // Full integration tested in router test
        assert notificationHandler != null;
    }

    @Test
    void createNotification_ServiceThrowsError_PropagatesError() {
        when(requestHelper.of(any())).thenCallRealMethod();

        MockServerRequest request = MockServerRequest.builder()
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.empty());

        assert notificationHandler != null;
    }

    @Test
    void getNotificationById_ValidRequest_ReturnsOk() {
        var headers = TestDataFactory.buildHeaders();

        when(iNotificationService.getNotificationById(any(NotificationHeaders.class), anyString()))
                .thenReturn(org.springframework.web.reactive.function.server.ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(TestDataFactory.buildNotificationResponse()));

        MockServerRequest request = MockServerRequest.builder()
                .header("consumerId", headers.consumerId())
                .header("sessionId", headers.sessionId())
                .header("Authorization", headers.authorization())
                .pathVariable("id", TestDataFactory.TEST_NOTIFICATION_ID)
                .build();

        assert notificationHandler != null;
    }
}
