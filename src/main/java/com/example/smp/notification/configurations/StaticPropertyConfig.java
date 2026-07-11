package com.example.smp.notification.configurations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import com.example.smp.notification.configurations.properties.ErrorMessages;

@Getter
@Component
@DependsOn("coreConfig")
public class StaticPropertyConfig {

    private final boolean isCustomerProductsSessionIntegrityEnabled;

    @Getter @Setter
    private static ErrorMessages errorMessages = new ErrorMessages();

    public StaticPropertyConfig(
            @Value("${static.error-messages}") String errorMessagesJson,
            @Value("${static.is-customer-products-session-integrity-enabled}") String isCustomerProductsSessionIntegrityEnabled,
            ObjectMapper objectMapper
    ) {
        this.isCustomerProductsSessionIntegrityEnabled =
                Boolean.parseBoolean(isCustomerProductsSessionIntegrityEnabled);

        try {
            setErrorMessages(objectMapper.readValue(errorMessagesJson, ErrorMessages.class));
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Error parsing static.error-messages property", e);
        }
    }
}
