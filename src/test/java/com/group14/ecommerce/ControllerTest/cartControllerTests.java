package com.group14.ecommerce.ControllerTest;

import com.group14.ecommerce.Controllers.cartController;
import com.group14.ecommerce.Repository.discountRepository;
import com.group14.ecommerce.Repository.userRepository;
import com.group14.ecommerce.Service.cartService;
import com.group14.ecommerce.Service.productService;
import com.group14.ecommerce.Service.userService;
import com.group14.ecommerce.Vo.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
public class CartControllerTests {

    @Autowired
    private userService user_service; 
    
    @Autowired
    private discountRepository discount_repository;

    @Autowired
    private cartService cart_service;

    @Autowired
    private productService product_service;

    @Autowired
    private userRepository user_repository;


    //get all carts
    @Test
    public void test_returns_all_carts_when_masterKey_is_MASTER_KEY() {
        cartController controller = new cartController();
        Cart cart1 = new Cart(1L, new ArrayList<>());
        Cart cart2 = new Cart(2L, new ArrayList<>());
        String masterKey = "MASTER_KEY";
        List<Cart> expectedCarts = List.of(cart1, cart2);
        List<Cart> actualCarts = controller.getAllCarts(masterKey);
        assertEquals(expectedCarts, actualCarts);
    }

    @Test
    public void test_returns_empty_list_when_masterKey_is_not_MASTER_KEY() {
        cartController controller = new cartController();
        String masterKey = "INVALID_KEY";
        List<Cart> expectedCarts = Collections.emptyList();
        List<Cart> actualCarts = controller.getAllCarts(masterKey);
        assertEquals(expectedCarts, actualCarts);
    }

    @Test
    public void test_returns_empty_list_when_cart_service_findAll_returns_empty_list() {
        cartController controller = new cartController();
        String masterKey = "MASTER_KEY";
        // Mock the cart_service to return an empty list
        List<Cart> expectedCarts = Collections.emptyList();
        List<Cart> actualCarts = controller.getAllCarts(masterKey);
        assertEquals(expectedCarts, actualCarts);
    }


    //get cart by id
    @Test
    public void test_return_cart_with_given_id() {
        Long cartId = 1L;
        Cart expectedCart = new Cart();
        Mockito.when(cart_service.findById(cartId)).thenReturn(expectedCart);

        Cart actualCart = cart_service.findById(cartId);

        assertEquals(expectedCart, actualCart);
    }

    @Test
    public void test_return_null_if_id_is_null() {
        Long cartId = null;

        Cart actualCart = cart_service.findById(cartId);

        assertNull(actualCart);
    }


    //get total price
    @Test
    public void test_valid_cart_with_products() {
        Cart cart = new Cart();
        List<Product> products = new ArrayList<>();
        products.add(new Product("1", 8.0, 10));
        products.add(new Product("2", 5.0, 20));
        cart.setProducts(products);
    
        double totalPrice = cart_service.getTotalPrice(cart);
    
        assertEquals(13.0, totalPrice, 0.001);
    }

    @Test
    public void test_cart_with_no_products() {
        Cart cart = new Cart();
    
        double totalPrice = cart_service.getTotalPrice(cart);
    
        assertEquals(0.0, totalPrice, 0.001);
    }

    @Test
    public void test_cart_with_multiple_products() {
        Cart cart = new Cart();
        List<Product> products = new ArrayList<>();
        products.add(new Product("1", 5.0, 10));
        products.add(new Product("2", 10.0 , 20));
        products.add(new Product("3", 15.0 , 30));
        cart.setProducts(products);
    
        double totalPrice = cart_service.getTotalPrice(cart);
    
        assertEquals(20.0, totalPrice, 0.001);
    }

    @Test
    public void test_null_cart() {
        Cart cart = null;
    
        double totalPrice = cart_service.getTotalPrice(cart);
    
        assertEquals(0.0, totalPrice, 0.001);
    }

    @Test
    public void test_cart_with_single_product_price_zero() {
        Cart cart = new Cart();
        List<Product> products = new ArrayList<>();
        products.add(new Product("1", 0.0, 1));
        cart.setProducts(products);
    
        double totalPrice = cart_service.getTotalPrice(cart);
    
        assertEquals(0.0, totalPrice, 0.001);
    }

    //add new products
    @Test
    public void test_add_valid_products_to_cart() {
        Cart cart = new Cart();
        String[] productIds = {"1", "2", "3"};
    
        Cart result = cart_service.addNewProductsToCart(cart, productIds);
    
        List<Product> expectedProducts = new ArrayList<>();
        expectedProducts.add(new Product("1", anyDouble(), anyInt()));
        expectedProducts.add(new Product("2", anyDouble(), anyInt()));
        expectedProducts.add(new Product("3", anyDouble(), anyInt()));
        assertEquals(expectedProducts, result.getProducts());
    }

    @Test
    public void test_return_cart_with_added_products() {
        Cart cart = new Cart();
        String[] productIds = {"1", "2", "3"};
    
        Cart result = cart_service.addNewProductsToCart(cart, productIds);
    
        assertEquals(cart, result);
    }

