package com.khamitcevich.model.exception;

public class NotUniqueRoleTypeException extends DBException {
    public NotUniqueRoleTypeException(String message) {
        super(message);
    }

    public NotUniqueRoleTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
