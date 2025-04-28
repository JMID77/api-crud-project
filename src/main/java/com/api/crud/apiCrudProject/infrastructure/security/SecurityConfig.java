package com.api.crud.apiCrudProject.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.web.SecurityFilterChain;

import com.api.crud.apiCrudProject.domain.entity.RoleType;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // CSRF désactivé pour les API REST (les cookies ne sont pas utilisés ici, donc pas besoin de protection complète)
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                    // Autorisation pour les URL accessibles à tout le monde
                    .requestMatchers("/api/vLS.1/**", 
                                        "/error").permitAll()       // Accès libre aux routes /api/vLS.1
                    // Autorisation pour les URL accessibles aux utilisateurs ADMIN ou USER
                    .requestMatchers("/api/vES.1/**")
                                    .hasAnyRole(RoleType.ROLE_USER.name(), RoleType.ROLE_ADMIN.name())  // Accessible aux utilisateurs et administrateurs
                    // Autorisation pour les URL accessibles uniquement aux administrateurs
                    .requestMatchers("/api/vIS.1/**")
                                    .hasRole(RoleType.ROLE_ADMIN.name())   // Accessible uniquement aux administrateurs
                    // Par défaut, toutes les autres routes nécessitent une authentification
                    .anyRequest().authenticated()
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}