package com.github.egnaf.auth.transfers.errors;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ExceptionResponse {
    private String message;
    private int status;

    public ExceptionResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status.value();
    }
}
