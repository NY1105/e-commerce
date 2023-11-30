package com.group14.ecommerce;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group14.ecommerce.Vo.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static com.jayway.jsonpath.JsonPath.read;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
class ProductControllerTests {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @BeforeEach
    void clearDatabase(@Autowired JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0;");
        jdbcTemplate.execute("TRUNCATE TABLE ecommerce.discount;");
        jdbcTemplate.execute("TRUNCATE TABLE ecommerce.product;");
        jdbcTemplate.execute("TRUNCATE TABLE ecommerce.user;");
        jdbcTemplate.execute("TRUNCATE TABLE ecommerce.cart;");
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1;");
    }

    @BeforeEach
    void setUp() {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }


    // TEST: GET /products
    @Test
    public void getProductsToListBeforeAddingAny() throws Exception {
        MvcResult result_0 = mockMvc.perform(
                        MockMvcRequestBuilders.request(HttpMethod.GET, "/products")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseJson = result_0.getResponse().getContentAsString();
        List<String> jsonArray = read(responseJson, "$");
        assert jsonArray.toString().equals("[]");
    }
    // TEST: GET /products
    @Test
    public void getProductsToListAfterAddingOneProduct() throws Exception {
        MvcResult result_1 = mockMvc.perform(
                        MockMvcRequestBuilders.request(HttpMethod.POST, "/product")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType("application/json").content(asJsonString(new Product("2",20,10))))
                .andReturn();

        MvcResult result_0 = mockMvc.perform(
                        MockMvcRequestBuilders.request(HttpMethod.GET, "/products")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseJson = result_0.getResponse().getContentAsString();
        List<String> jsonArray = read(responseJson, "$");
        assert jsonArray.toString().equals("[{\"productId\":\"2\",\"price\":20.0,\"inventory\":10}]");
    }
    // TEST: GET /products
    @Test
    public void getProductsToListAfterAddingTwoProducts() throws Exception {
        MvcResult result_1 = mockMvc.perform(
                        MockMvcRequestBuilders.request(HttpMethod.POST, "/product")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType("application/json").content(asJsonString(new Product("2",20,10))))
                .andReturn();
        MvcResult result_2 = mockMvc.perform(
                        MockMvcRequestBuilders.request(HttpMethod.POST, "/product")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType("application/json").content(asJsonString(new Product("3",20,10))))
                .andReturn();
        MvcResult result_0 = mockMvc.perform(
                        MockMvcRequestBuilders.request(HttpMethod.GET, "/products")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseJson = result_0.getResponse().getContentAsString();
        List<String> jsonArray = read(responseJson, "$");
        assert jsonArray.toString().equals("[{\"productId\":\"2\",\"price\":20.0,\"inventory\":10},{\"productId\":\"3\",\"price\":20.0,\"inventory\":10}]");
    }
    
    // TEST: GET /product
    @Test
    public void getProductToShowBeforeAddingAny() throws Exception {
        MvcResult result_0 = mockMvc.perform(
                        MockMvcRequestBuilders.request(HttpMethod.GET, "/product")
                                .accept(MediaType.APPLICATION_JSON)
                                .param("productId", "9")
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
    }

    // TEST: GET /product
    @Test
    public void getProductToShowAfterAddingOneProduct() throws Exception {
        MvcResult result_1 = mockMvc.perform(
                        MockMvcRequestBuilders.request(HttpMethod.POST, "/product")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType("application/json").content(asJsonString(new Product("1",20,10))))
                .andReturn();
        MvcResult result_0 = mockMvc.perform(
                        MockMvcRequestBuilders.request(HttpMethod.GET, "/product")
                                .accept(MediaType.APPLICATION_JSON)
                                .param("productId", "1")
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseJson = result_0.getResponse().getContentAsString();
        List<String> jsonArray = read(responseJson, "$");
        assert jsonArray.toString().equals("{\"productId\":\"1\",\"price\":20.0,\"inventory\":10}");
    }

    // TEST: POST /product
    @Test
    public void postProductToAddOneNewProduct() throws Exception {
        Object obj = new Product("2",20,10);
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.request(HttpMethod.POST, "/product")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType("application/json").content(asJsonString(obj)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }
    // TEST: POST /product
    @Test
    public void postProductToAddOtherNewProduct() throws Exception {
        Object obj = new Product("2",20,10);
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.request(HttpMethod.POST, "/product")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType("application/json").content(asJsonString(obj)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }
    @Test
    public void postProductToAddThreeNewProduct() throws Exception {
        Object obj = List.of(
                new Product("1",100,5),
                new Product("2",200,2),
                new Product("3",500,0)
        );
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.request(HttpMethod.POST, "/products")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType("application/json").content(asJsonString(obj)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }
    


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
