package com.example.smp.notification.configurations;

import jakarta.annotation.PostConstruct;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import com.example.smp.config.AppConfigSecret;
import com.example.smp.exception.AppException;

@Slf4j
@Generated
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Import(AppConfigSecret.class)
public class CoreConfig extends AppConfigSecret {

    @PostConstruct
    @Override
    public void init() throws AppException {
        super.init();
    }
}
