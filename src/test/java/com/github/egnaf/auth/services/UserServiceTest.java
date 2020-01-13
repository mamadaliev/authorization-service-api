package com.github.egnaf.auth.services;

import com.github.egnaf.auth.exceptions.NotFoundException;
import com.github.egnaf.auth.models.RoleModel;
import com.github.egnaf.auth.models.UserModel;
import com.github.egnaf.auth.repositories.UserRepository;
import com.github.egnaf.auth.utils.RandomIdentifier;
import com.github.egnaf.auth.utils.TimestampHelper;
import com.github.egnaf.auth.utils.Status;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private Map<String, UserModel> users = new HashMap<>();

    @Before
    public void setUp() throws Exception {
        //expected
        users.put("user1", UserModel.builder()
                .id(RandomIdentifier.generate("user1"))
                .username("user1")
                .email("user1@reckure.com")
                .password(passwordEncoder.encode("u1_password"))
                .roles(new HashSet<>())
                .created(TimestampHelper.getCurrentTimestamp())
                .updated(TimestampHelper.getCurrentTimestamp())
                .status(Status.ACTIVE)
                .build());
        users.get("user1").getRoles().add(new RoleModel("ROLE_USER"));

        users.put("user2", UserModel.builder()
                .id(RandomIdentifier.generate("user2"))
                .username("user1")
                .email("user1@reckure.com")
                .password(passwordEncoder.encode("u2_password"))
                .roles(new HashSet<>())
                .created(TimestampHelper.getCurrentTimestamp())
                .updated(TimestampHelper.getCurrentTimestamp())
                .status(Status.ACTIVE)
                .build());
        users.get("user2").getRoles().add(new RoleModel("ROLE_USER"));

        userRepository.save(users.get("user1"));
        userRepository.save(users.get("user2"));
    }

    @Test
    public void getAll() {
        //expected
        List<UserModel> expectedUsers = new ArrayList<>(users.values());

        //actual
        List<UserModel> actualUsers = userService.getAll(2, 0, "id", false);

        //equals
        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    public void getById() {
        //expected
        UserModel expectedUser = users.get("user1");

        //actual
        UserModel actualUser = userService.getById(RandomIdentifier.generate("user1"));

        //equals
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void add() {
        //init
        String id = RandomIdentifier.generate("user3");

        //expected
        UserModel expectedUser = UserModel.builder()
                .id(id)
                .username("user3")
                .email("user3@reckure.com")
                .password(passwordEncoder.encode("user3_password"))
                .roles(new HashSet<>())
                .created(TimestampHelper.getCurrentTimestamp())
                .updated(TimestampHelper.getCurrentTimestamp())
                .status(Status.ACTIVE)
                .build();
        expectedUser.getRoles().add(new RoleModel("ROLE_USER"));

        //actual
        userService.add(expectedUser);
        UserModel actualUser = userRepository.findById(id).orElse(null);

        //equals
        assert actualUser != null;
        assertEquals(expectedUser.getId(), actualUser.getId());
        assertEquals(expectedUser.getEmail(), actualUser.getEmail());
        assertEquals(expectedUser.getUsername(), actualUser.getUsername());
        assertEquals(expectedUser.getStatus(), actualUser.getStatus());
        assertEquals(expectedUser.getRoles(), actualUser.getRoles());
    }

    @Test
    public void delete() {
        //init
        String id = RandomIdentifier.generate("user4");

        //expected
        UserModel expectedUser = UserModel.builder()
                .id(id)
                .username("user4")
                .email("user4@reckure.com")
                .password(passwordEncoder.encode("user4_password"))
                .roles(new HashSet<>())
                .created(TimestampHelper.getCurrentTimestamp())
                .updated(TimestampHelper.getCurrentTimestamp())
                .status(Status.ACTIVE)
                .build();
        expectedUser.getRoles().add(new RoleModel("ROLE_USER"));

        //actual
        userService.delete("user4"); // void

        try {
            assertNull(userService.getById(id));
        } catch (NotFoundException e) {
            assertEquals("User by id " + id + " not found", e.getMessage());
        }
    }

    @Test
    public void search() {
        //init
        String id = RandomIdentifier.generate("user5");

        //expected
        UserModel expectedUser = UserModel.builder()
                .id(id)
                .username("user5")
                .email("user5@reckure.com")
                .password(passwordEncoder.encode("user5_password"))
                .roles(new HashSet<>())
                .created(TimestampHelper.getCurrentTimestamp())
                .updated(TimestampHelper.getCurrentTimestamp())
                .status(Status.ACTIVE)
                .build();
        expectedUser.getRoles().add(new RoleModel("ROLE_USER"));

        userRepository.save(expectedUser);

        //equals
        Assert.assertEquals(expectedUser, userService.search("user5"));
    }
}
