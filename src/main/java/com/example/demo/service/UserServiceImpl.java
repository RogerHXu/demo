package com.example.demo.service;

import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.response.MessageResponse;
import com.example.demo.user.ERole;
import com.example.demo.user.Role;
import com.example.demo.user.User;
import com.example.demo.web.UserAlreadyExistException;
import com.example.demo.web.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void register(User userDTO) throws UserAlreadyExistException {
        if(checkIfExist(userDTO.getEmail())) throw new UserAlreadyExistException("An account with this email already exists");
        User user = new User(userDTO.getEmail(),
                userDTO.getEmail(),
                encoder.encode(userDTO.getPassword()));
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public boolean checkIfExist(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }

    @Override
    public Optional<User> getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    @Override
    public void updatePassword(User user, String newPassword) {
        user.setPassword(encoder.encode(newPassword));
        user.setResetPasswordToken(null);
        userRepository.save(user);
    }

    @Override
    public void updateResetPasswordToken(String token, String email) {
        Optional<User> user = userRepository.findUserByEmail(email);
        if(user.isPresent()){
            user.get().setResetPasswordToken(token);
            userRepository.save(user.get());
        }
        else{
            throw new UserNotFoundException("That address is either invalid, not a verified primary email or is not associated with a personal user account.");
        }
    }


}
