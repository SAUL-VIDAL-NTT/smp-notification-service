package com.example.smp.notification.utils;

import org.junit.jupiter.api.Test;
import com.example.smp.exception.AppException;
import com.example.smp.types.AppHttpStatusCode;
import reactor.test.StepVerifier;

class AppUtilsTest {

    @Test
    void returnError_AppException_RethrowsIt() {
        AppException ex = new AppException("9999", "Error", AppHttpStatusCode.INTERNAL_SERVER_ERROR);
        StepVerifier.create(AppUtils.returnError(ex))
                .expectError(AppException.class)
                .verify();
    }

    @Test
    void returnError_GenericException_WrapsInAppException() {
        RuntimeException ex = new RuntimeException("unexpected");
        StepVerifier.create(AppUtils.returnError(ex))
                .expectErrorMatches(e -> e instanceof AppException
                        && ((AppException) e).getHttpStatusCode() == AppHttpStatusCode.INTERNAL_SERVER_ERROR)
                .verify();
    }
}
