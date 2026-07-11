package com.example.smp.notification.configurations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.smp.notification.repository.INotificationRepository;
import com.example.smp.notification.repository.impl.NotificationRepositoryImpl;
import com.example.smp.notification.repository.mock.MockNotificationRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BeanInjectionConfigTest {

    @Mock
    private PropertiesConfig propertiesConfig;

    private BeanInjectionConfig beanInjectionConfig;

    @BeforeEach
    void setUp() {
        beanInjectionConfig = new BeanInjectionConfig(propertiesConfig);
    }

    @Test
    void notificationRepository_WhenMockActive_ReturnsMockRepository() {
        when(propertiesConfig.getMockList()).thenReturn(
                java.util.List.of("mock", "mock-notification"));

        INotificationRepository repo = beanInjectionConfig.notificationRepository();

        assertThat(repo).isInstanceOf(MockNotificationRepository.class);
    }

    @Test
    void notificationRepository_WhenMockNotActive_ReturnsRealRepository() {
        when(propertiesConfig.getMockList()).thenReturn(java.util.List.of());

        INotificationRepository repo = beanInjectionConfig.notificationRepository();

        assertThat(repo).isInstanceOf(NotificationRepositoryImpl.class);
    }
}
