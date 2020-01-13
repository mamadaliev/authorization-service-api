package com.github.egnaf.auth.controllers;

import com.github.egnaf.auth.exceptions.NotFoundException;
import com.github.egnaf.auth.services.UserService;
import com.github.egnaf.auth.transfers.UserTransfer;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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

    //TODO: create a form for parameters as a request body
    @GetMapping("/users")
    @ResponseStatus(code = HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public List<UserTransfer> getAll(@RequestParam(required = false, defaultValue = "10") int limit,
                                     @RequestParam(required = false, defaultValue = "0") int offset,
                                     @RequestParam(required = false, defaultValue = "id") String sort,
                                     @RequestParam(required = false, defaultValue = "false") boolean desc) {
        List<UserTransfer> users = userService.getAll(limit, offset, sort, desc).stream()
                .map(userModel -> mapper.map(userModel, UserTransfer.class))
                .collect(Collectors.toList());
        log.debug("Retrieved all users by limit={}, offset={}, sort={}, desc={}", limit, offset, sort, desc);
        return users;
    }

    @GetMapping("/users/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public UserTransfer getById(@PathVariable String id) throws NotFoundException, NumberFormatException {
        UserTransfer user =  mapper.map(userService.getById(id), UserTransfer.class);
        log.debug("Retrieved user by identifier: {}", user);
        return user;
    }

    //TODO: add method deleteById with PreAuthorize
}
