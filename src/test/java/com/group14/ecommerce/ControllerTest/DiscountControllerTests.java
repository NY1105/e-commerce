package com.group14.ecommerce.ControllerTest;

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
class DiscountControllerTests {

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
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void addNewDiscount() throws Exception {
        Object obj = List.of(
                new CartDiscount(0.1, 0, 1),
				new CartDiscount(0.2, 0, 2),
				new CartDiscount(0.3, 0, 3),
				new CartDiscount(0.4, 0, 4),
				new CartDiscount(0.5, 0, 5),
				new MembershipDiscount(0, 100, 1),
				new MembershipDiscount(0, 200, 2),
				new MembershipDiscount(0, 300, 3)
        );
        MvcResult result_0 = mockMvc.perform(
                        MockMvcRequestBuilders.request(HttpMethod.PUT, "/discounts")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType("application/json").content(asJsonString(obj)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void getDiscounts() throws Exception {
        addNewDiscount();
        MvcResult result_0 = mockMvc.perform(
                        MockMvcRequestBuilders.request(HttpMethod.GET, "/discounts")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType("application/json"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseJson = result_0.getResponse().getContentAsString();
        List<String> jsonArray = read(responseJson, "$");
        assert jsonArray.toString().equals("[{\"discountCode\":1,\"discountPercentOff\":0.1,\"discountAmount\":0.0},{\"discountCode\":2,\"discountPercentOff\":0.2,\"discountAmount\":0.0},{\"discountCode\":3,\"discountPercentOff\":0.3,\"discountAmount\":0.0},{\"discountCode\":4,\"discountPercentOff\":0.4,\"discountAmount\":0.0},{\"discountCode\":5,\"discountPercentOff\":0.5,\"discountAmount\":0.0},{\"discountCode\":6,\"discountPercentOff\":0.0,\"discountAmount\":100.0},{\"discountCode\":7,\"discountPercentOff\":0.0,\"discountAmount\":200.0},{\"discountCode\":8,\"discountPercentOff\":0.0,\"discountAmount\":300.0}]");
    }



    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
