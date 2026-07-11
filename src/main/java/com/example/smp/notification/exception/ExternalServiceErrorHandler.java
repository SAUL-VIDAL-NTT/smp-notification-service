package com.example.smp.notification.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.smp.notification.configurations.StaticPropertyConfig;
import com.example.smp.notification.contants.AppConstants;
import com.example.smp.types.AppHttpStatusCode;
import com.example.smp.exception.AppException;
import com.example.smp.dto.response.AppStatus;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeoutException;

public class ExternalServiceErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(ExternalServiceErrorHandler.class);

    private ExternalServiceErrorHandler() {
    }

    public static <T> Mono<T> error(Throwable error) {
        if (logger.isErrorEnabled()) {
            logger.error("{\"errorMessage\": \"{}\", \"errorClass\": \"{}\"}",
                    error.getMessage(), error.getClass().getName());
        }

        if (error instanceof TimeoutException) {
            return Mono.error(new AppException(
                    AppConstants.Errors.GENERIC_ERROR_CODE, "Timeout", error,
                    AppHttpStatusCode.REQUEST_TIMEOUT));
        }

        if (error instanceof AppException ex
                && ex.getHttpStatusCode() == AppHttpStatusCode.FUNCTIONAL_ERROR) {

            AppException fallback = new AppException(
                    AppConstants.Errors.GENERIC_ERROR_CODE,
                    AppConstants.Errors.GENERIC_ERROR_MESSAGE,
                    AppHttpStatusCode.FUNCTIONAL_ERROR);

            if (StaticPropertyConfig.getErrorMessages() == null) {
                return Mono.error(fallback);
            }

            AppStatus status = StaticPropertyConfig.getErrorMessages().get(ex.getErrorCode());
            if (status == null) {
                return Mono.error(fallback);
            }

            return Mono.error(new AppException(
                    status.getCode(), status.getMessage(), AppHttpStatusCode.FUNCTIONAL_ERROR));
        }

        return Mono.error(new AppException(
                AppConstants.Errors.GENERIC_ERROR_CODE,
                AppConstants.Errors.GENERIC_ERROR_MESSAGE,
                error,
                AppHttpStatusCode.INTERNAL_SERVER_ERROR));
    }
}
