package com.group14.ecommerce.Controllers;

import java.util.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;


import com.group14.ecommerce.Repository.cartRepository;
import com.group14.ecommerce.Vo.Cart;


@RestController
public class cartController {

    @Autowired
    private cartRepository cart_repository;

    @GetMapping("/cart")
    public String test(){
        return("this is a test");
    }

    @GetMapping("/carts")
    public List<Cart> getAllCarts() {
        return cart_repository.findAll();
    }
}