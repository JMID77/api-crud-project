package com.api.crud.apiCrudProject.infrastructure.exception;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


@RestControllerAdvice
public class GlobalExceptionHandler {
    // Gérer toutes les autres exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGlobalException(Exception ex, WebRequest request) {
       List<String> errorDetails = List.of("{error.htpp.internal.error}", "{error.htpp.internal.error.retry}");
       ApiError apiError = new ApiError(
                   ErrorHttpCodes.INTERNAL_SERVER_ERROR.getStatusCode(),
                   ErrorHttpCodes.INTERNAL_SERVER_ERROR.getMessage(),
                   ex.getMessage(),
                   request.getDescription(false).replace("uri=", ""),
                   ErrorHttpCodes.INTERNAL_SERVER_ERROR.getCode(),
                   errorDetails
               );

       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                           .body(apiError);
    }


    // Gérer les erreurs de validation (Request dans le Controller)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(MessageFormat.format("{exception.argument.notvalid.generic}", error.getField(), error.getDefaultMessage()));
        }

        ApiError apiError = new ApiError(
                    ErrorHttpCodes.INTERNAL_SERVER_ERROR.getStatusCode(),
                    ErrorHttpCodes.INTERNAL_SERVER_ERROR.getMessage(),
                    ex.getMessage(),
                    request.getDescription(false).replace("uri=", ""),
                    ErrorHttpCodes.INTERNAL_SERVER_ERROR.getCode(),
                    errors
                );
                
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(apiError);
    }


    // Gérer les exceptions d'accessiblité selon le role
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        List<String> errors = List.of(ErrorHttpCodes.ACCESS_DENIED.getMessage()+"\n"+ ex.getMessage());

        ApiError error = new ApiError(
            ErrorHttpCodes.ACCESS_DENIED.getStatusCode(),
            ErrorHttpCodes.ACCESS_DENIED.getMessage(),
            ex.getMessage(),
            request.getDescription(false).replace("uri=", ""),
            ErrorHttpCodes.ACCESS_DENIED.getCode(),
            errors
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(error);
    }
    

    // Gérer les erreurs d'authentification
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAccessDeniedException(AuthenticationException ex, WebRequest request) {
        List<String> errors = List.of(ErrorHttpCodes.UNAUTHORIZED.getMessage(), ex.getMessage());

        ApiError error = new ApiError(
            ErrorHttpCodes.UNAUTHORIZED.getStatusCode(),
            ErrorHttpCodes.UNAUTHORIZED.getMessage(),
            ex.getMessage(),
            request.getDescription(false).replace("uri=", ""),
            ErrorHttpCodes.UNAUTHORIZED.getCode(),
            errors
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(error);
    }

    
    // Gérer l'erreur IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        List<String> errors = List.of(ErrorHttpCodes.BAD_REQUEST.getMessage(), ex.getMessage());

        ApiError error = new ApiError(
            ErrorHttpCodes.BAD_REQUEST.getStatusCode(),
            ErrorHttpCodes.BAD_REQUEST.getMessage(),
            ex.getMessage(),
            request.getDescription(false).replace("uri=", ""),
            ErrorHttpCodes.BAD_REQUEST.getCode(),
            errors
        );

       return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(error);
    }


    // Gérer l'erreur de ressource (record entité : user, action, userSystem) non trouvée
    @ExceptionHandler(RessourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(RessourceNotFoundException ex, WebRequest request) {
        List<String> errors = List.of(ErrorHttpCodes.NOT_FOUND.getMessage(), ex.getMessage());

        ApiError error = new ApiError(
            ErrorHttpCodes.NOT_FOUND.getStatusCode(),
            ErrorHttpCodes.NOT_FOUND.getMessage(),
            ex.getMessage(),
            request.getDescription(false).replace("uri=", ""),
            ErrorHttpCodes.NOT_FOUND.getCode(),
            errors
        );

       return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(error);
    }
}
