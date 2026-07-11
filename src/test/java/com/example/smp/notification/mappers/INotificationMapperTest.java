package com.example.smp.notification.mappers;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import com.example.smp.notification.contants.AppConstants;
import com.example.smp.notification.models.dtos.validator.NotificationHeaders;
import com.example.smp.notification.utils.TestDataFactory;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class INotificationMapperTest {

    private final INotificationMapper mapper = Mappers.getMapper(INotificationMapper.class);

    @Test
    void toHttpHeaders_MapsAllHeaders() {
        NotificationHeaders headers = TestDataFactory.buildHeaders();
        HttpHeaders httpHeaders = mapper.toHttpHeaders(headers);

        assertThat(httpHeaders.getFirst(AppConstants.Headers.CONSUMER_ID))
                .isEqualTo(TestDataFactory.TEST_CONSUMER_ID);
        assertThat(httpHeaders.getFirst(AppConstants.Headers.SESSION_ID))
                .isEqualTo(TestDataFactory.TEST_SESSION_ID);
        assertThat(httpHeaders.getFirst(AppConstants.Headers.X_REQUEST_ID))
                .isEqualTo(TestDataFactory.TEST_REQUEST_ID);
    }

    @Test
    void toHttpHeaders_NullRequestId_DoesNotAddHeader() {
        NotificationHeaders headers = NotificationHeaders.builder()
                .consumerId("SMP")
                .sessionId("sess-001")
                .requestId(null)
                .build();
        HttpHeaders httpHeaders = mapper.toHttpHeaders(headers);

        assertThat(httpHeaders.getFirst(AppConstants.Headers.X_REQUEST_ID)).isNull();
    }
}
