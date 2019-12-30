package com.github.egnaf.auth_api_service.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidDataException extends RuntimeException {

    private final String message;
    private final HttpStatus httpStatus;

    public InvalidDataException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
