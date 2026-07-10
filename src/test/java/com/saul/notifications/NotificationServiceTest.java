package com.saul.notifications;

import com.saul.notifications.domain.Notification;
import com.saul.notifications.dto.CreateNotificationRequest;
import com.saul.notifications.dto.NotificationResponse;
import com.saul.notifications.exception.NotificationNotFoundException;
import com.saul.notifications.repository.NotificationRepository;
import com.saul.notifications.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private NotificationRepository repository;

    @InjectMocks
    private NotificationService service;

    private Notification sampleNotification;

    @BeforeEach
    void setUp() {
        sampleNotification = new Notification(
                "notif_abc123",
                "Test Title",
                "Test Message",
                "user_001",
                "TEST_TYPE",
                Instant.now()
        );
    }

    @Test
    void create_validRequest_returnsNotificationResponse() {
        when(repository.save(any(Notification.class))).thenReturn(Mono.just(sampleNotification));

        CreateNotificationRequest request = new CreateNotificationRequest();
        request.setTitle("Test Title");
        request.setMessage("Test Message");
        request.setRecipientId("user_001");
        request.setType("TEST_TYPE");

        StepVerifier.create(service.create(request))
                .assertNext(response -> {
                    assertThat(response.getId()).isEqualTo("notif_abc123");
                    assertThat(response.getTitle()).isEqualTo("Test Title");
                    assertThat(response.getMessage()).isEqualTo("Test Message");
                    assertThat(response.getRecipientId()).isEqualTo("user_001");
                    assertThat(response.getType()).isEqualTo("TEST_TYPE");
                    assertThat(response.getCreatedAt()).isNotNull();
                })
                .verifyComplete();
    }

    @Test
    void findById_existingId_returnsNotificationResponse() {
        when(repository.findById("notif_abc123")).thenReturn(Mono.just(sampleNotification));

        StepVerifier.create(service.findById("notif_abc123"))
                .assertNext(response -> {
                    assertThat(response.getId()).isEqualTo("notif_abc123");
                    assertThat(response.getTitle()).isEqualTo("Test Title");
                })
                .verifyComplete();
    }

    @Test
    void findById_nonExistingId_throwsNotificationNotFoundException() {
        when(repository.findById("notif_unknown")).thenReturn(Mono.empty());

        StepVerifier.create(service.findById("notif_unknown"))
                .expectError(NotificationNotFoundException.class)
                .verify();
    }

    @Test
    void create_generatesUniqueId() {
        Notification n1 = new Notification("notif_111", "T", "M", "r", "T", Instant.now());
        Notification n2 = new Notification("notif_222", "T", "M", "r", "T", Instant.now());

        when(repository.save(any())).thenReturn(Mono.just(n1), Mono.just(n2));

        CreateNotificationRequest request = new CreateNotificationRequest();
        request.setTitle("T");
        request.setMessage("M");
        request.setRecipientId("r");
        request.setType("T");

        NotificationResponse r1 = service.create(request).block();
        NotificationResponse r2 = service.create(request).block();

        assertThat(r1).isNotNull();
        assertThat(r2).isNotNull();
        assertThat(r1.getId()).isNotEqualTo(r2.getId());
    }
}
