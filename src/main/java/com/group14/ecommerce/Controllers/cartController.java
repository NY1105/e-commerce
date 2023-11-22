package com.group14.ecommerce.Controllers;

import java.util.*;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
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
    public Cart getCartById(@RequestParam Long cartId){
        return cart_repository.getReferenceById(cartId);
    }

    @GetMapping("/cart/total")
    public double getCartTotal(@RequestParam Long cartId){
        return cart_service.getTotalPrice(cartId);
    }
}