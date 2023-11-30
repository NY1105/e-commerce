package com.group14.ecommerce.Service;

import com.group14.ecommerce.Exceptions.CartNotFoundException;
import com.group14.ecommerce.Repository.cartRepository;
import com.group14.ecommerce.Repository.discountRepository;
import com.group14.ecommerce.Repository.productRepository;
import com.group14.ecommerce.Repository.userRepository;
import com.group14.ecommerce.Vo.*;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class cartService {

    private final cartRepository cart_repository;
    private final userRepository user_repository;
    private final userService user_service;
    private final productRepository product_repository;
    private final discountRepository discount_repository;
    private final productService product_service;

    public cartService(cartRepository cartRepository, userRepository userRepository, userService userService, productRepository productRepository, discountRepository discountRepository, productService productService){
        cart_repository = cartRepository;
        user_repository = userRepository;
        user_service = userService;
        product_repository = productRepository;
        discount_repository = discountRepository;
        product_service = productService;
    }


    public double getTotalPrice(Cart cart){
        List<Product> productList = cart.getProducts();
        double total = 0;
        for (Product p : productList) {        
            total += p.getPrice();
        }
        return total;
    }

    public List<Cart> findAll(){
        return cart_repository.findAll();
    }

    public Cart findById(Long cartId) throws CartNotFoundException {
        Optional<Cart> cart = cart_repository.findById(cartId);
        if (cart.isPresent())
            return cart.get();
        throw new CartNotFoundException();
    }

    public Cart saveAndFlush(Cart cart){
        return cart_repository.saveAndFlush(cart);
    }



    public double getDiscountedTotalPrice(Cart cart, User user){
        double total = getTotalPrice(cart);
        Optional<Discount> cart_discount = discount_repository.findByCount(cart.getProducts().size());
        Optional<Discount> membership_discount = discount_repository.findByTier(cart.getProducts().size());
        if (cart_discount.isPresent())
            total *= 1 - cart_discount.get().getDiscountPercentOff();
        if (membership_discount.isPresent())
            total *= 1 - membership_discount.get().getDiscountPercentOff();
        if (cart_discount.isPresent())
            total -= cart_discount.get().getDiscountAmount();
        if (membership_discount.isPresent())
            total -= membership_discount.get().getDiscountAmount();
        return total;
    }

    public double checkout(Long cartId, User user) {
        Optional<User> authed_user = user_service.auth(user);
        if (authed_user.isEmpty())
            return -1;
        Optional<Cart> cart = cart_repository.findById(cartId);
        if (cart.isEmpty())
            return 0;
        double total = getDiscountedTotalPrice(cart.get(), authed_user.get());
        product_service.adjustInventory(cart.get());
        authed_user.get().setTotalSpent(authed_user.get().getTotalSpent()+total);
        user_repository.saveAndFlush(authed_user.get());
        return total;
    }

    public Cart addNewProductsToCart(Cart cart, String[] productIds) {
        List<Product> products = new ArrayList<>(Collections.emptyList());
        for (String p : productIds){
            Optional<Product> product = product_repository.findById(p);
            if (product.isEmpty())
                continue;
            products.add(product.get());
        }
        cart.setProducts(products);
        return cart_repository.save(cart);
    }
}
