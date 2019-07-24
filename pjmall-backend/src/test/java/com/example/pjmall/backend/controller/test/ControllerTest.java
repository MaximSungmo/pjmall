package com.example.pjmall.backend.controller.test;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import com.example.pjmall.backend.domain.User;
import com.google.gson.Gson;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {
    
	@Autowired
    MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Autowired
	private FilterChainProxy springSecurityFilterChain;
	
	private String accessToken; 
	
	@Before	
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.
			webAppContextSetup(webApplicationContext)
			.addFilters(springSecurityFilterChain)
			.build();
		
		if(accessToken != null) {
			return;
		}
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>(); 
		params.add("grant_type", "password");
		params.add("client_id", "pjmall");
		
		params.add("username", "user1");
		params.add("password", "5678");
		
		params.add("scope", "MALL_USER");
		
		ResultActions resultActions =
		mockMvc
			.perform(post("/oauth/token")
			.params(params)
			.with(httpBasic("pjmall", "1234"))
			.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk());
		
		String resultString = resultActions.andReturn().getResponse().getContentAsString();
		JacksonJsonParser jsonParser = new JacksonJsonParser();
		accessToken = jsonParser.parseMap(resultString).get("access_token").toString();
	}
	
	@Test
	public void testHelloUnauthorized() throws Exception {
		mockMvc
			.perform(get("/hello"))
			.andDo(print())
			.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void testGetAuthorized() throws Exception {
		mockMvc
			.perform(
				get("/hello")
				.header("Authorization", "Bearer " + accessToken))
			.andDo(print())
			.andExpect(status().isOk());
	}
	
	@Test
	public void testPostAuthorized() throws Exception {
		User user = new User(1L, "kickscar@gmail.com", "Hello123");
		mockMvc
			.perform(
				post("/hello2")
				.header("Authorization", "Bearer " + accessToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(user)))
			.andDo(print())
			.andExpect(status().isOk());
	}
}