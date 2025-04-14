package com.api.crud.apiCrudProject.infrastructure.exception.actions;

import com.api.crud.apiCrudProject.infrastructure.exception.ResourceNotFoundException;

public class ActionNotFoundException extends ResourceNotFoundException {

    // Constructeur prenant un message
    public ActionNotFoundException(String message, Long actionId) {
        super("Action", actionId, message);
    }
}
