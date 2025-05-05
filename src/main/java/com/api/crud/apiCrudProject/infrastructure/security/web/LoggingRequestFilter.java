package com.api.crud.apiCrudProject.infrastructure.security.web;

import java.io.IOException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.api.crud.apiCrudProject.domain.entity.enumeration.Language;
import com.api.crud.apiCrudProject.infrastructure.security.service.UserSystemTechnicalService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class LoggingRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UserSystemTechnicalService userSysTechnicalService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        // Gestion du log
        loggingUserSysConnected(request);


        // Gestion du changement de langue selon utilisateur technique connecté
        String lang = userSysTechnicalService.getCurrentUserLanguage();
        Locale locale = new Locale((lang != null ? lang : Language.ENGLISH.name()));
        request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);

        filterChain.doFilter(request, response);
    }

    private void loggingUserSysConnected(HttpServletRequest request) {
        String method = request.getMethod();
        String path = request.getRequestURI();
        String authHeader = request.getHeader("Authorization");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        String username = (auth != null && auth.isAuthenticated()) ? auth.getName() : "anonymous";

        System.out.printf("🔍 [%s] %s | User: %s | AuthHeader: %s%n", method, path, username, authHeader);
        // Gestion du log (Fin)
    }
}
