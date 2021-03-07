package com.mobile.example;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobile.example.config.TokenProvider;
import com.mobile.example.controller.HomeController;
import com.mobile.example.dto.LoginDTO;
import com.mobile.example.dto.UserDTO;
import com.mobile.example.repository.UserRepository;
import com.mobile.example.response.BaseResponse;
import com.mobile.example.service.UserService;
import com.mobile.example.service.impl.UserServiceImpl;
import com.mobile.example.util.UserUtil;

@WebMvcTest(HomeController.class)
public class HomeControllerTest {

	@Autowired
	MockMvc mockMvc;


	@Autowired
	ObjectMapper mapper;


	@MockBean
	UserService service;
	
	@MockBean
	UserRepository userRepository;
	
	@MockBean
	TokenProvider tokenProvider;
	
	
	@Test
	public void post_find_user_200() throws Exception {
		
		BaseResponse<Object> response = new BaseResponse<Object>();
		UserDTO user=new UserDTO("pon@mail.com", "", "15000", "0943219114", "test 1","1100300101010");
		response.setStatusCode("00");
		response.setData(user.transferUser());
		
		Mockito.when(service.findUser(user.getUserName())).thenReturn(response);
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/mobile/userinfo")
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").content(this.mapper.writeValueAsBytes(user.transferUser()));
		
		System.out.println(">>>>>>>>>>>>>>>>>> "+this.mapper.writeValueAsString(response));
		
		mockMvc.perform(builder).andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.content().string(this.mapper.writeValueAsString(response)));
	}
	
	@Test
	public void post_authenticate_200() throws Exception {
		
		BaseResponse<Object> response = new BaseResponse<Object>();
		LoginDTO login=new LoginDTO("pon@mail.com", "P@ssw0rd");
		response.setStatusCode("00");
		response.setData(login);
		
		Mockito.when(service.getAuthenticate(login)).thenReturn(response);
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/mobile/authenticate")
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").content(this.mapper.writeValueAsBytes(login));

		System.out.println(">>>>>>>>>>>>>>>>>> "+this.mapper.writeValueAsString(response));
		
		mockMvc.perform(builder).andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.content().string(this.mapper.writeValueAsString(response)));
		
				
	}
	
	@Test
	public void post_register_200() throws Exception {
		
		BaseResponse<Object> response = new BaseResponse<Object>();
		UserDTO user=new UserDTO("pon2@mail.com", "", "15000", "0943219114", "test 1","1100300101010");
		response.setStatusCode("00");
		
		
		Mockito.when(service.saveUser(user)).thenReturn(response);
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/mobile/register")
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").content(this.mapper.writeValueAsBytes(user));
		
		System.out.println(">>>>>>>>>>>>>>>>>> "+this.mapper.writeValueAsString(response));
		
		mockMvc.perform(builder).andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.content().string(this.mapper.writeValueAsString(response)));
	}
	
	

	@Test
	public void post_test() throws Exception {
		
		
		Mockito.when(service.testresdata("Hello world")).thenReturn("Hello world");
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/mobile/test")
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").content("Hello world");

		mockMvc.perform(builder).andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string("Hello world"));
	}
	
	@Test
	public void test_hide_phone() {
		String phone = "0943219111";
		assertEquals("XXXXXX9111", UserUtil.replacePhone(phone));
	}
	
	@Test
	public void test_set_role() {
		int salary = 350000;
		assertEquals("GOLD", UserUtil.setRole(salary));
	}
	
	@Test
	public void test_set_reference() {
		String phone = "0943219111";
		assertEquals("02103079111", UserUtil.setReference(phone));
	}
	

}
