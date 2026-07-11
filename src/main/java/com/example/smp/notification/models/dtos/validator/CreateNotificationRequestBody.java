package com.example.smp.notification.models.dtos.validator;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateNotificationRequestBody {

    @NotBlank(message = "title must not be blank")
    private String title;

    @NotBlank(message = "message must not be blank")
    private String message;

    @NotBlank(message = "projectId must not be blank")
    private String projectId;
}
