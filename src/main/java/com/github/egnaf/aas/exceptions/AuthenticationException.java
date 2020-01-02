package com.github.egnaf.aas.exceptions;

import org.springframework.http.HttpStatus;

public class AuthenticationException extends RuntimeException {

    private final String message;
    private final HttpStatus httpStatus;

    public AuthenticationException(String message, HttpStatus httpStatus) {
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
