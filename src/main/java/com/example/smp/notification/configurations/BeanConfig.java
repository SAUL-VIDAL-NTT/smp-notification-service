package com.example.smp.notification.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import com.example.smp.common.client.config.MessageConfig;
import com.example.smp.config.injections.CustomerProductsSessionIntegrityInjection;
import com.example.smp.exception.webflux.APIExceptionHandlerWebFlux;
import com.example.smp.exception.webflux.GlobalErrorAttributesStructureWebFlux;
import com.example.smp.utils.JwtAuth;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;
import java.time.Duration;

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

    private final PropertiesConfig propertiesConfig;

    @Bean
    public ReactorClientHttpConnector connector() throws SSLException {
        var ssl = SslContextBuilder.forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();
        var httpClient = HttpClient.create()
                .secure(sslContextSpec -> sslContextSpec.sslContext(ssl))
                .responseTimeout(Duration.ofSeconds(Long.parseLong(propertiesConfig.getWebclientTimeout())))
                .followRedirect(true);
        return new ReactorClientHttpConnector(httpClient.wiretap(true));
    }

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
