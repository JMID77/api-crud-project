package com.api.crud.apiCrudProject.infrastructure.exception;

public interface IResourceNotFoundException {
    String getEntityName();
    Long getIdResource();
    String getMessage();
}
