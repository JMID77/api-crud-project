package com.api.crud.apiCrudProject.infrastructure.exception;

public interface IRessourceNotFoundException {
    Entities getEntity();
    Long getIdResource();
    String getMessage();
}
