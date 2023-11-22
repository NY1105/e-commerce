package com.group14.ecommerce.Service;

import com.group14.ecommerce.Repository.cartRepository;
import com.group14.ecommerce.Vo.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class cartService {

    private final cartRepository cart_repository;

    public cartService(cartRepository cartRepository){
        this.cart_repository = cartRepository;
    }

    public double getTotalPrice(Long cartId){
        List<Product> productList = cart_repository.getReferenceById(cartId).getProducts();
        double total = 0;
        for (Product p : productList) {
            total += p.getPrice();
        }
        return total;
    }

}
