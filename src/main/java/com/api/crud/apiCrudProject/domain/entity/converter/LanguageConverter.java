package com.api.crud.apiCrudProject.domain.entity.converter;

import com.api.crud.apiCrudProject.domain.entity.Language;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class LanguageConverter implements AttributeConverter<Language, String> {

    @Override
    public String convertToDatabaseColumn(Language lang) {
        return (lang != null ? lang.getCode() : null);
    }

    @Override
    public Language convertToEntityAttribute(String sdData) {
        for (Language lang : Language.values()) {
            if (lang.getCode().equals(sdData)) {
                return lang;
            }
        }

        return null;
    }
}
