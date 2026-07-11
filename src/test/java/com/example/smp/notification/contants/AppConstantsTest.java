package com.example.smp.notification.contants;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AppConstantsTest {

    @Test
    void headers_Constants_AreDefined() {
        assertThat(AppConstants.Headers.CONSUMER_ID).isEqualTo("consumerId");
        assertThat(AppConstants.Headers.SESSION_ID).isEqualTo("sessionId");
        assertThat(AppConstants.Headers.AUTHORIZATION).isEqualTo("Authorization");
        assertThat(AppConstants.Headers.X_REQUEST_ID).isEqualTo("X-Request-Id");
    }

    @Test
    void mocks_Constants_AreDefined() {
        assertThat(AppConstants.Mocks.MOCK_NOTIFICATION).isEqualTo("mock-notification");
    }

    @Test
    void endpoints_Constants_AreDefined() {
        assertThat(AppConstants.EndPoints.NOTIFICATIONS_PATH).isEqualTo("/notifications");
        assertThat(AppConstants.EndPoints.NOTIFICATIONS_BY_ID_PATH).isEqualTo("/notifications/{id}");
    }

    @Test
    void errors_Constants_AreDefined() {
        assertThat(AppConstants.Errors.GENERIC_ERROR_CODE).isEqualTo("9999");
        assertThat(AppConstants.Errors.ERROR_CODE_NOT_FOUND).isEqualTo("NTF-BFF-404-001");
    }

    @Test
    void general_Constants_AreDefined() {
        assertThat(AppConstants.General.SUCCESS_CODE).isEqualTo("0000");
        assertThat(AppConstants.General.PLATFORM_ID).isEqualTo("NTF");
    }
}
