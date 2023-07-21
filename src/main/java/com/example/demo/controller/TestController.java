package com.example.demo.controller;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/test")
public class TestController {
    @GetMapping("/all")
    public String allAccess(){
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String userAccess(){
        return "User Content.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess(){
        return "Admin Interface.";
    }

    @GetMapping("/add_new")
    public String addApplication(){
        return "fragments";
    }

    @GetMapping("/modal")
    public String index(Model model) {
        return "modal";
    }

    @PostMapping("/searching")
    public @ResponseBody String search(@ModelAttribute String values){
        return "hello " + values + "1";
    }

    @PostMapping(path="/greet")
    public String greet(@RequestParam("name") String values, Model model) {
        model.addAttribute("item", values);
        return "fragments::";
    }

}
