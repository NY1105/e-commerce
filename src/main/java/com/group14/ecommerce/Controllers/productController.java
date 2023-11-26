package com.group14.ecommerce.Controllers;

import java.util.*;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;


import com.group14.ecommerce.Repository.productRepository;
import com.group14.ecommerce.Vo.Product;


@RestController
public class productController {

    @Autowired
    private productRepository product_repository;
    

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return product_repository.findAll();
    }

    @GetMapping("/product")
    public Product getOneProduct(@RequestParam Long productId){
        return product_repository.getReferenceById(productId);
    }

    @PostMapping(value = "/product", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Product newProduct(@RequestBody Product product){
        return product_repository.saveAndFlush(product);
    }
}