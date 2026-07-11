package com.example.smp.notification.contants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppConstants {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class General {
        public static final String SUCCESS_CODE = "0000";
        public static final String SUCCESS_MESSAGE = "Success response";
        public static final String PLATFORM_ID = "NTF";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Headers {
        public static final String CONSUMER_ID = "consumerId";
        public static final String SESSION_ID = "sessionId";
        public static final String SESSION_APP_ID = "sessionAppId";
        public static final String MONITOR_DATA = "monitorData";
        public static final String CUSTOMER_ID = "customerId";
        public static final String DEVICE_ID = "deviceId";
        public static final String DEVICE_TYPE = "deviceType";
        public static final String AUTHORIZATION = "Authorization";
        public static final String TRACE_PARENT = "traceparent";
        public static final String API_VERSION = "apiVersion";
        public static final String SUBSCRIPTION_KEY = "subscription-key";
        public static final String ACCOUNT_NUMBER = "accountNumber";
        public static final String X_REQUEST_ID = "X-Request-Id";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Mocks {
        public static final String MOCK_NOTIFICATION = "mock-notification";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class EndPoints {
        public static final String NOTIFICATIONS_PATH = "/notifications";
        public static final String NOTIFICATIONS_BY_ID_PATH = "/notifications/{id}";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Message {
        public static final String TOKEN_EMPTY = "Access token is empty or null";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Errors {
        public static final String MESSAGE_TRACE_COMMON =
                "[SMP_APP_CU]: {} [*] [SMP_APP_DEVICE_TYPE]: {} [*] [SMP_APP_TRACEPARENT]: {} "
              + "[*] [SMP_APP_SESSION_APP_ID]: {} [*] [SMP_PRODUCT_NAME]: {} [*] [SMP_URL_TEMPLATE]: {}";

        public static final String GENERIC_ERROR_CODE = "9999";
        public static final String GENERIC_ERROR_MESSAGE =
                "Lo sentimos, no hemos podido realizar tu operación. Estamos trabajando para solucionar el inconveniente.";

        public static final String ERROR_CODE_INVALID_TOKEN = "NTF-BFF-401-001";
        public static final String ERROR_MESSAGE_INVALID_TOKEN = "Access token inválido o no proporcionado";
        public static final String ERROR_CODE_NOT_FOUND = "NTF-BFF-404-001";
        public static final String ERROR_MESSAGE_NOT_FOUND = "Notification not found";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class QueryParams {
        public static final String CONSUMER_ID = "consumerId";
        public static final String TRACEPARENT = "traceparent";
        public static final String SESSION_ID = "sessionId";
        public static final String SESSION_APP_ID = "sessionAppId";
    }
}
