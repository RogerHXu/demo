package com.example.demo.repository;

import com.example.demo.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findUserByEmail(String email);
    Boolean existsByEmail(String email);

    Optional<User> findByResetPasswordToken(String token);

}
