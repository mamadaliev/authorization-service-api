package com.github.egnaf.sbaj.dto;

import lombok.Data;

@Data
public class ErrorHandlerResponse {
    private int status;
    private String message;

    public ErrorHandlerResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
