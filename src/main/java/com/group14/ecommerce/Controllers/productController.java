package com.group14.ecommerce.Controllers;

import java.util.*;

import com.group14.ecommerce.Service.productService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;


import com.group14.ecommerce.Repository.productRepository;
import com.group14.ecommerce.Vo.Product;


@RestController
public class productController {

    @Autowired
    private productRepository product_repository;
    private final productService productService;

    public productController(com.group14.ecommerce.Service.productService productService) {
        this.productService = productService;
    }


    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return product_repository.findAll();
    }

    @GetMapping("/product")
    public ResponseEntity<Product> getOneProduct(@RequestParam String productId){
        Optional<Product> product = product_repository.findById(productId);
        return product.map(p -> new ResponseEntity<Product>(p, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PostMapping(value = "/product", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> newProduct(@RequestBody Product product){
        return productService.addProduct(product);
    }

    @PostMapping(value = "/products", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> newProducts(@RequestBody List<Product> products){
        return productService.addProducts(products);
    }
}