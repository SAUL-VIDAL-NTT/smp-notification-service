package com.example.smp.notification.contants;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AppConstantsTest {

    @Test
    void generalConstants_HaveExpectedValues() {
        assertThat(AppConstants.General.SUCCESS_CODE).isEqualTo("0000");
        assertThat(AppConstants.General.SUCCESS_MESSAGE).isEqualTo("Success response");
        assertThat(AppConstants.General.PLATFORM_ID).isEqualTo("NTF");
    }

    @Test
    void headerConstants_HaveExpectedValues() {
        assertThat(AppConstants.Headers.CONSUMER_ID).isEqualTo("consumerId");
        assertThat(AppConstants.Headers.AUTHORIZATION).isEqualTo("Authorization");
        assertThat(AppConstants.Headers.REQUEST_ID).isEqualTo("X-Request-Id");
    }

    @Test
    void endpointConstants_HaveExpectedValues() {
        assertThat(AppConstants.EndPoints.NOTIFICATIONS_BASE_PATH).isEqualTo("/notifications");
        assertThat(AppConstants.EndPoints.NOTIFICATION_BY_ID_PATH).isEqualTo("/{id}");
    }

    @Test
    void mocksConstants_HaveExpectedValues() {
        assertThat(AppConstants.Mocks.MOCK_NOTIFICATION).isEqualTo("mock-notification");
    }

    @Test
    void errorsConstants_NotNullOrEmpty() {
        assertThat(AppConstants.Errors.GENERIC_ERROR_CODE).isNotBlank();
        assertThat(AppConstants.Errors.GENERIC_ERROR_MESSAGE).isNotBlank();
        assertThat(AppConstants.Errors.ERROR_CODE_NOT_FOUND).isNotBlank();
    }
}
