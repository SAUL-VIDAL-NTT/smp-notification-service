package com.example.smp.notification.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import com.example.smp.notification.contants.AppConstants;
import com.example.smp.notification.repository.INotificationRepository;
import com.example.smp.notification.repository.impl.NotificationRepositoryImpl;
import com.example.smp.notification.repository.mock.MockNotificationRepository;

import java.util.Arrays;

@Component
public class BeanInjectionConfig {

    private static final String MOCK = "mock";

    private final PropertiesConfig propertiesConfig;

    @Autowired
    public BeanInjectionConfig(PropertiesConfig propertiesConfig) {
        this.propertiesConfig = propertiesConfig;
    }

    @Bean(name = "notificationRepository")
    public INotificationRepository notificationRepository() {
        if (isMock(MOCK, AppConstants.Mocks.MOCK_NOTIFICATION)) {
            return new MockNotificationRepository();
        }
        return new NotificationRepositoryImpl();
    }

    public boolean isMock(String... mocks) {
        return Arrays.stream(mocks).anyMatch(x -> propertiesConfig.getMockList().contains(x));
    }
}
