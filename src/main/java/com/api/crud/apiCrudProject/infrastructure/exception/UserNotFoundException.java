package com.api.crud.apiCrudProject.infrastructure.exception;


public class UserNotFoundException extends ResourceNotFoundException {
 
    // Constructeur prenant un message
    public UserNotFoundException(String message, Long userId) {
        super("User", userId, message);
    }

}
