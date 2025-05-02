package com.api.crud.apiCrudProject.infrastructure.exception;

public abstract class RessourceNotFoundExceptionBase extends RuntimeException {

    private String messageKey;
    private Object[] messageArgs;

    protected RessourceNotFoundExceptionBase(String messageKey, Object... messageArgs) {
        this.messageKey = messageKey;
        this.messageArgs = messageArgs;
    }

    public String getMessageKey() {
        return this.messageKey;
    }

    public Object[] getMessageArguments() {
        return this.messageArgs;
    }
}
