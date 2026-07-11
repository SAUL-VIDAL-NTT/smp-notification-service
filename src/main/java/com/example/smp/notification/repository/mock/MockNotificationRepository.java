package com.example.smp.notification.repository.mock;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import com.example.smp.notification.models.dtos.request.NotificationRequest;
import com.example.smp.notification.repository.INotificationRepository;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MockNotificationRepository implements INotificationRepository {

    private final ConcurrentHashMap<String, NotificationRequest> mockStore = new ConcurrentHashMap<>();

    @Override
    public Mono<ResponseEntity<NotificationRequest>> save(NotificationRequest notification) {
        if (notification.getId() == null) {
            notification.setId("mock-" + UUID.randomUUID());
        }
        if (notification.getCreatedAt() == null) {
            notification.setCreatedAt(Instant.now().toString());
        }
        mockStore.put(notification.getId(), notification);
        return Mono.just(ResponseEntity.ok(notification));
    }

    @Override
    public Mono<ResponseEntity<NotificationRequest>> findById(String id) {
        NotificationRequest mock = mockStore.get(id);
        if (mock == null) {
            mock = NotificationRequest.builder()
                    .id(id)
                    .title("Mock notification title")
                    .message("Mock notification message")
                    .projectId("MOCK-PROJECT-1")
                    .severity("medium")
                    .createdAt(Instant.now().toString())
                    .requestId("mock-req-" + UUID.randomUUID())
                    .build();
        }
        return Mono.just(ResponseEntity.ok(mock));
    }
}
