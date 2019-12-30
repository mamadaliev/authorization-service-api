package com.github.egnaf.auth_api_service.exceptions;

import org.springframework.http.HttpStatus;

public class AlreadyExistsException extends RuntimeException {

    private HttpStatus status;

    public AlreadyExistsException() {
        super("Object already exists");
    }

    public AlreadyExistsException(String message) {
        super(message);
    }

    public AlreadyExistsException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
