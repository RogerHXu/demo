package com.example.demo.controller;

import com.example.demo.repository.UserRepository;
import com.example.demo.response.MessageResponse;
import com.example.demo.user.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping(path="/api/user")
public class UserInfoController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private MongoTemplate mt;

    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/usrn")
    public User nameChange(@Valid @RequestParam String oldName, @Valid @RequestParam String newName){
        Query query = new Query().addCriteria(Criteria.where("username").is(oldName));
        Update up = new Update().set("username", newName);
        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(false);
        return mt.findAndModify(query, up, options, User.class);
    }


    @PostMapping("/usrp")
    public ResponseEntity<?> passwordChange(@Valid @RequestParam String id, @Valid @RequestParam String oldPassword, @Valid @RequestParam String newPassword) {
        Criteria criteria = Criteria.where("id").is(id).andOperator(Criteria.where("password").is(oldPassword));
        Query query = new Query().addCriteria(criteria);
        Update update = new Update().set("password", encoder.encode(newPassword));
        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(false).upsert(false);
        if(mt.findAndModify(query, update, options, User.class) == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("The old password is incorrect"));
        }
        return ResponseEntity.ok(new MessageResponse("Password changed successfully!"));
    }

    @PostMapping("/usre")
    public User emailChange(@Valid @RequestBody Map<String, String> json) {
        Query query = new Query().addCriteria(Criteria.where("id").is(json.get("id")));
        Update up = new Update().set("email", json.get("email"));
        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(false);
        return mt.findAndModify(query, up, options, User.class);
    }

    @GetMapping("/all")
    public List<User> findAll(){
        return userRepository.findAll();
    }

    @GetMapping("/password")
    public Optional<User> find(@Valid @RequestParam String id){
        return userRepository.findById(id);
    }

}

