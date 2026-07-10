package com.saul.notifications.dto;

import java.time.Instant;

public class NotificationResponse {

    private String id;
    private String title;
    private String message;
    private String recipientId;
    private String type;
    private Instant createdAt;

    public NotificationResponse() {}

    public NotificationResponse(String id, String title, String message,
                                 String recipientId, String type, Instant createdAt) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.recipientId = recipientId;
        this.type = type;
        this.createdAt = createdAt;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getRecipientId() { return recipientId; }
    public void setRecipientId(String recipientId) { this.recipientId = recipientId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
