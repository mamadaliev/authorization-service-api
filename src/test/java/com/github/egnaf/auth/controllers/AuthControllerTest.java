package com.github.egnaf.auth.controllers;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AuthControllerTest {

    @Autowired
    private AuthController authController;

    @Test
    public void register() {
        //TODO: write test for method register
    }

    @Test(dependsOnMethods = {"register"})
    public void login() {
        //TODO: write test for method login
    }

    @Test(dependsOnMethods = {"register", "login"})
    public void whoami() {
        //TODO: write test for method whoami
    }

    @Test(dependsOnMethods = {"register", "login"})
    public void refresh() {
        //TODO: write test for method refresh
    }
}
