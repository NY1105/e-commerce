package com.group14.ecommerce;

import java.util.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class userController {

    @Autowired
    private userRepository user_repository;

    @GetMapping("/user")
    public List<User> getAllUsers() {
        List<User> userList = user_repository.findAll();
        return userList;
    }
}