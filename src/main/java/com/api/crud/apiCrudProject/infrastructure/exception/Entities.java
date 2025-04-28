package com.api.crud.apiCrudProject.infrastructure.exception;


public enum Entities {
    ACTION("Action"),
    USER("User"),
    USER_SYSTEM("UserSystem");


    private final String entity;

    Entities(String entity) {
        this.entity = entity;
    }

    public String getEnity() {
        return this.entity;
    }
}
