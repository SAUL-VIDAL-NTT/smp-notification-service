package com.example.smp.notification.utils;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseUtilTest {

    @Test
    void successful_NoBody_Returns200() {
        StepVerifier.create(ResponseUtil.successful())
                .expectNextMatches(r -> r.statusCode().value() == 200)
                .verifyComplete();
    }

    @Test
    void successful_WithBody_Returns200() {
        StepVerifier.create(ResponseUtil.successful("test-data"))
                .expectNextMatches(r -> r.statusCode().value() == 200)
                .verifyComplete();
    }

    @Test
    void created_WithBody_Returns201() {
        StepVerifier.create(ResponseUtil.created("test-data"))
                .expectNextMatches(r -> r.statusCode().value() == 201)
                .verifyComplete();
    }

    @Test
    void error_Returns500() {
        StepVerifier.create(ResponseUtil.error())
                .expectNextMatches(r -> r.statusCode().value() == 500)
                .verifyComplete();
    }

    @Test
    void successResponse_HasCorrectStatusCode() {
        var response = ResponseUtil.successResponse();
        assertThat(response.getStatus().getCode()).isEqualTo("0000");
    }

    @Test
    void errorResponse_HasCorrectCode() {
        var response = ResponseUtil.errorResponse("9999", "Error");
        assertThat(response.getStatus().getCode()).isEqualTo("9999");
    }
}
