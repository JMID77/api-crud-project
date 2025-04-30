package com.api.crud.apiCrudProject.infrastructure.configuration;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import com.api.crud.apiCrudProject.infrastructure.security.UserSystemTechnicalService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.lang.NonNull;

@Configuration
public class UserLocaleResolver extends AcceptHeaderLocaleResolver {

    @Autowired
    private UserSystemTechnicalService userSysTechnicalService;

    @Override
    public @NonNull Locale resolveLocale(@NonNull HttpServletRequest request) {
        try {
            String userLanguage = this.userSysTechnicalService.getCurrentUserLanguage();
            if (userLanguage != null && !userLanguage.isEmpty()) {
                Locale locale = Locale.forLanguageTag(userLanguage);
                if (locale != null) {
                    return locale;
                }
            }
        } catch (Exception e) {
            // En cas d'exception on ne fait rien et on renvois le LocaleDefault
        }

        return Locale.getDefault();
    }

}
