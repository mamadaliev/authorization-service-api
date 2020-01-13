package com.github.egnaf.auth.services;

import com.github.egnaf.auth.models.RoleModel;
import com.github.egnaf.auth.models.UserModel;
import com.github.egnaf.auth.repositories.UserRepository;
import com.github.egnaf.auth.utils.RandomIdentifier;
import com.github.egnaf.auth.utils.Status;
import com.github.egnaf.auth.utils.TimestampHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.junit.Assert.assertEquals;

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
                .username("user2")
                .email("user2@reckure.com")
                .password(passwordEncoder.encode("u2_password"))
                .roles(new HashSet<>())
                .created(TimestampHelper.getCurrentTimestamp())
                .updated(TimestampHelper.getCurrentTimestamp())
                .status(Status.ACTIVE)
                .build());
        users.get("user2").getRoles().add(new RoleModel("ROLE_USER"));

        users.put("user7", UserModel.builder()
                .id(RandomIdentifier.generate("user7"))
                .username("user7")
                .email("user7@reckure.com")
                .password(passwordEncoder.encode("u7_password"))
                .roles(new HashSet<>())
                .created(TimestampHelper.getCurrentTimestamp())
                .updated(TimestampHelper.getCurrentTimestamp())
                .status(Status.ACTIVE)
                .build());
        users.get("user7").getRoles().add(new RoleModel("ROLE_USER"));

        userRepository.save(users.get("user1"));
        userRepository.save(users.get("user2"));
        userRepository.save(users.get("user7"));
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
