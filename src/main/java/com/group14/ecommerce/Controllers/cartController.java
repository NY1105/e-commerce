package com.group14.ecommerce.Controllers;

import java.util.*;

import com.group14.ecommerce.Vo.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;


import com.group14.ecommerce.Repository.cartRepository;
import com.group14.ecommerce.Vo.Cart;
import com.group14.ecommerce.Service.cartService;


@RestController
public class cartController {

    @Autowired
    private cartRepository cart_repository;

    @Autowired
    private cartService cart_service;

    @GetMapping("/carts")
    public List<Cart> getAllCarts() {
        return cart_repository.findAll();
    }

    @GetMapping("/cart")
    public Optional<Cart> getCartById(@RequestParam Long cartId){
        return cart_repository.findById(cartId);
    }

    @GetMapping("/cart/total")
    public double getCartTotal(@RequestParam Long cartId){
        return cart_service.getTotalPrice(cartId);
    }

    @PostMapping("/cart")
    public Cart getNewCart() {
        Cart new_cart = new Cart();
        return cart_repository.saveAndFlush(new_cart);
    }

    @PostMapping(path = "/checkout", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> checkout(@RequestParam Long cartId, @RequestBody User user) {
        double total_paid = cart_service.checkout(cartId, user);
        if (total_paid > 0)
            return new ResponseEntity<String>("Paid: "+ total_paid, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}