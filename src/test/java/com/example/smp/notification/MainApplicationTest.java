package com.example.smp.notification;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MainApplicationTest {

    @Test
    void mainApplicationClass_shouldExist() {
        assertDoesNotThrow(() -> Class.forName("com.example.smp.notification.MainApplication"));
    }
}
