package com.saul.notifications.controller;

import com.saul.notifications.dto.CreateNotificationRequest;
import com.saul.notifications.dto.NotificationResponse;
import com.saul.notifications.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<NotificationResponse> create(@Valid @RequestBody CreateNotificationRequest request) {
        return service.create(request);
    }

    @GetMapping("/{id}")
    public Mono<NotificationResponse> getById(@PathVariable String id) {
        return service.findById(id);
    }
}
