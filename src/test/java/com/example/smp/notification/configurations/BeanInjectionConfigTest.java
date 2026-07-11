package com.example.smp.notification.configurations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.smp.notification.repository.INotificationRepository;
import com.example.smp.notification.repository.mock.MockNotificationRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class BeanInjectionConfigTest {

    @Test
    void notificationRepository_WhenMockActive_ReturnsMock() {
        PropertiesConfig propertiesConfig = new PropertiesConfig();
        propertiesConfig.setMocks("mock, mock-notification");

        BeanInjectionConfig config = new BeanInjectionConfig(propertiesConfig);
        INotificationRepository repository = config.notificationRepository();

        assertThat(repository).isInstanceOf(MockNotificationRepository.class);
    }

    @Test
    void notificationRepository_WhenMockInactive_ReturnsReal() {
        PropertiesConfig propertiesConfig = new PropertiesConfig();
        propertiesConfig.setMocks("");

        BeanInjectionConfig config = new BeanInjectionConfig(propertiesConfig);
        INotificationRepository repository = config.notificationRepository();

        assertThat(repository).isNotInstanceOf(MockNotificationRepository.class);
    }

    @Test
    void isMock_MatchingMock_ReturnsTrue() {
        PropertiesConfig propertiesConfig = new PropertiesConfig();
        propertiesConfig.setMocks("mock, mock-notification");

        BeanInjectionConfig config = new BeanInjectionConfig(propertiesConfig);
        assertThat(config.isMock("mock-notification")).isTrue();
    }
}
