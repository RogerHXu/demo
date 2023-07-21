package com.example.demo.service;

import com.example.demo.user.User;

import java.util.Optional;

public interface UserService {

    void register(User userDTO);

    boolean checkIfExist(String email);

    Optional<User> getByResetPasswordToken(String token);

    void updatePassword(User user, String newPassword);

    void updateResetPasswordToken(String token, String email);

}
