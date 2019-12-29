package com.github.egnaf.auth_api_service.controllers;

import com.github.egnaf.auth_api_service.transfers.forms.LoginForm;
import com.github.egnaf.auth_api_service.transfers.forms.RegisterForm;
import com.github.egnaf.auth_api_service.transfers.UserTransfer;
import com.github.egnaf.auth_api_service.models.UserModel;
import com.github.egnaf.auth_api_service.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class AuthController {

    private final Mapper mapper;
    private final UserService userService;

    @Autowired
    public AuthController(Mapper mapper, UserService userService) {
        this.mapper = mapper;
        this.userService = userService;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterForm user) {
        return userService.register(new UserModel(user.getUsername(), user.getEmail(), user.getPassword(), null));
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginForm user) {
        return userService.login(user.getUsername(), user.getPassword());
    }

    @GetMapping(value = "/whoami")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public UserTransfer whoami(HttpServletRequest request) {
        return mapper.map(userService.whoami(request), UserTransfer.class);
    }

    @GetMapping(value = "/refresh")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public String refresh(HttpServletRequest request) {
        log.info(request.getRemoteUser());
        return userService.refresh(request.getRemoteUser());
    }
}
