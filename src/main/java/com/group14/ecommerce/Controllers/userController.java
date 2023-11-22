package com.group14.ecommerce.Controllers;

import java.util.*;

import com.group14.ecommerce.Vo.Product;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;


import com.group14.ecommerce.Repository.userRepository;
import com.group14.ecommerce.Vo.User;


@RestController
public class userController {
    @Autowired
    private userRepository user_repository;

    @GetMapping("/")
    public String test(){
        return("this is a test");
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return user_repository.findAll();
    }

    @GetMapping("/users")
    public User getOneProduct(@RequestParam Long userId) {
        return user_repository.getReferenceById(userId);
    }

    
}