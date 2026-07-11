package com.example.smp.notification.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.example.smp.common.client.config.MessageConfig;
import com.example.smp.config.injections.CustomerProductsSessionIntegrityInjection;
import com.example.smp.exception.webflux.APIExceptionHandlerWebFlux;
import com.example.smp.exception.webflux.GlobalErrorAttributesStructureWebFlux;
import com.example.smp.utils.JwtAuth;

@Slf4j
@AllArgsConstructor
@Import({
        GlobalErrorAttributesStructureWebFlux.class,
        MessageConfig.class,
        APIExceptionHandlerWebFlux.class,
        CustomerProductsSessionIntegrityInjection.class
})
@Configuration
public class BeanConfig {

    @Bean
    public JwtAuth jwtAuth() {
        JwtAuth jwtAuth = new JwtAuth();
        jwtAuth.init();
        return jwtAuth;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
