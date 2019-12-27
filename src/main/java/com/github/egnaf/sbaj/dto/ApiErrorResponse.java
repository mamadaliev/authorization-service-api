package com.github.egnaf.sbaj.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ApiErrorResponse {

    private Integer status;
    private String path;
    private String errorMessage;
    private String timeStamp;

    public ApiErrorResponse(int status, Map<String, Object> errorAttributes) {
        this.setStatus(status);
        this.setPath((String) errorAttributes.get("path"));
        this.setErrorMessage((String) errorAttributes.get("message"));
        this.setTimeStamp(errorAttributes.get("timestamp").toString());
    }
}
