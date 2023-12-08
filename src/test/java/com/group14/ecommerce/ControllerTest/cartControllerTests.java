package com.group14.ecommerce.ControllerTest;

import com.group14.ecommerce.Controllers.cartController;
import com.group14.ecommerce.Exceptions.CartNotFoundException;
import com.group14.ecommerce.Repository.userRepository;
import com.group14.ecommerce.Service.cartService;
<<<<<<< Updated upstream
import com.group14.ecommerce.Vo.Cart;
import com.group14.ecommerce.Vo.Product;
=======
import com.group14.ecommerce.Service.productService;
import com.group14.ecommerce.Service.userService;

>>>>>>> Stashed changes
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< Updated upstream
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
=======
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
>>>>>>> Stashed changes

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

<<<<<<< Updated upstream
@WebMvcTest(cartController.class)
public class cartControllerTests {
=======
import java.util.Arrays;
import java.util.Collections;
import com.group14.ecommerce.Vo.Cart;
import com.group14.ecommerce.Vo.User;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CartControllerTests {

>>>>>>> Stashed changes
    @Autowired
    cartService CS;

    @Autowired
    userService US;

<<<<<<< Updated upstream
    @Test
    void getAllCarts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.GET, "/carts")
          .accept(MediaType.APPLICATION_JSON))
          .andExpect(MockMvcResultMatchers.status()
            .isOk())
          .andReturn();
