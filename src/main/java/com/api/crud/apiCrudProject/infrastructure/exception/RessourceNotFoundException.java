package com.api.crud.apiCrudProject.infrastructure.exception;

import java.text.MessageFormat;

public class RessourceNotFoundException extends RuntimeException implements IRessourceNotFoundException {
    private final Entities entity;
    private final Long idResource;


    public RessourceNotFoundException(Entities entity, Long id) {
        super(MessageFormat.format("{exception.exception.ressource.notfound}", entity.name(), id));
        this.entity = entity;
        this.idResource = id;
    }

    @Override
    public Entities getEntity() {
        return this.entity;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public Long getIdResource() {
        return this.idResource;
    }
}
