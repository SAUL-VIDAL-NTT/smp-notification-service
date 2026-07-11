package com.example.smp.notification.exception;

import org.junit.jupiter.api.Test;
import com.example.smp.exception.AppException;
import com.example.smp.types.AppHttpStatusCode;
import reactor.test.StepVerifier;

import java.util.concurrent.TimeoutException;

class ExternalServiceErrorHandlerTest {

    @Test
    void error_TimeoutException_ReturnsAppExceptionWithRequestTimeout() {
        TimeoutException cause = new TimeoutException("Timeout");

        StepVerifier.create(ExternalServiceErrorHandler.error(cause))
                .expectErrorMatches(ex ->
                        ex instanceof AppException appEx
                        && appEx.getHttpStatusCode() == AppHttpStatusCode.REQUEST_TIMEOUT)
                .verify();
    }

    @Test
    void error_GenericException_ReturnsAppExceptionWithInternalServerError() {
        RuntimeException cause = new RuntimeException("Unexpected error");

        StepVerifier.create(ExternalServiceErrorHandler.error(cause))
                .expectErrorMatches(ex ->
                        ex instanceof AppException appEx
                        && appEx.getHttpStatusCode() == AppHttpStatusCode.INTERNAL_SERVER_ERROR)
                .verify();
    }
}
