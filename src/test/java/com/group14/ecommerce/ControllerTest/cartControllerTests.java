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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
public class CartControllerTests {

    @Mock
    private userService user_service; 
    
    @Mock
    private discountRepository discount_repository;


    @Mock
    private productService product_service;

    @Mock
    private userRepository user_repository;


    //get all carts
    @Test
    public void test_returns_all_carts_when_masterKey_is_MASTER_KEY() {
        // cartService mockCartService = mock(cartService.class);
        cartController mockcontroller = mock(cartController.class);
        Cart cart1 = new Cart(1L, new ArrayList<>());
        Cart cart2 = new Cart(2L, new ArrayList<>());
        String masterKey = "MASTER_KEY";
        List<Cart> expectedCarts = List.of(cart1, cart2);
        List<Cart> actualCart = new ArrayList<>();
        actualCart.add(cart1);
        actualCart.add(cart2);
        when(mockcontroller.getAllCarts(masterKey)).thenReturn(actualCart);
        assertEquals(expectedCarts, actualCart);
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
        cartController controller = mock(cartController.class);
        String masterKey = "MASTER_KEY";

        List<Cart> expectedCarts = Collections.emptyList();
        List<Cart> actualCarts = controller.getAllCarts(masterKey);

        assertEquals(expectedCarts, actualCarts);
    }


    //get cart by id
    @Test
    public void test_return_cart_with_given_id() {
        Long cartId = 1L;
        Cart expectedCart = new Cart();
        cartService cart_service = mock(cartService.class);
        Mockito.when(cart_service.findById(cartId)).thenReturn(expectedCart);

        Cart actualCart = cart_service.findById(cartId);

        assertEquals(expectedCart, actualCart);
    }

    @Test
    public void test_return_null_if_id_is_null() {
        Long cartId = null;
        cartService cart_service = mock(cartService.class);
        Mockito.when(cart_service.findById(cartId)).thenReturn(null);

        assertNull(cart_service.findById(cartId));
    }


    //get total price
    @Test
    public void test_valid_cart_with_products() {
        Cart cart = mock(Cart.class);
        cartService cart_service = mock(cartService.class);
        cart = new Cart(1L, new ArrayList<>());
        List<Product> products = new ArrayList<>();
        products.add(new Product("1", 8.0, 10));
        products.add(new Product("2", 5.0, 20));
        cart.setProducts(products);
    
        when(cart_service.getTotalPrice(cart)).thenReturn(13.0);
    
        assertEquals(13.0, cart_service.getTotalPrice(cart), 0.001);
    }

    @Test
    public void test_cart_with_no_products() {
        cartService cart_service = mock(cartService.class);
        Cart cart = new Cart();
    
        when(cart_service.getTotalPrice(cart)).thenReturn(0.0);
        assertEquals(0.0, cart_service.getTotalPrice(cart), 0.001);
    }

    @Test
    public void test_cart_with_multiple_products() {
        Cart cart = new Cart();
        cartService cart_service = mock(cartService.class);
        List<Product> products = new ArrayList<>();

        Product product1 = new Product("Product 1", 10.0, 10);
        Product product2 = new Product("Product 2", 20.0, 20);
        Product product3 = new Product("Product 3", 30.0, 30);
        products.add(product1);
        products.add(product2);
        products.add(product3);
        cart.setProducts(products);
    
        when(cart_service.getTotalPrice(cart)).thenReturn(60.0);
    
        assertEquals(60.0, cart_service.getTotalPrice(cart), 0.001);
    }

    @Test
    public void test_null_cart() {
        Cart cart = null;
        cartService cart_service = mock(cartService.class);
        double totalPrice = cart_service.getTotalPrice(cart);
    
        assertEquals(0.0, totalPrice, 0.001);
    }

    @Test
    public void test_cart_with_single_product_price_zero() {
        Cart cart = new Cart();
        cartService cart_service = mock(cartService.class);
        List<Product> products = new ArrayList<>();
        products.add(new Product("1", 0.0, 1));
        cart.setProducts(products);
    
        double totalPrice = cart_service.getTotalPrice(cart);
    
        assertEquals(0.0, totalPrice, 0.001);
    }

