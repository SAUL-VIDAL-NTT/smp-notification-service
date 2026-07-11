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

    private final String smpPublicKeyCrypto;
    private final String smpPrivateKeyCrypto;
    private final String smpPrivateKeyIvCrypto;
    private final String smpPrivateKeyPasswordCrypto;
    private final boolean isCustomerProductsSessionIntegrityEnabled;

    @Getter @Setter
    private static ErrorMessages errorMessages = new ErrorMessages();

    public StaticPropertyConfig(
            @Value("${static.smp-public-key-crypto}") String smpPublicKeyCrypto,
            @Value("${static.smp-private-key-crypto}") String smpPrivateKeyCrypto,
            @Value("${static.smp-private-key-iv-crypto}") String smpPrivateKeyIvCrypto,
            @Value("${static.smp-private-key-password-crypto}") String smpPrivateKeyPasswordCrypto,
            @Value("${static.error-messages}") String errorMessagesJson,
            @Value("${static.is-customer-products-session-integrity-enabled}") String isCustomerProductsSessionIntegrityEnabled,
            ObjectMapper objectMapper
    ) {
        this.smpPublicKeyCrypto = smpPublicKeyCrypto;
        this.smpPrivateKeyCrypto = smpPrivateKeyCrypto;
        this.smpPrivateKeyIvCrypto = smpPrivateKeyIvCrypto;
        this.smpPrivateKeyPasswordCrypto = smpPrivateKeyPasswordCrypto;
        this.isCustomerProductsSessionIntegrityEnabled =
                Boolean.parseBoolean(isCustomerProductsSessionIntegrityEnabled);

        try {
            setErrorMessages(objectMapper.readValue(errorMessagesJson, ErrorMessages.class));
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Error parsing static.error-messages property", e);
        }
    }
}
