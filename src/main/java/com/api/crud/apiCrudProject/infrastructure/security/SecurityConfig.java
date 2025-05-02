package com.api.crud.apiCrudProject.infrastructure.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.web.SecurityFilterChain;

import com.api.crud.apiCrudProject.domain.entity.enumeration.RoleType;
import com.api.crud.apiCrudProject.infrastructure.security.service.UserSystemTechnicalService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;



@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UserSystemTechnicalService userSysTechService;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // Autorisation pour les URL accessibles à tout le monde
                .requestMatchers("/api/vLS.1/**", 
                                    "/error").permitAll()       // Accès libre aux routes /api/vLS.1
                // // Autorisation pour les URL accessibles aux utilisateurs ADMIN ou USER
                // .requestMatchers("/api/vES.1/**")
                //                 .hasAnyRole(this.userSysTechService.mapperRoleTypeToString(RoleType.ROLE_USER), 
                //                                 this.userSysTechService.mapperRoleTypeToString(RoleType.ROLE_ADMIN))       // Accessible aux utilisateurs et administrateurs
                // Autorisation pour les URL accessibles uniquement aux administrateurs
                .requestMatchers("/api/vIS.1/internal/admins/**")
                            .hasRole(this.userSysTechService.mapperRoleTypeToString(RoleType.ROLE_ADMIN))          // Accessible uniquement aux administrateurs
                // Autorisation pour les URL accessibles uniquement aux administrateurs
                .requestMatchers("/api/vIS.1/internal/users/**")
                            .hasAnyRole(this.userSysTechService.mapperRoleTypeToString(RoleType.ROLE_ADMIN), 
                                        this.userSysTechService.mapperRoleTypeToString(RoleType.ROLE_USER))          // Accessible uniquement aux administrateurs
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsManager userDetailsService() {
        List<UserDetails> users = this.userSysTechService.retrieveAllUserSystem();

        return new InMemoryUserDetailsManager(users);
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = 
               http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService());
        return authenticationManagerBuilder.build();
    }    
}