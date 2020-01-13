package com.github.egnaf.auth.services.impl;

import com.github.egnaf.auth.exceptions.AlreadyExistsException;
import com.github.egnaf.auth.exceptions.NotFoundException;
import com.github.egnaf.auth.repositories.UserRepository;
import com.github.egnaf.auth.services.UserService;
import com.github.egnaf.auth.models.RoleModel;
import com.github.egnaf.auth.models.UserModel;
import com.github.egnaf.auth.utils.RandomIdentifier;
import com.github.egnaf.auth.utils.Status;
import com.github.egnaf.auth.utils.TimestampHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserModel> getAll(int limit, int offset, String sort, boolean desc) {
        Sort sorted = desc ? Sort.by(sort).descending() : Sort.by(sort).ascending();
        return userRepository.findAll(PageRequest.of(offset, limit, sorted)).getContent();
    }

    @Override
    public UserModel getById(String id) {
        return userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("The user by id [" + id + "] not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public UserModel add(UserModel userModel) {
        if (!userRepository.existsByUsername(userModel.getUsername())
                && !userRepository.existsByEmail(userModel.getEmail())) {
            UserModel user = UserModel.builder()
                    .id(RandomIdentifier.generate(userModel.getUsername()))
                    .username(userModel.getUsername())
                    .email(userModel.getEmail())
                    .password(passwordEncoder.encode(userModel.getPassword()))
                    .roles(new HashSet<>())
                    .status(Status.ACTIVE)
                    .created(TimestampHelper.getCurrentTimestamp())
                    .updated(TimestampHelper.getCurrentTimestamp())
                    .build();
            user.getRoles().add(new RoleModel("ROLE_USER"));
            return userRepository.save(user);
        } else {
            throw new AlreadyExistsException("Username or Email already exists", HttpStatus.NOT_MODIFIED);
        }
    }

    @Override
    public void delete(String username) {
        if (!userRepository.existsByUsername(username)) {
            userRepository.deleteByUsername(username);
        } else {
            throw new NotFoundException("The user by username [" + username + "] not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public UserModel search(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("The user by username [" + username + "] not found",
                        HttpStatus.NOT_FOUND));
    }
}
