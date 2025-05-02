package com.api.crud.apiCrudProject.infrastructure.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TranslationService {
    @Autowired
    private MessageSource messageSource;

    public String translate(String key) {
        try {
            return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException ex) {
            return "Translation not found for: " + key; // Ou une valeur par défaut    
        }
    }

    public String translate(String key, Object... args) {
        try {
            return messageSource.getMessage(key, args, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException ex) {
            return "Translation not found for: " + key; // Ou une valeur par défaut    
        }
    }
}
