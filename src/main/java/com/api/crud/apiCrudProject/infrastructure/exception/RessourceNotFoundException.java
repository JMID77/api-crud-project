package com.api.crud.apiCrudProject.infrastructure.exception;

public class RessourceNotFoundException extends RessourceNotFoundExceptionBase {
    private final Entities entity;
    private final Long idResource;

    
    public RessourceNotFoundException(Entities entity, Long id) {
        super("{exception.exception.ressource.notfound}", entity.name(), id);
        this.entity = entity;
        this.idResource = id;
    }

    public Entities getEntity() {
        return this.entity;
    }


    public Long getIdResource() {
        return this.idResource;
    }
}
