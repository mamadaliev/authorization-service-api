package com.github.egnaf.auth.services;

import com.github.egnaf.auth.transfers.AuthTransfer;
import com.github.egnaf.auth.transfers.forms.LoginForm;
import com.github.egnaf.auth.transfers.forms.RegisterForm;
import com.github.egnaf.auth.models.UserModel;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {

    AuthTransfer register(RegisterForm registerForm);

    AuthTransfer login(LoginForm loginForm);

    UserModel whoami(HttpServletRequest request);

    AuthTransfer refresh(String username, String refreshToken);
}
