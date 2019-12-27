package com.github.egnaf.sbaj.controllers;

import com.github.egnaf.sbaj.dto.forms.RegisterForm;
import com.github.egnaf.sbaj.dto.UserDto;
import com.github.egnaf.sbaj.models.User;
import com.github.egnaf.sbaj.services.UserService;
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

    @PostMapping("/auth/register")
    public String register(@RequestBody RegisterForm user) {
        return userService.register(new User(user.getUsername(), user.getEmail(), user.getPassword(), null));
    }

    @PostMapping("/auth/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        return userService.login(username, password);
    }

    @GetMapping(value = "/auth/whoami")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public UserDto whoami(HttpServletRequest request) {
        return mapper.map(userService.whoami(request), UserDto.class);
    }

    @GetMapping(value = "/auth/refresh")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public String refresh(HttpServletRequest request) {
        log.info(request.getRemoteUser());
        return userService.refresh(request.getRemoteUser());
    }
}
