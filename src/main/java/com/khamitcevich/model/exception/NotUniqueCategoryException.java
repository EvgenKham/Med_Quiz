package com.khamitcevich.model.exception;

public class NotUniqueCategoryException extends DBException {
    public NotUniqueCategoryException(String message) {
        super(message);
    }

    public NotUniqueCategoryException(String message, Throwable cause) {
        super(message, cause);
    }

}
