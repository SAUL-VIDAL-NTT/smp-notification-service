package com.saul.notifications.dto;

import java.util.List;

public class ErrorResponse {

    private String error;
    private String message;
    private List<FieldError> details;

    public ErrorResponse(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public ErrorResponse(String error, String message, List<FieldError> details) {
        this.error = error;
        this.message = message;
        this.details = details;
    }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public List<FieldError> getDetails() { return details; }
    public void setDetails(List<FieldError> details) { this.details = details; }

    public static class FieldError {
        private String field;
        private String issue;

        public FieldError(String field, String issue) {
            this.field = field;
            this.issue = issue;
        }

        public String getField() { return field; }
        public void setField(String field) { this.field = field; }

        public String getIssue() { return issue; }
        public void setIssue(String issue) { this.issue = issue; }
    }
}
