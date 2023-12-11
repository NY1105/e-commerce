package com.group14.ecommerce.Service;

import java.util.*;

import com.group14.ecommerce.Repository.productRepository;
import com.group14.ecommerce.Vo.Cart;
import com.group14.ecommerce.Vo.Product;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;

@Service
public class productService {
  private final productRepository product_repository;

  public productService(productRepository productRepository) {
    product_repository = productRepository;
  }

  public ResponseEntity<Product> addProduct(Product product) {
    if (product.getInventory() < 0) {
        return ResponseEntity.badRequest().build();
    }
    product_repository.saveAndFlush(product);
    return new ResponseEntity<Product>(product,HttpStatus.CREATED);
  }

  public ResponseEntity<List<Product>> addProducts(List<Product> products) {
    for (Product product : products) {
      if (product.getInventory() < 0) {
        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.BAD_REQUEST);
      }
    }
    product_repository.saveAllAndFlush(products);
    return new ResponseEntity<List<Product>>(products, HttpStatus.CREATED);
  }
    
  public void adjustInventory(Cart cart) {
    List<Product> productList = cart.getProducts();
    for (Product p : productList) {
      p.setInventory(p.getInventory() - 1);
    }
    product_repository.saveAllAndFlush(productList);
  }
}
