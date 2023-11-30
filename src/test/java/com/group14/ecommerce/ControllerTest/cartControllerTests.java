package com.group14.ecommerce.ControllerTest;

import com.group14.ecommerce.Controllers.cartController;
import com.group14.ecommerce.Exceptions.CartNotFoundException;
import com.group14.ecommerce.Repository.cartRepository;
import com.group14.ecommerce.Service.cartService;
import com.group14.ecommerce.Vo.Cart;
import com.group14.ecommerce.Vo.Product;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(cartController.class)
public class CartControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private cartService CS;

    @Test
    void getAllCarts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.GET, "/carts")
          .accept(MediaType.APPLICATION_JSON))
          .andExpect(MockMvcResultMatchers.status()
            .isOk())
          .andReturn();

    }

    @Test
    void getCartByIdReturn200() throws Exception {
        long cartId = 1L;

        mockMvc.perform(get("/cart/" + cartId)
            .contentType("application/json"))
            .andExpect(status().isOk());

//        Mockito.when(CS.findById(cartId)).thenReturn(cart.getCartId(1L));

    }

    @Test
    void getCartByIdReturn404() throws Exception {
        long cartId = 100L;

        Mockito.when(CS.findById(cartId)).thenThrow(CartNotFoundException.class);

        mockMvc.perform(get("/cart/" + cartId)).andExpect(status().isNotFound());

    }

    @Test
    void getCartTotal() {
    }

    @Test
    void addNewProducts() throws Exception {
        Product product = new Product("product1", 123.0, 10);

        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.POST,"/cart")
          .accept(MediaType.APPLICATION_JSON).contentType("application/json"));

    }

    @Test
    void checkout() throws Exception {
        mockMvc.perform(post("/checkout")
            .contentType("application/json"))
            .andExpect(status().isOk());
    }
}
