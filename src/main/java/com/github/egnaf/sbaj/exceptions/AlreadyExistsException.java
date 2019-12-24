package com.github.egnaf.sbaj.exceptions;

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
