package com.example.smp.notification.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import com.example.smp.notification.models.dtos.validator.NotificationHeaders;
import com.example.smp.notification.models.dtos.validator.CreateNotificationRequestBody;
import com.example.smp.notification.services.INotificationService;
import com.example.smp.notification.utils.RequestHelper;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class NotificationHandler {

    private final RequestHelper requestHelper;
    private final INotificationService iNotificationService;

    public Mono<ServerResponse> createNotification(ServerRequest request) {
        return requestHelper.of(request)
                .validateHeaders(NotificationHeaders.class)
                .validateBody(CreateNotificationRequestBody.class)
                .flatMap(result -> iNotificationService.createNotification(result.headers(), result.body()));
    }

    public Mono<ServerResponse> getNotificationById(ServerRequest request) {
        String id = request.pathVariable("id");
        return requestHelper.of(request)
                .validateHeaders(NotificationHeaders.class)
                .execute()
                .flatMap(result -> iNotificationService.getNotificationById(result.headers(), id));
    }
}
