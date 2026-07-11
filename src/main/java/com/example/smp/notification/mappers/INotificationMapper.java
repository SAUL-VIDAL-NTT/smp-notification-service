package com.example.smp.notification.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.http.HttpHeaders;
import com.example.smp.notification.contants.AppConstants;
import com.example.smp.notification.models.dtos.request.NotificationRequest;
import com.example.smp.notification.models.dtos.response.NotificationResponse;
import com.example.smp.notification.models.dtos.validator.NotificationHeaders;
import com.example.smp.notification.models.dtos.validator.NotificationRequestBody;

@Mapper(componentModel = "spring")
public interface INotificationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "requestId", source = "headers.requestId")
    @Mapping(target = "title", source = "body.title")
    @Mapping(target = "message", source = "body.message")
    @Mapping(target = "projectId", source = "body.projectId")
    @Mapping(target = "severity", source = "body.severity")
    NotificationRequest toNotificationRequest(NotificationHeaders headers, NotificationRequestBody body);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "requestId", source = "requestId")
    NotificationResponse toNotificationResponse(NotificationRequest request);

    default HttpHeaders toHttpHeaders(NotificationHeaders headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(AppConstants.Headers.CONSUMER_ID, headers.consumerId());
        httpHeaders.add(AppConstants.Headers.SESSION_APP_ID, headers.sessionAppId());
        httpHeaders.add(AppConstants.Headers.SESSION_ID, headers.sessionId());
        httpHeaders.add(AppConstants.Headers.CUSTOMER_ID, headers.customerId());
        httpHeaders.add(AppConstants.Headers.DEVICE_ID, headers.deviceId());
        httpHeaders.add(AppConstants.Headers.DEVICE_TYPE, headers.deviceType());
        if (headers.requestId() != null) {
            httpHeaders.add(AppConstants.Headers.REQUEST_ID, headers.requestId());
        }
        return httpHeaders;
    }
}