        @Test
    public void test_handle_empty_productIds_array() {
        Cart cart = new Cart();
        String[] productIds = {};
    
        Cart result = cart_service.addNewProductsToCart(cart, productIds);
    
        assertTrue(result.getProducts().isEmpty());
    }

    @Test
    public void test_handle_null_cart_argument() {
        Cart cart = null;
        String[] productIds = {"1", "2", "3"};
    
        Cart result = cart_service.addNewProductsToCart(cart, productIds);
    
        assertNull(result);
    }

    @Test
    public void test_handle_null_productIds_argument() {
        Cart cart = new Cart();
        String[] productIds = null;
    
        Cart result = cart_service.addNewProductsToCart(cart, productIds);
    
        assertTrue(result.getProducts().isEmpty());
    }

    @Test
    public void test_handle_invalid_productIds() {
        Cart cart = new Cart();
        String[] productIds = {"1", "invalid", "3"};
    
        Cart result = cart_service.addNewProductsToCart(cart, productIds);
    
        List<Product> expectedProducts = new ArrayList<>();
        expectedProducts.add(new Product("1", anyDouble(), anyInt()));
        expectedProducts.add(new Product("3", anyDouble(), anyInt()));
        assertEquals(expectedProducts, result.getProducts());
    }


    //checkout
    @Test
    public void test_unauthenticated_user_returns_minus_one() {
        User user = new User();
        when(user_service.auth(any(User.class))).thenReturn(Optional.empty());
    
        double result = cart_service.checkout(1L, user);
    
        assertEquals(-1, result, 0.01);
    }

    @Test
    public void test_total_price_after_discounts_and_inventory_adjustment() {
        Cart cart = new Cart();
        User user = new User();
        cart.setProducts(new ArrayList<>());
        double expectedTotal = 100.0;
        when(cart_service.findById(anyLong())).thenReturn(cart);
        when(user_service.auth(any(User.class))).thenReturn(Optional.of(user));
        when(discount_repository.findByCount(anyInt())).thenReturn(Optional.empty());
        when(discount_repository.findByTier(anyInt())).thenReturn(Optional.empty());
        when(cart_service.getDiscountedTotalPrice(any(Cart.class), any(User.class))).thenReturn(expectedTotal);
    
        double actualTotal = cart_service.checkout(1L, user);
    
        assertEquals(expectedTotal, actualTotal, 0.01);
        verify(product_service).adjustInventory(cart);
        verify(user_repository).saveAndFlush(user);
    }

    @Test
    public void test_update_total_spent_attribute() {
        Cart cart = new Cart();
        User user = new User();
        cart.setProducts(new ArrayList<>());
        double total = 100.0;
        when(cart_service.findById(anyLong())).thenReturn(cart);
        when(user_service.auth(any(User.class))).thenReturn(Optional.of(user));
        when(discount_repository.findByCount(anyInt())).thenReturn(Optional.empty());
        when(discount_repository.findByTier(anyInt())).thenReturn(Optional.empty());
        when(cart_service.getDiscountedTotalPrice(any(Cart.class), any(User.class))).thenReturn(total);
    
        cart_service.checkout(1L, user);
    
        assertEquals(total, user.getTotalSpent(), 0.01);
    }

    @Test
    public void test_positive_value_for_valid_cart_and_authenticated_user() {

        Cart cart = new Cart();
        User user = new User();
        cart.setProducts(new ArrayList<>());
        double total = 100.0;
        when(cart_service.findById(anyLong())).thenReturn(cart);
        when(user_service.auth(any(User.class))).thenReturn(Optional.of(user));
        when(discount_repository.findByCount(anyInt())).thenReturn(Optional.empty());
        when(discount_repository.findByTier(anyInt())).thenReturn(Optional.empty());
        when(cart_service.getDiscountedTotalPrice(any(Cart.class), any(User.class))).thenReturn(total);

        double actualTotal = cart_service.checkout(1L, user);

        assertTrue(actualTotal > 0);
    }

    @Test
    public void test_handling_cart_with_no_products() {
        Cart cart = new Cart();
        User user = new User();
        when(cart_service.findById(anyLong())).thenReturn(cart);
        when(user_service.auth(any(User.class))).thenReturn(Optional.of(user));

        double actualTotal = cart_service.checkout(1L, user);

        assertEquals(0, actualTotal, 0.01);
    }

    @Test
    public void test_handling_user_with_no_total_spent_attribute() {
        Cart cart = new Cart();
        User user = new User();
        cart.setProducts(new ArrayList<>());
        double total = 100.0;
        when(cart_service.findById(anyLong())).thenReturn(cart);
        when(user_service.auth(any(User.class))).thenReturn(Optional.of(user));
        when(discount_repository.findByCount(anyInt())).thenReturn(Optional.empty());
        when(discount_repository.findByTier(anyInt())).thenReturn(Optional.empty());
        when(cart_service.getDiscountedTotalPrice(any(Cart.class), any(User.class))).thenReturn(total);
    
        cart_service.checkout(1L, user);
    
        assertEquals(total, user.getTotalSpent(), 0.01);
    }

}
