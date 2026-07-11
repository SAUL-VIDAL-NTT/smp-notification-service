package com.example.smp.notification.utils;

import com.example.smp.notification.models.dtos.request.NotificationRequest;
import com.example.smp.notification.models.dtos.validator.NotificationHeaders;
import com.example.smp.notification.models.dtos.validator.NotificationRequestBody;

import java.time.Instant;
import java.util.UUID;

public class TestDataFactory {

    public static NotificationHeaders buildHeaders() {
        return NotificationHeaders.builder()
                .consumerId("SMP")
                .sessionId(UUID.randomUUID().toString())
                .sessionAppId("APP_SMP")
                .customerId("customer-001")
                .deviceId("device-001")
                .deviceType("AND")
                .monitorData("monitorData")
                .authorization("Bearer test-token")
                .requestId("req-" + UUID.randomUUID())
                .build();
    }

    public static NotificationRequestBody buildRequestBody() {
        return NotificationRequestBody.builder()
                .title("Project milestone updated")
                .message("The project moved to In Progress")
                .projectId("KAN-100")
                .severity("medium")
                .build();
    }

    public static NotificationRequest buildNotificationRequest() {
        return NotificationRequest.builder()
                .id("notif-" + UUID.randomUUID().toString().substring(0, 12))
                .title("Project milestone updated")
                .message("The project moved to In Progress")
                .projectId("KAN-100")
                .severity("medium")
                .createdAt(Instant.now().toString())
                .requestId("req-" + UUID.randomUUID())
                .build();
    }
}