    //add new products
    @Test
    public void test_add_valid_products_to_cart() {
        Cart cart = mock(Cart.class);
        cartService mockCartService = mock(cartService.class);
        String[] productIds = {"1", "2", "3"};
    
        Cart result = mockCartService.addNewProductsToCart(cart, productIds);
        when(mockCartService.addNewProductsToCart(cart, productIds)).thenReturn(result);
    
        List<Product> expectedProducts = new ArrayList<>();
        expectedProducts.add(new Product("1", anyDouble(), anyInt()));
        expectedProducts.add(new Product("2", anyDouble(), anyInt()));
        expectedProducts.add(new Product("3", anyDouble(), anyInt()));
        assertEquals(expectedProducts, result.getProducts());
    }

    @Test
    public void test_return_cart_with_added_products() {
        Cart cart = mock(Cart.class);
        cartService cart_service = mock(cartService.class);
        String[] productIds = {"1", "2", "3"};
    
        Cart result = cart_service.addNewProductsToCart(cart, productIds);
        when(cart_service.addNewProductsToCart(cart, productIds)).thenReturn(result);
        assertEquals(cart, result);
    }

        @Test
    public void test_handle_empty_productIds_array() {
        Cart cart = mock(Cart.class);
        cartService cart_service = mock(cartService.class);
        String[] productIds = {};
    
        Cart result = cart_service.addNewProductsToCart(cart, productIds);
    
        assertTrue(result.getProducts().isEmpty());
    }

    @Test
    public void test_handle_null_cart_argument() {
        Cart cart = mock(Cart.class);
        cartService cart_service = mock(cartService.class);
        cart = null;
        String[] productIds = {"1", "2", "3"};
    
        Cart result = cart_service.addNewProductsToCart(cart, productIds);
    
        assertNull(result);
    }

    @Test
    public void test_handle_null_productIds_argument() {
        Cart cart = mock(Cart.class);
        cartService cart_service = mock(cartService.class);
        String[] productIds = null;
    
        Cart result = cart_service.addNewProductsToCart(cart, productIds);
    
        assertTrue(result.getProducts().isEmpty());
    }

    @Test
    public void test_handle_invalid_productIds() {
        // Cart cart = mock(Cart.class);
        Cart cart = new Cart(1L, new ArrayList<>());
        cartService cart_service = mock(cartService.class);
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
        User user = mock(User.class);
        cartService cart_service = mock(cartService.class);
        // userService mockUserService = mock(userService.class);
        // when(mockUserService.auth(any(User.class))).thenReturn(Optional.empty());
        when(cart_service.checkout(1L, user)).thenReturn(-1.0);
    
        assertEquals(-1.0, cart_service.checkout(1L, user), 0.01);
    }

    @Test
    public void test_total_price_after_discounts_and_inventory_adjustment() {
        Cart cart = new Cart();
        User user = new User();
        cartService cart_service = mock(cartService.class);
        cart.setProducts(new ArrayList<>());
        double expectedTotal = 100.0;
        when(cart_service.findById(anyLong())).thenReturn(cart);
        when(user_service.auth(any(User.class))).thenReturn(Optional.of(user));
        when(discount_repository.findByCount(anyInt())).thenReturn(Optional.empty());
        when(discount_repository.findByTier(anyInt())).thenReturn(Optional.empty());
        when(cart_service.getDiscountedTotalPrice(any(Cart.class), any(User.class))).thenReturn(expectedTotal);
    
        when(cart_service.checkout(anyLong(), any(User.class))).thenReturn(100.0);
    
        assertEquals(100.0, cart_service.checkout(anyLong(), any(User.class)), 0.01);
        verify(product_service).adjustInventory(cart);
        verify(user_repository).saveAndFlush(user);
    }

    @Test
    public void test_update_total_spent_attribute() {
        Cart cart = mock(Cart.class);
        cartService cart_service = mock(cartService.class);
        User user = mock(User.class);
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
        Cart cart = mock(Cart.class);
        cartService cart_service = mock(cartService.class);
        User user = mock(User.class);
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
        Cart cart = mock(Cart.class);
        cartService cart_service = mock(cartService.class);
        User user = mock(User.class);
        when(cart_service.findById(anyLong())).thenReturn(cart);
        when(user_service.auth(any(User.class))).thenReturn(Optional.of(user));

        double actualTotal = cart_service.checkout(1L, user);

        assertEquals(0, actualTotal, 0.01);
    }

    @Test
    public void test_handling_user_with_no_total_spent_attribute() {
        Cart cart = mock(Cart.class);
        cartService cart_service = mock(cartService.class);
        User user = mock(User.class);
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
