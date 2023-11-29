package com.group14.ecommerce.Controllers;

import java.util.*;

import com.group14.ecommerce.Service.userService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;


import com.group14.ecommerce.Repository.userRepository;
import com.group14.ecommerce.Vo.User;


@RestController
public class userController {
    @Autowired
    private userRepository user_repository;

    @Autowired
    private userService user_service;

    @GetMapping("/")
    public String index(){
        return("redirect:/swagger-ui/index.html#/");
    }

    @GetMapping("/users")
    public List<User> getAllUsers(@RequestBody String masterKey) {
        if (masterKey.equals("MASTER_KEY")) return user_repository.findAll();
        return Collections.emptyList();
    }

    @PostMapping("/user/login")
    public ResponseEntity<User> getOneUser(@RequestBody User user) {
        Optional<User> authed_user = user_service.auth(user);
        return authed_user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }

    @PostMapping(path = "/user/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public User register(@RequestBody User user) {
        return user_service.register(user);
    }
}