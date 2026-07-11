package com.example.smp.notification.configurations;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CoreConfigTest {

    @Test
    void coreConfig_ShouldExtendAppConfigSecret() {
        CoreConfig coreConfig = new CoreConfig();
        assertThat(coreConfig).isNotNull();
        assertThat(coreConfig).isInstanceOf(com.example.smp.config.AppConfigSecret.class);
    }
}
