package com.example.smp.notification.models.dtos.validator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CreateNotificationRequestBodyTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validRequest_NoViolations() {
        CreateNotificationRequestBody body = CreateNotificationRequestBody.builder()
                .title("Test Title")
                .message("Test Message")
                .projectId("proj-001")
                .build();
        Set<ConstraintViolation<CreateNotificationRequestBody>> violations = validator.validate(body);
        assertThat(violations).isEmpty();
    }

    @Test
    void blankTitle_HasViolation() {
        CreateNotificationRequestBody body = CreateNotificationRequestBody.builder()
                .title("")
                .message("Test Message")
                .projectId("proj-001")
                .build();
        Set<ConstraintViolation<CreateNotificationRequestBody>> violations = validator.validate(body);
        assertThat(violations).isNotEmpty();
        assertThat(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("title")))
                .isTrue();
    }

    @Test
    void nullMessage_HasViolation() {
        CreateNotificationRequestBody body = CreateNotificationRequestBody.builder()
                .title("Test")
                .message(null)
                .projectId("proj-001")
                .build();
        Set<ConstraintViolation<CreateNotificationRequestBody>> violations = validator.validate(body);
        assertThat(violations).isNotEmpty();
        assertThat(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("message")))
                .isTrue();
    }

    @Test
    void blankProjectId_HasViolation() {
        CreateNotificationRequestBody body = CreateNotificationRequestBody.builder()
                .title("Test")
                .message("Message")
                .projectId("")
                .build();
        Set<ConstraintViolation<CreateNotificationRequestBody>> violations = validator.validate(body);
        assertThat(violations).isNotEmpty();
        assertThat(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("projectId")))
                .isTrue();
    }

    @Test
    void allFieldsBlank_HasMultipleViolations() {
        CreateNotificationRequestBody body = new CreateNotificationRequestBody();
        Set<ConstraintViolation<CreateNotificationRequestBody>> violations = validator.validate(body);
        assertThat(violations).hasSize(3);
    }
}
