package com.group14.ecommerce;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group14.ecommerce.Vo.*;

import org.json.JSONString;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
class ECommerceApplicationTests {

	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;

	@BeforeAll
	static void init() throws Exception {
		MockMvc mockMvcS = MockMvcBuilders.standaloneSetup(new ECommerceApplication()).build();
		Object discounts = List.of(
				new CartDiscount(1, 0, 0.1, 1),
				new CartDiscount(2, 0, 0.2, 2),
				new CartDiscount(3, 0, 0.3, 3),
				new CartDiscount(4, 0, 0.4, 4),
				new CartDiscount(5, 0, 0.5, 5),
				new MembershipDiscount(6, 100, 0, 1),
				new MembershipDiscount(7, 200, 0, 2),
				new MembershipDiscount(8, 300, 0, 3)
		);
		mockMvcS.perform(
				MockMvcRequestBuilders.request(HttpMethod.POST, "/discounts")
						.contentType("application/json").content(asJsonString(discounts))
						.accept(MediaType.APPLICATION_JSON))
				.andReturn();
		Object user = new User("1234","123456");
		mockMvcS.perform(
						MockMvcRequestBuilders.request(HttpMethod.POST, "/user/register")
								.contentType("application/json").content(asJsonString(user))
								.accept(MediaType.APPLICATION_JSON))
				.andReturn();
	}
	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	@Test
	public void getProducts() throws Exception {
		MvcResult result = mockMvc.perform(
						MockMvcRequestBuilders.request(HttpMethod.GET, "/products")
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(print())
				.andReturn();
	}

	@Test
	public void login() throws Exception {
		Object obj = new User("1234","123456");
		String str = asJsonString(obj);
		MvcResult result = mockMvc.perform(
			MockMvcRequestBuilders.request(HttpMethod.POST, "/user/login")
				.contentType("application/json").content(str)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.totalSpent").value(100))
			.andDo(print())
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
