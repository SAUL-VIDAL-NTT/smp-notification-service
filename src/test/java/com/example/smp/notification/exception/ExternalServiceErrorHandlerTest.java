package com.example.smp.notification.exception;

import org.junit.jupiter.api.Test;
import com.example.smp.exception.AppException;
import com.example.smp.types.AppHttpStatusCode;
import reactor.test.StepVerifier;

import java.util.concurrent.TimeoutException;

class ExternalServiceErrorHandlerTest {

    @Test
    void error_TimeoutException_ReturnsTimeoutAppException() {
        StepVerifier.create(ExternalServiceErrorHandler.error(new TimeoutException("timeout")))
                .expectErrorMatches(e -> e instanceof AppException
                        && ((AppException) e).getHttpStatusCode() == AppHttpStatusCode.REQUEST_TIMEOUT)
                .verify();
    }

    @Test
    void error_GenericException_ReturnsInternalServerError() {
        StepVerifier.create(ExternalServiceErrorHandler.error(
                        new RuntimeException("unexpected error")))
                .expectErrorMatches(e -> e instanceof AppException
                        && ((AppException) e).getHttpStatusCode() == AppHttpStatusCode.INTERNAL_SERVER_ERROR)
                .verify();
    }

    @Test
    void error_AppException_FunctionalError_ReturnsFunctionalError() {
        AppException functionalEx = new AppException("ERR-001", "Functional",
                AppHttpStatusCode.FUNCTIONAL_ERROR);

        StepVerifier.create(ExternalServiceErrorHandler.error(functionalEx))
                .expectErrorMatches(e -> e instanceof AppException)
                .verify();
    }
}
