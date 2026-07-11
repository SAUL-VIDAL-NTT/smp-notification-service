package com.example.smp.notification.utils;

import com.example.smp.notification.models.dtos.response.NotificationResponse;
import com.example.smp.notification.models.dtos.validator.CreateNotificationRequestBody;
import com.example.smp.notification.models.dtos.validator.NotificationHeaders;

import java.time.Instant;

public class TestDataFactory {

    public static final String TEST_CONSUMER_ID = "SMP";
    public static final String TEST_SESSION_ID = "session-001";
    public static final String TEST_SESSION_APP_ID = "SMP-TEST";
    public static final String TEST_CUSTOMER_ID = "customer-001";
    public static final String TEST_DEVICE_ID = "device-001";
    public static final String TEST_DEVICE_TYPE = "AND";
    public static final String TEST_AUTHORIZATION = "Bearer test-token";
    public static final String TEST_REQUEST_ID = "req-001-uuid";
    public static final String TEST_NOTIFICATION_ID = "notif-001-uuid";
    public static final String TEST_TITLE = "Build Completed";
    public static final String TEST_MESSAGE = "Project myapp build #42 completed";
    public static final String TEST_PROJECT_ID = "proj-abc-123";

    public static NotificationHeaders buildHeaders() {
        return NotificationHeaders.builder()
                .consumerId(TEST_CONSUMER_ID)
                .sessionId(TEST_SESSION_ID)
                .sessionAppId(TEST_SESSION_APP_ID)
                .customerId(TEST_CUSTOMER_ID)
                .deviceId(TEST_DEVICE_ID)
                .deviceType(TEST_DEVICE_TYPE)
                .authorization(TEST_AUTHORIZATION)
                .requestId(TEST_REQUEST_ID)
                .build();
    }

    public static CreateNotificationRequestBody buildCreateBody() {
        return CreateNotificationRequestBody.builder()
                .title(TEST_TITLE)
                .message(TEST_MESSAGE)
                .projectId(TEST_PROJECT_ID)
                .build();
    }

    public static NotificationResponse buildNotificationResponse() {
        return NotificationResponse.builder()
                .id(TEST_NOTIFICATION_ID)
                .title(TEST_TITLE)
                .message(TEST_MESSAGE)
                .projectId(TEST_PROJECT_ID)
                .createdAt(Instant.now().toString())
                .requestId(TEST_REQUEST_ID)
                .build();
    }
}
