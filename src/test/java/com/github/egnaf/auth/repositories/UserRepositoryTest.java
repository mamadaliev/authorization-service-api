package com.github.egnaf.auth.repositories;

import com.github.egnaf.auth.models.RoleModel;
import com.github.egnaf.auth.models.UserModel;
import com.github.egnaf.auth.utils.helpers.RandomHelper;
import com.github.egnaf.auth.utils.helpers.TimestampHelper;
import com.github.egnaf.auth.utils.enums.Status;
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

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    private Map<String, UserModel> users = new HashMap<>();

    @Before
    public void setUp() throws Exception {
        //expected
        users.put("user1", UserModel.builder()
                .id(RandomHelper.generate("user1"))
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
                .id(RandomHelper.generate("user2"))
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
    public void existsByUsername() {
        assertTrue(userRepository.existsByUsername("user1"));
    }

    @Test
    public void existsByEmail() {
        assertTrue(userRepository.existsByEmail("user1@reckure.com"));
    }

    @Test
    public void findByUsername() {
        //data
        String id = RandomHelper.generate("user3");

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
        assertNotNull(userRepository.findByUsername("user3"));
    }

    @Test
    public void deleteByUsername() {
        //data
        String id = RandomHelper.generate("user3");

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

        userRepository.deleteByUsername("user3");

        assertNull(userRepository.findById(id).orElse(null));
    }
}
