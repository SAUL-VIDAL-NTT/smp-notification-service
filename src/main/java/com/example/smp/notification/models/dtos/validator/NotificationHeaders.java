package com.example.smp.notification.models.dtos.validator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import com.example.smp.notification.contants.AppConstants.Headers;

@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public record NotificationHeaders(
        @JsonProperty(Headers.CONSUMER_ID)     String consumerId,
        @JsonProperty(Headers.SESSION_APP_ID)  String sessionAppId,
        @JsonProperty(Headers.SESSION_ID)      String sessionId,
        @JsonProperty(Headers.CUSTOMER_ID)     String customerId,
        @JsonProperty(Headers.DEVICE_ID)       String deviceId,
        @JsonProperty(Headers.DEVICE_TYPE)     String deviceType,
        @JsonProperty(Headers.MONITOR_DATA)    String monitorData,
        @JsonProperty(Headers.AUTHORIZATION)   String authorization,
        @JsonProperty(Headers.X_REQUEST_ID)    String requestId
) {
}
