package com.example.smp.notification.models.dtos.response;

import org.junit.jupiter.api.Test;
import com.example.smp.notification.utils.TestDataFactory;

import static org.assertj.core.api.Assertions.assertThat;

class NotificationResponseTest {

    @Test
    void builder_SetsAllFields() {
        NotificationResponse response = TestDataFactory.buildNotificationResponse();

        assertThat(response.getId()).isEqualTo(TestDataFactory.TEST_NOTIFICATION_ID);
        assertThat(response.getTitle()).isEqualTo(TestDataFactory.TEST_TITLE);
        assertThat(response.getMessage()).isEqualTo(TestDataFactory.TEST_MESSAGE);
        assertThat(response.getProjectId()).isEqualTo(TestDataFactory.TEST_PROJECT_ID);
        assertThat(response.getCreatedAt()).isNotNull();
        assertThat(response.getRequestId()).isEqualTo(TestDataFactory.TEST_REQUEST_ID);
    }
}
