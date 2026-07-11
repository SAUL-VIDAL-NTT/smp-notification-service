package com.example.smp.notification.configurations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.smp.utils.JwtAuth;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class BeanConfigTest {

    private final BeanConfig beanConfig = new BeanConfig();

    @Test
    void objectMapper_ShouldNotBeNull() {
        assertThat(beanConfig.objectMapper()).isNotNull();
    }

    @Test
    void jwtAuth_ShouldNotBeNull() {
        JwtAuth jwtAuth = beanConfig.jwtAuth();
        assertThat(jwtAuth).isNotNull();
    }
}
