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
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @Test(dependsOnMethods = {"add"})
    public void getAll() {
        //TODO: write test for method getAll
    }

    @Test(dependsOnMethods = {"add"})
    public void getById() {
        //TODO: write test for method getById
    }

    @Test
    public void add() {
        //TODO: write test for method add
    }
}
