package com.group14.ecommerce.Controllers;

import java.util.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;


import com.group14.ecommerce.Repository.productRepository;
import com.group14.ecommerce.Vo.Product;


@RestController
public class productController {

    @Autowired
    private productRepository product_repository;

    @GetMapping("/product")
    public String test(){
        return("this is a test");
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return product_repository.findAll();
    }
}