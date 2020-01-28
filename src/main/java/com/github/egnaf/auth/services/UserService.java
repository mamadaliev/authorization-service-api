package com.github.egnaf.auth.services;

import com.github.egnaf.auth.models.UserModel;

import java.util.List;

public interface UserService {

    List<UserModel> getAll(int limit, int offset, String sort, boolean desc);

    UserModel getById(String id);

    UserModel add(UserModel user);

    void delete(String username);

    UserModel search(String username);
}
