package com.company.app.rest;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {

    @GetMapping("/")
    public String defaultPage(Authentication auth) {
        return auth != null ? "Authenticated as " + auth.getName() : "You are not authenticated. Please login.";
    }

    @GetMapping("/secured")
    public Authentication securedPage(Authentication auth) {
        return auth;
    }
}
