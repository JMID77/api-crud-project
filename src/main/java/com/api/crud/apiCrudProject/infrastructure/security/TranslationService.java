package com.api.crud.apiCrudProject.infrastructure.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TranslationService {
    @Autowired
    private MessageSource messageSource;

    public String translate(String key) {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }
}
