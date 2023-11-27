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
    public Optional<Cart> getCartById(@RequestParam(required = false) Long cartId){
        if (cartId != null) 
            return cart_repository.findById(cartId);
        Optional<Cart> cart = Optional.of(new Cart());
        return Optional.of(cart_repository.saveAndFlush(cart.get()));
    }

    @GetMapping("/cart/total")
    public ResponseEntity<Double> getCartTotal(@RequestParam Long cartId){
        Optional<Cart> cart = cart_repository.findById(cartId);
        return cart.map(value -> new ResponseEntity<>(cart_service.getTotalPrice(value), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<Double>(HttpStatus.BAD_REQUEST));
    }
    @PostMapping("/cart")
    public ResponseEntity<Cart> addNewProducts(@RequestParam long cartId, @RequestBody long[] productIds){
        Optional<Cart> cart = cart_repository.findById(cartId);
        return cart.map(value -> new ResponseEntity<>(cart_service.addNewProductsToCart(value, productIds), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
    @PostMapping(path = "/checkout", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> checkout(@RequestParam Long cartId, @RequestBody User user) {
        double total_paid = cart_service.checkout(cartId, user);
        if (total_paid > 0)
            return new ResponseEntity<String>("Paid: "+ total_paid, HttpStatus.OK);
        if (total_paid == -1)
            return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}