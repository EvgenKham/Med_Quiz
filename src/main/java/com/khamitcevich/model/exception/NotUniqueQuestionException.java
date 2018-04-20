package com.khamitcevich.model.exception;

public class NotUniqueQuestionException extends DBException {
    public NotUniqueQuestionException(String message) {
        super(message);
    }

    public NotUniqueQuestionException(String message, Throwable cause) {
        super(message, cause);
    }
}
