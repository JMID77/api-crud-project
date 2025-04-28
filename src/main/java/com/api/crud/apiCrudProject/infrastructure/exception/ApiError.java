package com.api.crud.apiCrudProject.infrastructure.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@RequiredArgsConstructor
public class ApiError {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private String errorCode;  // Code d'erreur métier unique (ex: USER_CANNOT_DELETE)
    private List<String> errors; // Liste des erreurs détaillées (utiles pour plusieurs erreurs)

    public ApiError(int status, String error, String message, String path, String errorCode, List<String> errors) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.errorCode = errorCode;
        this.errors = errors;
    }
}
