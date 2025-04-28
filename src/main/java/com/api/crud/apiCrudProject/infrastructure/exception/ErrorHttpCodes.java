package com.api.crud.apiCrudProject.infrastructure.exception;

public enum ErrorHttpCodes {
    // Enum avec valeur HTTP, code d'erreur et message associé
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR", "{error.http.internal.server.error}"),
    BAD_REQUEST(400, "BAD_REQUEST", "{error.http.bad.request}"),
    NOT_FOUND(404, "NOT_FOUND", "{error.http.not.found}"),
    UNAUTHORIZED(401, "UNAUTHORIZED", "{error.http.unauthorized}"),
    ACCESS_DENIED(403, "ACCESS_DENIED", "{error.http.access.denied}");

    private final int httpStatusCode;  // Code HTTP (comme 500, 400)
    private final String errorCode;    // Code d'erreur spécifique (par exemple : "INTERNAL_SERVER_ERROR")
    private final String errorMessage; // Message d'erreur détaillé

    // Constructeur pour initialiser les valeurs
    ErrorHttpCodes(int httpStatusCode, String errorCode, String errorMessage) {
        this.httpStatusCode = httpStatusCode;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    // Getter pour récupérer le code HTTP
    public int getStatusCode() {
        return httpStatusCode;
    }

    // Getter pour récupérer le code d'erreur
    public String getCode() {
        return errorCode;
    }

    // Getter pour récupérer le message d'erreur
    public String getMessage() {
        return errorMessage;
    }

}
