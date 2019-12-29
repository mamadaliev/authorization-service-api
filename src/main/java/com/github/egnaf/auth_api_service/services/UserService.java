package com.github.egnaf.auth_api_service.services;

import com.github.egnaf.auth_api_service.transfers.forms.RegisterForm;
import com.github.egnaf.auth_api_service.models.UserModel;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {

    List<UserModel> getAll(int limit, int offset, String sort, boolean desc);

    UserModel getById(long id);

    UserModel add(RegisterForm registerForm);

    void delete(String username);

    UserModel search(String username);

    String register(UserModel userModel);

    String login(String username, String password);

    UserModel whoami(HttpServletRequest request);

    String refresh(String username);
}
