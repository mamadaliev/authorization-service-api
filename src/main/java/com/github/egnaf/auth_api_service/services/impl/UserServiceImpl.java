package com.github.egnaf.auth_api_service.services.impl;

import com.github.egnaf.auth_api_service.transfers.forms.RegisterForm;
import com.github.egnaf.auth_api_service.exceptions.AlreadyExistsException;
import com.github.egnaf.auth_api_service.exceptions.AuthenticationException;
import com.github.egnaf.auth_api_service.exceptions.InvalidDataException;
import com.github.egnaf.auth_api_service.exceptions.NotFoundException;
import com.github.egnaf.auth_api_service.models.RoleModel;
import com.github.egnaf.auth_api_service.models.UserModel;
import com.github.egnaf.auth_api_service.repositories.UserRepository;
import com.github.egnaf.auth_api_service.security.JwtTokenProvider;
import com.github.egnaf.auth_api_service.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserServiceImpl(JwtTokenProvider jwtTokenProvider, UserRepository userRepository,
                           PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public List<UserModel> getAll(int limit, int offset, String sort, boolean desc) {
        Sort sorted = desc ? Sort.by(sort).descending() : Sort.by(sort).ascending();
        return userRepository.findAll(PageRequest.of(offset, limit, sorted)).getContent();
    }

    @Override
    public UserModel getById(long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("User by id " + id + " not found"));
    }

    @Override
    public UserModel add(RegisterForm user) {
        if (userRepository.existsByUsername(user.getUsername()) || userRepository.existsByEmail(user.getEmail())) {
            throw new AlreadyExistsException("Username or Email already exists");
        }
        UserModel savingUserModel = new UserModel(user.getUsername(),
                user.getEmail(),
                passwordEncoder.encode(user.getPassword()),
                new ArrayList<>());
        savingUserModel.getRoleModels().add(new RoleModel("ROLE_USER"));
        return userRepository.save(savingUserModel);
    }

    public void delete(String username) {
        userRepository.deleteByUsername(username);
    }

    public UserModel search(@NotNull String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("The user doesn't exist", HttpStatus.NOT_FOUND));
    }

    @Override
    public String register(UserModel userModel) {
        if (!userRepository.existsByUsername(userModel.getUsername())) {
            userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
            userModel.setRoleModels(new ArrayList<>());
            userModel.getRoleModels().add(new RoleModel("ROLE_USER"));
            userRepository.save(userModel);
            return jwtTokenProvider.createToken(userModel.getUsername(), userModel.getRoleModels());
        } else {
            throw new AlreadyExistsException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Override
    public String login(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            UserModel userModel = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User by username " + username + " not found"));

            return jwtTokenProvider.createToken(username, userModel.getRoleModels());

        } catch (AuthenticationException e) {
            throw new InvalidDataException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Override
    public UserModel whoami(HttpServletRequest request) {
        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request));
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User by username " + username + " not found"));
    }

    @Override
    public String refresh(String username) {
        return jwtTokenProvider.createToken(username, userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User by username " + username + " not found"))
                .getRoleModels());
    }
}
