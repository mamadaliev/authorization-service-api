package com.github.egnaf.auth_api_service.controllers;

import com.github.egnaf.auth_api_service.transfers.forms.RegisterForm;
import com.github.egnaf.auth_api_service.transfers.UserTransfer;
import com.github.egnaf.auth_api_service.exceptions.NotFoundException;
import com.github.egnaf.auth_api_service.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class UserController {

    private final Mapper mapper;
    private final UserService userService;

    @Autowired
    public UserController(Mapper mapper, UserService userService) {
        this.mapper = mapper;
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserTransfer> getAll(@RequestParam(required = false, defaultValue = "10") int limit,
                                     @RequestParam(required = false, defaultValue = "0") int offset,
                                     @RequestParam(required = false, defaultValue = "id") String sort,
                                     @RequestParam(required = false, defaultValue = "false") boolean desc) {
        List<UserTransfer> users = userService.getAll(limit, offset, sort, desc).stream()
                .map(user -> mapper.map(user, UserTransfer.class))
                .collect(Collectors.toList());
        log.debug("Get all users: {}", users);
        return users;
    }

    @GetMapping("/users/{id}")
    public UserTransfer getById(@PathVariable long id) throws NotFoundException, NumberFormatException {
        UserTransfer user =  mapper.map(userService.getById(id), UserTransfer.class);
        log.debug("User by id {}: {}", id, user);
        return user;
    }

    @PostMapping("/users")
    public UserTransfer add(@RequestBody RegisterForm user) {
        return mapper.map(userService.add(user), UserTransfer.class);
    }
}
