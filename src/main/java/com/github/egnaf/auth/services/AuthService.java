package com.github.egnaf.auth.services;

import com.github.egnaf.auth.transfers.TokensTransfer;
import com.github.egnaf.auth.transfers.forms.LoginForm;
import com.github.egnaf.auth.transfers.forms.RegisterForm;
import com.github.egnaf.auth.models.UserModel;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {

    TokensTransfer register(RegisterForm registerForm);

    TokensTransfer login(LoginForm loginForm);

    UserModel whoami(HttpServletRequest request);

    TokensTransfer refresh(String username, String refreshToken);
}
