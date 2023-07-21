package com.example.demo.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@Getter
@Setter
public class RegistrationRequest {
    @NotBlank
    @Email
    private  String email;

    private Set<String> roles;
    @NotBlank
    private  String password;
}
