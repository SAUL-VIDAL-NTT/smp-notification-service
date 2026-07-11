package com.example.smp.notification.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AppUtilsTest {

    @Test
    void returnError_AppException_ReturnsErrorMono() {
        com.example.smp.exception.AppException ex = new com.example.smp.exception.AppException(
                "9999", "Error", com.example.smp.types.AppHttpStatusCode.INTERNAL_SERVER_ERROR);

        reactor.test.StepVerifier.create(AppUtils.returnError(ex))
                .expectError(com.example.smp.exception.AppException.class)
                .verify();
    }

    @Test
    void returnError_GenericException_ReturnsAppException() {
        RuntimeException ex = new RuntimeException("Generic error");

        reactor.test.StepVerifier.create(AppUtils.returnError(ex))
                .expectError(com.example.smp.exception.AppException.class)
                .verify();
    }
}
