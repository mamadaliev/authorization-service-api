package com.github.egnaf.sbaj.controllers;

import com.github.egnaf.sbaj.dto.forms.RegisterForm;
import com.github.egnaf.sbaj.dto.UserDto;
import com.github.egnaf.sbaj.exceptions.NotFoundException;
import com.github.egnaf.sbaj.services.UserService;
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
    public List<UserDto> getAll(@RequestParam(required = false, defaultValue = "10") int limit,
                                @RequestParam(required = false, defaultValue = "0") int offset,
                                @RequestParam(required = false, defaultValue = "id") String sort,
                                @RequestParam(required = false, defaultValue = "false") boolean desc) {
        List<UserDto> users = userService.getAll(limit, offset, sort, desc).stream()
                .map(user -> mapper.map(user, UserDto.class))
                .collect(Collectors.toList());
        log.debug("Get all users: {}", users);
        return users;
    }

    @GetMapping("/users/{id}")
    public UserDto getById(@PathVariable long id) throws NotFoundException, NumberFormatException {
        UserDto user =  mapper.map(userService.getById(id), UserDto.class);
        log.debug("User by id {}: {}", id, user);
        return user;
    }

    @PostMapping("/users")
    public UserDto add(@RequestBody RegisterForm user) {
        return mapper.map(userService.add(user), UserDto.class);
    }
}
