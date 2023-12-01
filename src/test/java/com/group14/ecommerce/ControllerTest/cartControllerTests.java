package com.group14.ecommerce.ControllerTest;

import com.group14.ecommerce.Controllers.cartController;
import com.group14.ecommerce.Exceptions.CartNotFoundException;
import com.group14.ecommerce.Repository.cartRepository;
import com.group14.ecommerce.Service.cartService;
import com.group14.ecommerce.Vo.Cart;
import com.group14.ecommerce.Vo.Product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static com.jayway.jsonpath.JsonPath.read;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import java.util.List;

@WebMvcTest(cartController.class)
public class CartControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private cartService CS;

        @BeforeEach
    void clearDatabase(@Autowired JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0;");
        jdbcTemplate.execute("TRUNCATE TABLE ecommerce.discount;");
        jdbcTemplate.execute("TRUNCATE TABLE ecommerce.product;");
        jdbcTemplate.execute("TRUNCATE TABLE ecommerce.user;");
        jdbcTemplate.execute("TRUNCATE TABLE ecommerce.cart;");
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1;");
    }

    @Test
    void getAllCarts() throws Exception {
        MvcResult result_0 = mockMvc.perform(
                        MockMvcRequestBuilders.request(HttpMethod.GET, "/carts")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseJson = result_0.getResponse().getContentAsString();
        List<String> jsonArray = read(responseJson, "$");
        assert jsonArray.toString().equals("[]");

    }

    @Test
    void getCartById() throws Exception {
        List<Product> list = null;
        long cartId = 1L;

        MvcResult result_0 = mockMvc.perform(
                        MockMvcRequestBuilders.request(HttpMethod.GET, "/cart/" + cartId)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseJson = result_0.getResponse().getContentAsString();
        List<String> jsonArray = read(responseJson, "$");
        assert jsonArray.toString().equals("[{\"cartId\":\"1\",\"Product:\"[]\"}]");

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
