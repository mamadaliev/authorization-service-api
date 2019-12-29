package com.github.egnaf.auth_api_service.transfers.errors;

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
