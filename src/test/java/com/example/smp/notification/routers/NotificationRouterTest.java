package com.example.smp.notification.routers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import com.example.smp.notification.handlers.NotificationHandler;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class NotificationRouterTest {

    @Mock
    private NotificationHandler notificationHandler;

    @InjectMocks
    private NotificationRouter notificationRouter;

    @Test
    void notificationRoutes_ShouldNotBeNull() {
        RouterFunction<ServerResponse> routes = notificationRouter.notificationRoutes();
        assertThat(routes).isNotNull();
    }
}
