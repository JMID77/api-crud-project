package com.api.crud.apiCrudProject.infrastructure.exception.users;

import com.api.crud.apiCrudProject.infrastructure.exception.ResourceNotFoundException;

public class UserNotFoundException extends ResourceNotFoundException {
 
    // Constructeur prenant un message
    public UserNotFoundException(String message, Long userId) {
        super("User", userId, message);
    }

}
