package com.api.crud.apiCrudProject.domain.entity;

public enum Language {
    FRENCH("fr", "Français", "French"),
    ENGLISH("en", "Anglais", "English");

    private final String code;
    private final String labelFr;
    private final String labelEn;

    Language(String code, String labelFr, String labelEn) {
        this.code = code;
        this.labelFr = labelFr;
        this.labelEn = labelEn;
    }

    public String getCode() {
        return code;
    }

    public String getLabelFr() {
        return labelFr;
    }

    public String getLabelEn() {
        return labelEn;
    }
}
