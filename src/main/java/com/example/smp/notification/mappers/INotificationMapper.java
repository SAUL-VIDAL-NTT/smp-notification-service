package com.example.smp.notification.mappers;

import org.mapstruct.Mapper;
import org.springframework.http.HttpHeaders;
import com.example.smp.notification.contants.AppConstants;
import com.example.smp.notification.models.dtos.validator.NotificationHeaders;

@Mapper(componentModel = "spring")
public interface INotificationMapper {

    default HttpHeaders toHttpHeaders(NotificationHeaders headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(AppConstants.Headers.CONSUMER_ID, headers.consumerId());
        httpHeaders.add(AppConstants.Headers.SESSION_APP_ID, headers.sessionAppId());
        httpHeaders.add(AppConstants.Headers.SESSION_ID, headers.sessionId());
        httpHeaders.add(AppConstants.Headers.CUSTOMER_ID, headers.customerId());
        httpHeaders.add(AppConstants.Headers.DEVICE_ID, headers.deviceId());
        httpHeaders.add(AppConstants.Headers.DEVICE_TYPE, headers.deviceType());
        if (headers.requestId() != null) {
            httpHeaders.add(AppConstants.Headers.X_REQUEST_ID, headers.requestId());
        }
        return httpHeaders;
    }
}
