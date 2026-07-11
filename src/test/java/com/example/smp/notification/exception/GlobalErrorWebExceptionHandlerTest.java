package com.example.smp.notification.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalErrorWebExceptionHandlerTest {

    @Test
    void handler_ShouldBeCreated() {
        GlobalErrorWebExceptionHandler handler = new GlobalErrorWebExceptionHandler();
        assertThat(handler).isNotNull();
    }
}
