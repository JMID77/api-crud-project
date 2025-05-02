package com.api.crud.apiCrudProject.infrastructure.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.api.crud.apiCrudProject.infrastructure.security.service.TranslationService;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    TranslationService translationService;

    
    // Gérer toutes les autres exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGlobalException(Exception ex, WebRequest request) {
       List<String> errorDetails = new ArrayList<>();

       errorDetails.add(translationService.translate("{error.htpp.internal.error}"));
       errorDetails.add(translationService.translate("{error.htpp.internal.error.retry}"));
       
       ApiError apiError = new ApiError(
                   ErrorHttpCodes.INTERNAL_SERVER_ERROR.getStatusCode(),
                   ErrorHttpCodes.INTERNAL_SERVER_ERROR.resolveMessage(translationService),
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
            errors.add(translationService.translate("{exception.argument.notvalid.generic}", error.getField(), error.getDefaultMessage()));
        }

        ApiError apiError = new ApiError(
                    ErrorHttpCodes.INTERNAL_SERVER_ERROR.getStatusCode(),
                    ErrorHttpCodes.INTERNAL_SERVER_ERROR.resolveMessage(translationService),
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
        List<String> errors = List.of(ErrorHttpCodes.ACCESS_DENIED.resolveMessage(translationService)+"\n"+ ex.getMessage());

        ApiError error = new ApiError(
            ErrorHttpCodes.ACCESS_DENIED.getStatusCode(),
            ErrorHttpCodes.ACCESS_DENIED.resolveMessage(translationService),
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
        List<String> errors = List.of(ErrorHttpCodes.UNAUTHORIZED.resolveMessage(translationService), ex.getMessage());

        ApiError error = new ApiError(
            ErrorHttpCodes.UNAUTHORIZED.getStatusCode(),
            ErrorHttpCodes.UNAUTHORIZED.resolveMessage(translationService),
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
        List<String> errors = List.of(ErrorHttpCodes.BAD_REQUEST.resolveMessage(translationService), ex.getMessage());

        ApiError error = new ApiError(
            ErrorHttpCodes.BAD_REQUEST.getStatusCode(),
            ErrorHttpCodes.BAD_REQUEST.resolveMessage(translationService),
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
        List<String> errors = List.of(ErrorHttpCodes.NOT_FOUND.resolveMessage(translationService), ex.getMessage());
        String localizedMessage = translationService.translate(
                                                        ex.getMessageKey(),
                                                        ex.getMessageArguments()
                                                    );

        ApiError error = new ApiError(
            ErrorHttpCodes.NOT_FOUND.getStatusCode(),
            ErrorHttpCodes.NOT_FOUND.resolveMessage(translationService),
            localizedMessage,
            // ex.getMessage(), // Pas utiliser message de l'exception ici, car c'est une clé de traduction => autre méchanisme (solution pour l'instant mise en place à revoir selon expérience)
            request.getDescription(false).replace("uri=", ""),
            ErrorHttpCodes.NOT_FOUND.getCode(),
            errors
        );

       return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(error);
    }
}
