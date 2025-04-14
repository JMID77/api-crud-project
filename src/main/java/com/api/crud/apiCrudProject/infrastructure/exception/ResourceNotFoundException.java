package com.api.crud.apiCrudProject.infrastructure.exception;



public class ResourceNotFoundException extends RuntimeException implements IResourceNotFoundException {
    private final String entityName;
    private final Long idResource;

    public ResourceNotFoundException(String entityName, Long id, String message) {
        super(message);
        this.entityName = entityName;
        this.idResource = id;
    }

    @Override
    public String getEntityName() {
        return this.entityName;
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
