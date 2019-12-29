package com.github.egnaf.auth_api_service.transfers.forms;

import lombok.Data;

@Data
public class RegisterForm {

    private String username;
    private String email;
    private String password;
}
