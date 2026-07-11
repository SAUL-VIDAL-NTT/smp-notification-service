package com.example.smp.notification.routers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import com.example.smp.notification.contants.AppConstants;
import com.example.smp.notification.handlers.NotificationHandler;
import com.example.smp.utils.HeaderFilterReactiveUtil;
import reactor.core.publisher.Mono;

import java.util.HashSet;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;

@Configuration
@RequiredArgsConstructor
public class NotificationRouter {

    private final NotificationHandler notificationHandler;

    @Bean
    public RouterFunction<ServerResponse> notificationRoutes() {
        return RouterFunctions.nest(
                path("/notifications"),
                RouterFunctions.route()
                        .POST("", this::createNotification)
                        .GET("/{id}", this::getNotificationById)
                        .build()
        );
    }

    private Mono<ServerResponse> createNotification(ServerRequest request) {
        var allowedHeaders = filterBasic();
        ServerRequest filteredRequest = HeaderFilterReactiveUtil.filterHeaders(request, allowedHeaders);
        return notificationHandler.createNotification(filteredRequest);
    }

    private Mono<ServerResponse> getNotificationById(ServerRequest request) {
        var allowedHeaders = filterBasic();
        ServerRequest filteredRequest = HeaderFilterReactiveUtil.filterHeaders(request, allowedHeaders);
        return notificationHandler.getNotificationById(filteredRequest);
    }

    private HashSet<String> filterBasic() {
        var allowedHeaders = new HashSet<String>();
        allowedHeaders.add(AppConstants.Headers.CONSUMER_ID);
        allowedHeaders.add(AppConstants.Headers.CUSTOMER_ID);
        allowedHeaders.add(AppConstants.Headers.SESSION_ID);
        allowedHeaders.add(AppConstants.Headers.SESSION_APP_ID);
        allowedHeaders.add(AppConstants.Headers.TRACE_PARENT);
        allowedHeaders.add(AppConstants.Headers.DEVICE_TYPE);
        allowedHeaders.add(AppConstants.Headers.DEVICE_ID);
        allowedHeaders.add(AppConstants.Headers.AUTHORIZATION);
        allowedHeaders.add(AppConstants.Headers.API_VERSION);
        allowedHeaders.add(AppConstants.Headers.SUBSCRIPTION_KEY);
        allowedHeaders.add(AppConstants.Headers.MONITOR_DATA);
        allowedHeaders.add(AppConstants.Headers.X_REQUEST_ID);
        return allowedHeaders;
    }
}
