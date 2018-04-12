package com.khamitcevich.exception;

public class NotUniqueUserPasswordException extends DBException {
    public NotUniqueUserPasswordException(String message) {
        super(message);
    }

    public NotUniqueUserPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
