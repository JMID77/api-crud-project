package com.api.crud.apiCrudProject.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                // CSRF activé pour les API REST (les cookies ne sont pas utilisés ici, donc pas besoin de protection complète)
                .csrf(csrf -> csrf
                    .ignoringRequestMatchers("/users/**") // Ignorer CSRF pour les routes où tu n'as pas besoin de protection
                )
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/users/**", "/hello", "/error").permitAll() // Autorise les requêtes pour "/users/**"
                    .anyRequest().authenticated() // Authentifie toute autre requête
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}