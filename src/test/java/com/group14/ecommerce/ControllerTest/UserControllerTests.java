package com.group14.ecommerce.ControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group14.ecommerce.Vo.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
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
class UserControllerTests {

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
    public void getUsersToListAllBeforeAddingUser() throws Exception {
        MvcResult result_0 = mockMvc.perform(
                        MockMvcRequestBuilders.request(HttpMethod.GET, "/users")
                                .accept(MediaType.APPLICATION_JSON).content("MASTER_KEY")
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseJson = result_0.getResponse().getContentAsString();
        List<String> jsonArray = read(responseJson, "$");
        assert jsonArray.toString().equals("[]");
    }

    @Test
    public void getUsersToListAllAfterAddingOneUser() throws Exception {
        Object obj = new User("user123","Pass456!");
        MvcResult result_1 = mockMvc.perform(
                        MockMvcRequestBuilders.request(HttpMethod.POST, "/user/register")
                                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(asJsonString(obj))
                )
                .andDo(print())
                .andReturn();

        MvcResult result_0 = mockMvc.perform(
                        MockMvcRequestBuilders.request(HttpMethod.GET, "/users")
                                .accept(MediaType.APPLICATION_JSON).content("MASTER_KEY")
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseJson = result_0.getResponse().getContentAsString();
        List<String> jsonArray = read(responseJson, "$");
        assert jsonArray.toString().equals("[{\"userId\":\"user123\",\"userPassword\":\"Pass456!\",\"totalSpent\":0.0,\"carts\":[],\"membershipTier\":0}]");
    }

    @Test
    public void getUsersToListAllAfterAddingTwoUsers() throws Exception {
        String str_1 = "{\"userId\":\"user123\",\"userPassword\":\"Pass123@\"}";
        MvcResult result_1 = mockMvc.perform(
                        MockMvcRequestBuilders.request(HttpMethod.POST, "/user/register")
                                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(str_1)
                )
                .andDo(print())
                .andReturn();
        String str_2 = "{\"userId\":\"user456\",\"userPassword\":\"Pass456!\"}";
        MvcResult result_2 = mockMvc.perform(
                        MockMvcRequestBuilders.request(HttpMethod.POST, "/user/register")
                                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(str_2)
                )
                .andDo(print())
                .andReturn();
        MvcResult result_0 = mockMvc.perform(
                        MockMvcRequestBuilders.request(HttpMethod.GET, "/users")
                                .accept(MediaType.APPLICATION_JSON).content("MASTER_KEY")
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseJson = result_0.getResponse().getContentAsString();
        List<String> jsonArray = read(responseJson, "$");
        assert jsonArray.toString().equals("[{\"userId\":\"user123\",\"userPassword\":\"Pass123@\",\"totalSpent\":0.0,\"carts\":[],\"membershipTier\":0},{\"userId\":\"user456\",\"userPassword\":\"Pass456!\",\"totalSpent\":0.0,\"carts\":[],\"membershipTier\":0}]");
    }

    @Test
    public void postUserLoginToUnregisteredID() throws Exception {
        String str = "{\"userId\":\"user123\",\"userPassword\":\"pass123\"}";
        MvcResult result_0 = mockMvc.perform(
                        MockMvcRequestBuilders.request(HttpMethod.POST, "/user/login")
                                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(str)
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andReturn();
    }

    @Test
    public void postUserLoginToRegisteredID() throws Exception {
        String str = "{\"userId\":\"user123\",\"userPassword\":\"Pass123!\"}";
        MvcResult result_1 = mockMvc.perform(
                        MockMvcRequestBuilders.request(HttpMethod.POST, "/user/register")
                                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(str)
                )
                .andDo(print())
                .andReturn();
        MvcResult result_0 = mockMvc.perform(
                        MockMvcRequestBuilders.request(HttpMethod.POST, "/user/login")
                                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(str)
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void postUserLoginToRegisteredIDWithWrongPassword() throws Exception {
        String str_1 = "{\"userId\":\"user123\",\"userPassword\":\"Pass456!\"}";
        MvcResult result_1 = mockMvc.perform(
                        MockMvcRequestBuilders.request(HttpMethod.POST, "/user/register")
                                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(str_1)
                )
                .andDo(print())
                .andReturn();
        String str_0 = "{\"userId\":\"user123\",\"userPassword\":\"pass123\"}";
        MvcResult result_0 = mockMvc.perform(
                        MockMvcRequestBuilders.request(HttpMethod.POST, "/user/login")
                                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(str_0)
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andReturn();
    }

    @Test
    public void postUserRegisterToUnregisteredIDWithBadPassword() throws Exception {
        String str = "{\"userId\":\"user123\",\"userPassword\":\"pass123\"}";
        MvcResult result_0 = mockMvc.perform(
                        MockMvcRequestBuilders.request(HttpMethod.POST, "/user/register")
                                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(str)
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
    }

    @Test
    public void postUserRegisterToRegisteredID() throws Exception {
        String str_1 = "{\"userId\":\"user123\",\"userPassword\":\"pass456\"}";
        MvcResult result_1 = mockMvc.perform(
                        MockMvcRequestBuilders.request(HttpMethod.POST, "/user/register")
                                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(str_1)
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
        String str_0 = "{\"userId\":\"user123\",\"userPassword\":\"pass123\"}";
        MvcResult result_0 = mockMvc.perform(
                        MockMvcRequestBuilders.request(HttpMethod.POST, "/user/register")
                                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(str_0)
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
        
    }

    @Test
    public void postUserRegisterToUnregisteredIDWithGoodPassword() throws Exception {
        String str = "{\"userId\":\"user123\",\"userPassword\":\"Pass456!\"}";
        MvcResult result_1 = mockMvc.perform(
                        MockMvcRequestBuilders.request(HttpMethod.POST, "/user/register")
                                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(str)
                )
                .andDo(print())
                .andReturn();
        MvcResult result_0 = mockMvc.perform(
                        MockMvcRequestBuilders.request(HttpMethod.POST, "/user/login")
                                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(str)
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value("user123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userPassword").value("Pass456!"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalSpent").value("0.0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.carts").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.membershipTier").value("0"))
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
