package com.company.app.service;

import com.company.app.domain.User;
import com.company.app.domain.form.UserCreateForm;

import java.util.Collection;
import java.util.Optional;

public interface UserService {

    Optional<User> getUserById(long id);

    Optional<User> getUserByEmail(String email);

    Collection<User> getAllUsers();

    User createForm(UserCreateForm form);
}
