package com.api.crud.apiCrudProject.infrastructure.security.web;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class LoggingRequestFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println(">>> LoggingRequestFilter déclenché");

        String method = request.getMethod();
        String path = request.getRequestURI();
        String authHeader = request.getHeader("Authorization");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            System.out.println(">>> Aucun utilisateur authentifié dans le SecurityContext.");
        } else {
            System.out.println(">>> Utilisateur authentifié: " + auth.getName());
        }
        String username = (auth != null && auth.isAuthenticated()) ? auth.getName() : "anonymous";

        System.out.printf("🔍 [%s] %s | User: %s | AuthHeader: %s%n", method, path, username, authHeader);

        filterChain.doFilter(request, response);
    }
}
