package com.group14.ecommerce.Service;

import java.util.*;

import com.group14.ecommerce.Repository.productRepository;
import com.group14.ecommerce.Vo.Cart;
import com.group14.ecommerce.Vo.Product;
import org.springframework.stereotype.Service;

@Service
public class productService {
    private final productRepository product_repository;

    public productService(productRepository productRepository) {
        product_repository = productRepository;
    }

    public void adjustInventory(Cart cart){
        List<Product> productList = cart.getProducts();
        for (Product p : productList){
            p.setInventory(p.getInventory()-1);
        }
        product_repository.saveAllAndFlush(productList);
    }
}
