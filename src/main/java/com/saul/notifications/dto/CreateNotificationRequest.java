package com.saul.notifications.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateNotificationRequest {

    @NotBlank(message = "title is required")
    private String title;

    @NotBlank(message = "message is required")
    private String message;

    @NotBlank(message = "recipientId is required")
    private String recipientId;

    @NotBlank(message = "type is required")
    private String type;

    public CreateNotificationRequest() {}

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getRecipientId() { return recipientId; }
    public void setRecipientId(String recipientId) { this.recipientId = recipientId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
