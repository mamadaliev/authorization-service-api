package com.github.egnaf.auth.repositories;

import com.github.egnaf.auth.models.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserModel, String> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<UserModel> findByUsername(String username);

    void deleteByUsername(String username);
}
