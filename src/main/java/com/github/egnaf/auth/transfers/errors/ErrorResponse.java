package com.github.egnaf.auth.transfers.errors;

import lombok.Data;

import java.util.Map;

@Data
public class ErrorResponse {
    private String message;
    private Integer status;

    public ErrorResponse(int status, Map<String, Object> errorAttributes) {
        this.setStatus(status);
        this.setMessage((String) errorAttributes.get("message"));
    }
}
