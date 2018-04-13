package com.khamitcevich.model.exception;

public class NotUniqueUserTimesheetNumberException extends DBException {
    public NotUniqueUserTimesheetNumberException(String message) {
        super(message);
    }

    public NotUniqueUserTimesheetNumberException(String message, Throwable cause) {
        super(message, cause);
    }
}
