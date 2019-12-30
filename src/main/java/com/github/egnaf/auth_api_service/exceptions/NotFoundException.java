package com.github.egnaf.auth_api_service.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends RuntimeException {

    private HttpStatus status;

    public NotFoundException() {
        super("Object not found");
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
