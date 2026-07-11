package com.example.smp.notification.configurations;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import com.example.smp.notification.configurations.properties.BaseUri;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "common")
@DependsOn("coreConfig")
public class PropertiesConfig {

    private String profile;
    private BaseUri baseUri;
    private String mocks;
    private String webclientTimeout;

    public List<String> getMockList() {
        return mocks != null ? List.of(mocks.split(",")) : List.of();
    }
}
