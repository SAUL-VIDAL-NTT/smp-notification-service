package com.example.smp.notification.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class RequestHelperTest {

    private RequestHelper requestHelper;

    @BeforeEach
    void setUp() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();
        requestHelper = new RequestHelper(validator, new ObjectMapper());
    }

    @Test
    void requestHelper_ShouldBeCreated() {
        assertThat(requestHelper).isNotNull();
    }

    @Test
    void buildHeaders_ShouldReturnHeadersWithBearer() {
        var headers = RequestHelper.buildHeaders("test-token");
        assertThat(headers.getFirst("Authorization")).isEqualTo("Bearer test-token");
    }
}
