package com.api.crud.apiCrudProject.infrastructure.security.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevSecurityFilterConfig {
    @Bean
    public LoggingRequestFilter loggingRequestFilter() {
        return new LoggingRequestFilter();
    }
}
