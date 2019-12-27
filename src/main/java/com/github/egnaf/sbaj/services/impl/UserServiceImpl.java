package com.github.egnaf.sbaj.services.impl;

import com.github.egnaf.sbaj.dto.forms.RegisterForm;
import com.github.egnaf.sbaj.exceptions.AlreadyExistsException;
import com.github.egnaf.sbaj.exceptions.AuthenticationException;
import com.github.egnaf.sbaj.exceptions.InvalidDataException;
import com.github.egnaf.sbaj.exceptions.NotFoundException;
import com.github.egnaf.sbaj.models.Role;
import com.github.egnaf.sbaj.models.User;
import com.github.egnaf.sbaj.repositories.UserRepository;
import com.github.egnaf.sbaj.security.JwtTokenProvider;
import com.github.egnaf.sbaj.services.UserService;
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
    public List<User> getAll(int limit, int offset, String sort, boolean desc) {
        Sort sorted = desc ? Sort.by(sort).descending() : Sort.by(sort).ascending();
        return userRepository.findAll(PageRequest.of(offset, limit, sorted)).getContent();
    }

    @Override
    public User getById(long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("User by id " + id + " not found"));
    }

    @Override
    public User add(RegisterForm user) {
        if (userRepository.existsByUsername(user.getUsername()) || userRepository.existsByEmail(user.getEmail())) {
            throw new AlreadyExistsException("Username or Email already exists");
        }
        User savingUser = new User(user.getUsername(),
                user.getEmail(),
                passwordEncoder.encode(user.getPassword()),
                new ArrayList<>());
        savingUser.getRoles().add(new Role("ROLE_USER"));
        return userRepository.save(savingUser);
    }

    public void delete(String username) {
        userRepository.deleteByUsername(username);
    }

    public User search(@NotNull String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("The user doesn't exist", HttpStatus.NOT_FOUND));
    }

    @Override
    public String register(User user) {
        if (!userRepository.existsByUsername(user.getUsername())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(new ArrayList<>());
            user.getRoles().add(new Role("ROLE_USER"));
            userRepository.save(user);
            return jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
        } else {
            throw new AlreadyExistsException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Override
    public String login(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User by username " + username + " not found"));

            return jwtTokenProvider.createToken(username, user.getRoles());

        } catch (AuthenticationException e) {
            throw new InvalidDataException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Override
    public User whoami(HttpServletRequest request) {
        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request));
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User by username " + username + " not found"));
    }

    @Override
    public String refresh(String username) {
        return jwtTokenProvider.createToken(username, userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User by username " + username + " not found"))
                .getRoles());
    }
}
