package com.example.smp.notification.repository.mock;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import com.example.smp.notification.models.dtos.response.NotificationResponse;
import com.example.smp.notification.models.dtos.validator.CreateNotificationRequestBody;
import com.example.smp.notification.models.dtos.validator.NotificationHeaders;
import com.example.smp.notification.repository.INotificationRepository;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MockNotificationRepository implements INotificationRepository {

    private final ConcurrentHashMap<String, NotificationResponse> mockStore = new ConcurrentHashMap<>();

    @Override
    public Mono<ResponseEntity<NotificationResponse>> saveNotification(
            NotificationHeaders headers, CreateNotificationRequestBody body) {
        String id = UUID.randomUUID().toString();
        NotificationResponse notification = NotificationResponse.builder()
                .id(id)
                .title(body.getTitle())
                .message(body.getMessage())
                .projectId(body.getProjectId())
                .createdAt(Instant.now().toString())
                .requestId(headers.requestId())
                .build();
        mockStore.put(id, notification);
        return Mono.just(ResponseEntity.status(HttpStatus.CREATED).body(notification));
    }

    @Override
    public Mono<ResponseEntity<NotificationResponse>> findNotificationById(
            NotificationHeaders headers, String id) {
        NotificationResponse notification = mockStore.get(id);
        if (notification == null) {
            notification = NotificationResponse.builder()
                    .id(id)
                    .title("Mock Notification")
                    .message("This is a mock notification message")
                    .projectId("mock-project-001")
                    .createdAt(Instant.now().toString())
                    .requestId(headers.requestId())
                    .build();
        }
        return Mono.just(ResponseEntity.ok(notification));
    }
}
