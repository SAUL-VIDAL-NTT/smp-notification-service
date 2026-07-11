package com.example.smp.notification.mappers;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import com.example.smp.notification.models.dtos.request.NotificationRequest;
import com.example.smp.notification.models.dtos.response.NotificationResponse;
import com.example.smp.notification.models.dtos.validator.NotificationHeaders;
import com.example.smp.notification.models.dtos.validator.NotificationRequestBody;
import com.example.smp.notification.utils.TestDataFactory;

import static org.assertj.core.api.Assertions.assertThat;

class INotificationMapperTest {

    private final INotificationMapper mapper = Mappers.getMapper(INotificationMapper.class);

    @Test
    void toNotificationRequest_MapsHeadersAndBody() {
        NotificationHeaders headers = TestDataFactory.buildHeaders();
        NotificationRequestBody body = TestDataFactory.buildRequestBody();

        NotificationRequest result = mapper.toNotificationRequest(headers, body);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo(body.getTitle());
        assertThat(result.getMessage()).isEqualTo(body.getMessage());
        assertThat(result.getProjectId()).isEqualTo(body.getProjectId());
        assertThat(result.getSeverity()).isEqualTo(body.getSeverity());
        assertThat(result.getRequestId()).isEqualTo(headers.requestId());
    }

    @Test
    void toNotificationResponse_MapsRequest() {
        NotificationRequest request = TestDataFactory.buildNotificationRequest();

        NotificationResponse result = mapper.toNotificationResponse(request);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(request.getId());
        assertThat(result.getTitle()).isEqualTo(request.getTitle());
        assertThat(result.getRequestId()).isEqualTo(request.getRequestId());
    }

    @Test
    void toHttpHeaders_MapsAllHeaders() {
        NotificationHeaders headers = TestDataFactory.buildHeaders();

        var httpHeaders = mapper.toHttpHeaders(headers);

        assertThat(httpHeaders).isNotNull();
        assertThat(httpHeaders.getFirst("consumerId")).isEqualTo(headers.consumerId());
    }
}
