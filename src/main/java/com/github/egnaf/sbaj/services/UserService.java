package com.github.egnaf.sbaj.services;

import com.github.egnaf.sbaj.dto.forms.RegisterForm;
import com.github.egnaf.sbaj.models.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {

    List<User> getAll(int limit, int offset, String sort, boolean desc);

    User getById(long id);

    User add(RegisterForm registerForm);

    void delete(String username);

    User search(String username);

    String register(User user);

    String login(String username, String password);

    User whoami(HttpServletRequest request);

    String refresh(String username);
}
