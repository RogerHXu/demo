package com.example.demo.controller;

import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserDetailsImpl;
import com.example.demo.service.UserServiceImpl;
import com.example.demo.user.ERole;
import com.example.demo.user.Role;
import com.example.demo.user.User;
import com.example.demo.web.UserAlreadyExistException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Controller
public class RegisterController {

    @Autowired
    UserServiceImpl userService;

    @GetMapping("/register")
    public String showForm(Model model){
        // create model object to store form data
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String signup(@Valid @ModelAttribute("user") User userDTO, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("user", userDTO);
            return "register";
        }

        if(!Objects.equals(userDTO.getPassword(), userDTO.getPasswordCon())){
            result.rejectValue("password", "userDTO.password", "Passwords don't match");
            model.addAttribute("user", userDTO);
            return "register";
        }

        try{
            userService.register(userDTO);
        }catch (UserAlreadyExistException e){
            result.rejectValue("email", "userDTO.email", "There is already an account registered with this email");
            model.addAttribute("user", userDTO);
            return "register";
        }

        return "redirect:login";
    }
}
