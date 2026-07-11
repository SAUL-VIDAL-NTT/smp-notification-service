package com.example.smp.notification.configurations;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PropertiesConfigTest {

    @Test
    void getMockList_NullMocks_ReturnsEmptyList() {
        PropertiesConfig config = new PropertiesConfig();
        assertThat(config.getMockList()).isEmpty();
    }

    @Test
    void getMockList_WithMocks_ReturnsCorrectList() {
        PropertiesConfig config = new PropertiesConfig();
        config.setMocks("mock, mock-notification");
        assertThat(config.getMockList()).containsExactly("mock", "mock-notification");
    }
}
