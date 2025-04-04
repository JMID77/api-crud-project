package com.api.crud.apiCrudProject.infrastructure.exception;

public class UserNotFoundException extends RuntimeException {

    // Constructeur prenant un message
    public UserNotFoundException(String message) {
        super(message);
    }

    // Constructeur prenant un message et une cause
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
