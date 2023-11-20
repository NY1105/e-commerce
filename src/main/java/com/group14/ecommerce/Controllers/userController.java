package com.group14.ecommerce.Controllers;

import java.util.*;

import com.group14.ecommerce.Repository.userRepository;
import com.group14.ecommerce.Vo.User;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;



@RestController
public class userController {

    @Autowired
    private userRepository user_repository;

    @GetMapping("/test")
    public String test(){
        return("this is a test");
    }

    @GetMapping("/user")
    public List<User> getAllUsers() {
        return user_repository.findAll();
    }
}