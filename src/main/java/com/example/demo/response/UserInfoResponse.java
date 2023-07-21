package com.example.demo.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class UserInfoResponse {
    private String id;
    private String username;
    private String email;
    private List<String> roles;
}