=======
    @Autowired
    productService PS;

    @Autowired
    userRepository UR;


    // getAllCarts tests
    @Test
    public void test_returns_all_carts_when_masterKey_is_MASTER_KEY() {
        cartController controller = new cartController();
        String masterKey = "MASTER_KEY";

        List<Cart> expectedCarts = List.of(new Cart(1L, anyList()), new Cart(2L, anyList()));
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
>>>>>>> Stashed changes

        @Test
    public void test_returns_empty_list_when_cart_service_findAll_returns_empty_list() {
        cartController controller = new cartController();
        String masterKey = "MASTER_KEY";

        List<Cart> expectedCarts = Collections.emptyList();
        List<Cart> actualCarts = controller.getAllCarts(masterKey);
        assertEquals(expectedCarts, actualCarts);
    }


    // getCartById tests
    @Test
    public void test_valid_cartId_returnsOptionalCart() throws CartNotFoundException {
        Long cartId = 1L;
        Optional<Cart> result = CS.findById(cartId);

        assertTrue(result.isPresent());
        assertEquals(cartId, result.get().getCartId());
    }

        @Test
    public void test_invalid_cartId_returnsEmptyOptional() throws CartNotFoundException {
        Long cartId = -1L;
    
        Optional<Cart> result = CS.findById(cartId);
    
        assertFalse(result.isPresent());
    }

    @Test
<<<<<<< Updated upstream
    void getCartByIdReturn200() throws Exception {
        long cartId = 1L;

        mockMvc.perform(get("/cart/" + cartId)
            .contentType("application/json"))
            .andExpect(status().isOk());

//        Mockito.when(CS.findById(cartId)).thenReturn(cart.getCartId(1L));
=======
    public void test_no_cartId_returnsEmptyOptional() throws CartNotFoundException {
        Optional<Cart> result = CS.findById(null);
    
        assertFalse(result.isPresent());
    }

    @Test
    public void test_nonExistent_cartId_throwsCartNotFoundException() {
        Long cartId = 100L;
    
        assertThrows(CartNotFoundException.class, () -> CS.findById(cartId));
    }
>>>>>>> Stashed changes

    @Test
    public void test_null_cartId_returnsEmptyOptional() throws CartNotFoundException {
        Optional<Cart> result = CS.findById(null);
    
        assertFalse(result.isPresent());
    }

    @Test
    public void test_empty_cart_returnsOptionalWithNullFields() throws CartNotFoundException {
        Long cartId = 1L;
        Optional<Cart> result = CS.findById(cartId);
    
        assertTrue(result.isPresent());
        assertNull(result.get().getCartId());
        assertNull(result.get().getProducts());
    }

    //Total price tests
    @Test
    public void test_valid_cart_id() {
        Long cartId = 1L;
        Optional<Cart> cart = CS.findById(cartId);
        Double totalPrice = CS.getTotalPrice(cart);
    
        assertNotNull(totalPrice);
        assertEquals(10.0, totalPrice, 0.01);
    }

    @Test
    public void test_empty_cart() {
        Long cartId = 2L;
        Optional<Cart> cart = CS.findById(cartId);
        Double totalPrice = CS.getTotalPrice(cart);
    
        assertNotNull(totalPrice);
        assertEquals(0.0, totalPrice, 0.01);
    }

    @Test
    public void test_cart_no_products() {
        Long cartId = 3L;
        Optional<Cart> cart = CS.findById(cartId);
        Double totalPrice = CS.getTotalPrice(cart);
    
        assertNotNull(totalPrice);
        assertEquals(0.0, totalPrice, 0.01);
    }

    @Test
    public void test_invalid_cart_id() {
        Long cartId = 4L;
        Optional<Cart> cart = CS.findById(cartId);
    
        assertThrows(CartNotFoundException.class, () -> CS.getTotalPrice(cart));
    }

    @Test
    public void test_empty_optional() {
        Long cartId = 5L;
        Optional<Cart> cart = CS.findById(cartId);
        Double totalPrice = CS.getTotalPrice(cart);

        assertNull(totalPrice);
    }

    @Test
    public void test_null_total_price() {
        Long cartId = 6L;        
        Optional<Cart> cart = CS.findById(cartId);
        Double totalPrice = CS.getTotalPrice(cart);

        assertNull(totalPrice);
    }

    // addNewProductsToCart tests
    @Test
    public void test_addNewProducts_success() throws CartNotFoundException {
        long cartId = 1;
        String[] productIds = {"p1", "p2"};
        Cart cart = new Cart();
        when(CS.findById(cartId)).thenReturn(Optional.of(cart));
    
        Cart result = CS.addNewProductsToCart(Optional.of(cart), productIds);
        
        assertEquals(cart, result);
        assertEquals(2, cart.getProducts().size());
        assertTrue(cart.getProducts().containsAll(Arrays.asList(productIds)));
    }

    @Test
    public void test_addNewProducts_emptyArray() throws CartNotFoundException {
        long cartId = 1;
        String[] productIds = {};
        Cart cart = new Cart();
        when(CS.findById(cartId)).thenReturn(Optional.of(cart));
    
        Cart result = CS.addNewProductsToCart(Optional.of(cart), productIds);

        assertEquals(cart, result);
        assertTrue(cart.getProducts().isEmpty());
    }

    @Test
    void getCartByIdReturn404() throws Exception {
        long cartId = 100L;

        Mockito.when(CS.findById(cartId)).thenThrow(CartNotFoundException.class);

        mockMvc.perform(get("/cart/" + cartId)).andExpect(status().isNotFound());

    }

    @Test
    public void test_addNewProducts_nonExistentCart() {
        long cartId = 1;
        String[] productIds = {"p1", "p2"};
        when(CS.findById(cartId)).thenReturn(Optional.empty());
        Optional<Cart> cart = CS.findById(cartId);
    
        assertThrows(CartNotFoundException.class, () -> CS.addNewProductsToCart(cart, productIds));
    }

    @Test
    public void test_addNewProducts_nonExistentProductId() throws CartNotFoundException {
        long cartId = 1;
        String[] productIds = {"p1", "p2"};
        Optional<Cart> cart = CS.findById(cartId);
        when(CS.findById(cartId)).thenReturn(Optional.of(cart.get()));
    
        Cart result = CS.addNewProductsToCart(cart, productIds);
    
        assertEquals(cart, result);
        assertTrue(cart.get().getProducts().isEmpty());
    }

    @Test
    public void test_addNewProducts_nonStringProductId() throws CartNotFoundException {
        long cartId = 1;
        String[] productIds = {"p1", "p2", "123"};
        Optional<Cart> cart = CS.findById(cartId);
        when(CS.findById(cartId)).thenReturn(Optional.of(cart.get()));
    
        Cart result = CS.addNewProductsToCart(cart, productIds);
    
        assertEquals(cart, result);
        assertTrue(cart.get().getProducts().isEmpty());
    }

    // checkout tests
    @Test
    public void test_unauthorized_user() {
        // Arrange
        cartController controller = new cartController();
        Long cartId = 1L;
        User user = new User();

        Optional<User> authed_user = Optional.empty();
        when(US.auth(user)).thenReturn(authed_user);
    
        // Act
        double actualTotal = CS.checkout(cartId, user);
        ResponseEntity<String> response = controller.checkout(cartId, user);
    
        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(-1.0, actualTotal, 0.001);
        verify(CS, never()).findById(any(Long.class));
        verify(PS, never()).adjustInventory(any(Cart.class));
        verify(UR, never()).saveAndFlush(any(User.class));
    }

    @Test
    public void test_valid_cart_id_and_authenticated_user() {
        Long cartId = 1L;
        User user = new User();
        cartController controller = new cartController();
    
        Optional<User> authed_user = Optional.of(user);
        when(US.auth(user)).thenReturn(authed_user);
        Optional<Cart> cart = Optional.of(new Cart());
        when(CS.findById(cartId)).thenReturn(cart);
        double expectedTotal = 100.0;
        when(CS.getDiscountedTotalPrice(cart.get(), authed_user.get())).thenReturn(expectedTotal);
    
        double actualTotal = CS.checkout(cartId, user);
        ResponseEntity<String> response = controller.checkout(cartId, user);
    
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedTotal, actualTotal, 0.001);
        verify(PS).adjustInventory(cart.get());
        verify(UR).saveAndFlush(authed_user.get());
    }

        @Test
    public void test_no_products_and_authenticated_user() {
        Long cartId = 1L;
        User user = new User();
        cartController controller = new cartController();
    
        Optional<User> authed_user = Optional.of(user);
        when(US.auth(user)).thenReturn(authed_user);
        Optional<Cart> cart = Optional.of(new Cart());
        when(CS.findById(cartId)).thenReturn(cart);
        when(CS.getDiscountedTotalPrice(cart.get(), authed_user.get())).thenReturn(0.0);
    
        double actualTotal = CS.checkout(cartId, user);
        ResponseEntity<String> response = controller.checkout(cartId, user);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0.0, actualTotal, 0.001);
        verify(PS, never()).adjustInventory(any(Cart.class));
        verify(UR).saveAndFlush(authed_user.get());
    }

    @Test
    public void test_no_discounts_and_authenticated_user() {
        Long cartId = 1L;
        User user = new User();
        cartController controller = new cartController();

    
        Optional<User> authed_user = Optional.of(user);
        when(US.auth(user)).thenReturn(authed_user);
        Optional<Cart> cart = Optional.of(new Cart());
        when(CS.findById(cartId)).thenReturn(cart);
        double expectedTotal = 100.0;
        when(CS.getDiscountedTotalPrice(cart.get(), authed_user.get())).thenReturn(expectedTotal);
    
        double actualTotal = CS.checkout(cartId, user);
        ResponseEntity<String> response = controller.checkout(cartId, user);
    
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedTotal, actualTotal, 0.001);
        verify(PS).adjustInventory(cart.get());
        verify(UR).saveAndFlush(authed_user.get());
    }

    @Test
    public void test_invalid_cart_id_and_authenticated_user() {
        Long cartId = 1L;
        User user = new User();
        cartController controller = new cartController();
    
        Optional<User> authed_user = Optional.of(user);
        when(US.auth(user)).thenReturn(authed_user);
        Optional<Cart> cart = Optional.empty();
        when(CS.findById(cartId)).thenReturn(cart);
    
        double actualTotal = CS.checkout(cartId, user);
        ResponseEntity<String> response = controller.checkout(cartId, user);
    
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(0.0, actualTotal, 0.001);
        verify(PS, never()).adjustInventory(any(Cart.class));
        verify(UR, never()).saveAndFlush(any(User.class));
    }

    @Test
    public void test_out_of_stock_products_and_authenticated_user() {
        Long cartId = 1L;
        User user = new User();
        cartController controller = new cartController();
    
        Optional<User> authed_user = Optional.of(user);
        when(US.auth(user)).thenReturn(authed_user);
        Optional<Cart> cart = Optional.of(new Cart());
        when(CS.findById(cartId)).thenReturn(cart);
        double expectedTotal = 0.0;
        when(CS.getDiscountedTotalPrice(cart.get(), authed_user.get())).thenReturn(expectedTotal);

        double actualTotal = CS.checkout(cartId, user);
        ResponseEntity<String> response = controller.checkout(cartId, user);
    
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedTotal, actualTotal, 0.001);
        verify(PS, never()).adjustInventory(any(Cart.class));
        verify(UR).saveAndFlush(authed_user.get());
    }

}
