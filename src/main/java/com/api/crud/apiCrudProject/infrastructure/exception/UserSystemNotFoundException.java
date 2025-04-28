package com.api.crud.apiCrudProject.infrastructure.exception;


public class UserSystemNotFoundException extends ResourceNotFoundException {
    // Constructeur prenant un message
    public UserSystemNotFoundException(String message, Long userSysId) {
        super("UserSystem", userSysId, message);
    }
}
