package com.api.crud.apiCrudProject.infrastructure.security.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DevSecurityFilterConfig {
    @Bean
    public LoggingRequestFilter loggingRequestFilter() {
        return new LoggingRequestFilter();
    }
}
