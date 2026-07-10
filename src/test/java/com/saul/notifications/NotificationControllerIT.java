package com.saul.notifications;

import com.saul.notifications.dto.CreateNotificationRequest;
import com.saul.notifications.dto.ErrorResponse;
import com.saul.notifications.dto.NotificationResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class NotificationControllerIT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void createNotification_validPayload_returns201() {
        CreateNotificationRequest request = new CreateNotificationRequest();
        request.setTitle("Avance de proyecto");
        request.setMessage("Tu proyecto ha pasado a la siguiente fase.");
        request.setRecipientId("user_456");
        request.setType("PROJECT_UPDATE");

        webTestClient.post()
                .uri("/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(NotificationResponse.class)
                .value(response -> {
                    assertThat(response.getId()).isNotBlank();
                    assertThat(response.getTitle()).isEqualTo("Avance de proyecto");
                    assertThat(response.getMessage()).isEqualTo("Tu proyecto ha pasado a la siguiente fase.");
                    assertThat(response.getRecipientId()).isEqualTo("user_456");
                    assertThat(response.getType()).isEqualTo("PROJECT_UPDATE");
                    assertThat(response.getCreatedAt()).isNotNull();
                });
    }

    @Test
    void createNotification_missingTitle_returns400() {
        CreateNotificationRequest request = new CreateNotificationRequest();
        request.setMessage("Some message");
        request.setRecipientId("user_456");
        request.setType("PROJECT_UPDATE");

        webTestClient.post()
                .uri("/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorResponse.class)
                .value(error -> {
                    assertThat(error.getError()).isEqualTo("ValidationError");
                    assertThat(error.getMessage()).isEqualTo("Invalid request payload");
                    assertThat(error.getDetails()).isNotEmpty();
                    assertThat(error.getDetails().get(0).getField()).isEqualTo("title");
                    assertThat(error.getDetails().get(0).getIssue()).isEqualTo("required");
                });
    }

    @Test
    void createNotification_blankMessage_returns400() {
        CreateNotificationRequest request = new CreateNotificationRequest();
        request.setTitle("Valid title");
        request.setMessage("   ");
        request.setRecipientId("user_456");
        request.setType("PROJECT_UPDATE");

        webTestClient.post()
                .uri("/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorResponse.class)
                .value(error -> assertThat(error.getError()).isEqualTo("ValidationError"));
    }

    @Test
    void createNotification_missingRecipientId_returns400() {
        CreateNotificationRequest request = new CreateNotificationRequest();
        request.setTitle("Valid title");
        request.setMessage("Valid message");
        request.setType("PROJECT_UPDATE");

        webTestClient.post()
                .uri("/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void createNotification_missingType_returns400() {
        CreateNotificationRequest request = new CreateNotificationRequest();
        request.setTitle("Valid title");
        request.setMessage("Valid message");
        request.setRecipientId("user_456");

        webTestClient.post()
                .uri("/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void getNotification_existingId_returns200() {
        // Create first
        CreateNotificationRequest request = new CreateNotificationRequest();
        request.setTitle("Get Test");
        request.setMessage("Test message for GET");
        request.setRecipientId("user_get");
        request.setType("TEST_TYPE");

        NotificationResponse created = webTestClient.post()
                .uri("/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(NotificationResponse.class)
                .returnResult()
                .getResponseBody();

        assertThat(created).isNotNull();

        // Now GET
        webTestClient.get()
                .uri("/notifications/{id}", created.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(NotificationResponse.class)
                .value(response -> {
                    assertThat(response.getId()).isEqualTo(created.getId());
                    assertThat(response.getTitle()).isEqualTo("Get Test");
                });
    }

    @Test
    void getNotification_nonExistingId_returns404() {
        webTestClient.get()
                .uri("/notifications/{id}", "notif_nonexistent_xyz")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorResponse.class)
                .value(error -> {
                    assertThat(error.getError()).isEqualTo("NotFoundError");
                    assertThat(error.getMessage()).isEqualTo("Notification not found");
                });
    }
}
