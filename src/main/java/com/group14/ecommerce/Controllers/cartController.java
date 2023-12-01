package com.group14.ecommerce.Controllers;

import com.group14.ecommerce.Exceptions.CartNotFoundException;
import com.group14.ecommerce.Vo.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.group14.ecommerce.Vo.Cart;
import com.group14.ecommerce.Service.cartService;

import java.util.Collections;
import java.util.List;

@RestController
public class cartController {

    @Autowired
    private cartService cart_service;

    @GetMapping("/carts")
    public List<Cart> getAllCarts(@RequestBody String masterKey) {
        if (masterKey.equals("MASTER_KEY")) return cart_service.findAll();
        return Collections.emptyList();
    }

    @GetMapping("/cart")
    public ResponseEntity<?> getCartById(@RequestParam(required = false) Long cartId) throws CartNotFoundException {
        return new ResponseEntity<>(cart_service.findById(cartId), HttpStatus.OK);
    }

    @GetMapping("/cart/total")
    public ResponseEntity<Double> getCartTotal(@RequestParam Long cartId) throws CartNotFoundException{
        Cart cart = cart_service.findById(cartId);
//      return cart.map(value -> new ResponseEntity<>(cart_service.getTotalPrice(value), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<Double>(HttpStatus.BAD_REQUEST));
        return new ResponseEntity<>(cart_service.getTotalPrice(cart), HttpStatus.OK);
    }
    @PostMapping("/cart")
    public ResponseEntity<Cart> addNewProducts(@RequestParam long cartId, @RequestBody String[] productIds) throws CartNotFoundException{
        Cart cart = cart_service.findById(cartId);
//      return cart.map(value -> new ResponseEntity<>(cart_service.addNewProductsToCart(value, productIds), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        return new ResponseEntity<>(cart_service.addNewProductsToCart(cart, productIds), HttpStatus.OK);
    }
    @PostMapping(path = "/checkout", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> checkout(@RequestParam Long cartId, @RequestBody User user) {
        double total_paid = cart_service.checkout(cartId, user);
        if (total_paid > 0)
            return new ResponseEntity<>("Paid: " + total_paid, HttpStatus.OK);
        if (total_paid == -1)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}