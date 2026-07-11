package com.example.smp.notification.models.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {
    private String id;
    private String title;
    private String message;
    private String projectId;
    private String severity;
    private String createdAt;
    private String requestId;
}
