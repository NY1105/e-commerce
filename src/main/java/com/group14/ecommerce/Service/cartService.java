package com.group14.ecommerce.Service;

import com.group14.ecommerce.Repository.cartRepository;
import com.group14.ecommerce.Repository.userRepository;
import com.group14.ecommerce.Vo.Product;
import com.group14.ecommerce.Vo.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class cartService {

    private final cartRepository cart_repository;
    private final userRepository user_repository;
    private final userService user_service;

    public cartService(cartRepository cartRepository, userRepository userRepository, userService userService){
        this.cart_repository = cartRepository;
        this.user_repository = userRepository;
        this.user_service = userService;
    }


    public double getTotalPrice(Long cartId){
        List<Product> productList = cart_repository.getReferenceById(cartId).getProducts();
        double total = 0;
        for (Product p : productList) {        
            total += p.getPrice();
        }
        return total;
    }

    public double checkout(Long cartId, User user) {
        Optional<User> authed_user = user_service.auth(user);
        double total = getTotalPrice(cartId);
        if (authed_user.isEmpty())
            return 0;
        authed_user.get().setTotalSpent(authed_user.get().getTotalSpent()+total);
        user_repository.saveAndFlush(authed_user.get());
        return total;
    }
}
