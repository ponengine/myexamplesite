package com.mobile.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URL;
import java.util.Collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobile.example.dto.LoginDTO;
import com.mobile.example.dto.UserDTO;
import com.mobile.example.response.BaseResponse;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HomeControllerTest2 {

	@LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

	@Autowired
	ObjectMapper mapper;
    
    @Test
    @DisplayName("Integration authenticate")
    void authenticateTest() throws Exception {
    	LoginDTO login=new LoginDTO("pon3@mail.com", "P@ssw0rd");
    	HttpHeaders headers = new HttpHeaders();
    	headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

    	HttpEntity<LoginDTO> entity = new HttpEntity<>(login, headers);

        ResponseEntity<String> response = restTemplate
        		.postForEntity(new URL("http://localhost:" + port + "/api/mobile/authenticate").toString()
        				, entity, String.class);
        BaseResponse<Object> baseRes = mapper.readValue(response.getBody(), BaseResponse.class);
        System.out.println(">>>>>>>>>>>>>>>>>> "+this.mapper.writeValueAsString(response.getBody()));
        assertEquals("00", baseRes.getStatusCode());
    }
    
    
//    @DisplayName("Integration findUser")
//    void finduserTest() throws Exception {
//    	LoginDTO login=new LoginDTO("pon@mail.com", "P@ssw0rd");
//    	HttpHeaders headers = new HttpHeaders();
//    	headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//    	headers.set("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwb25AbWFpbC5jb20iLCJyb2xlcyI6IlJPTEVfU0lMVkVSIiwiaWF0IjoxNjE1MTE1NTgyLCJleHAiOjE2MTUxMzM1ODJ9.kZzudk7AJ3zMKL38O1mEs-8WF1t7BZAlTfNuHHWwbjs");
//    	HttpEntity<LoginDTO> entity = new HttpEntity<>(login, headers);
//
//        ResponseEntity<String> response = restTemplate
//        		.postForEntity(new URL("http://localhost:" + port + "/api/mobile/userinfo").toString()
//        				, entity, String.class);
//        BaseResponse<Object> baseRes = mapper.readValue(response.getBody(), BaseResponse.class);
//        System.out.println(">>>>>>>>>>>>>>>>>> "+this.mapper.writeValueAsString(response.getBody()));
//        assertEquals("00", baseRes.getStatusCode());
//    }
    
//    @Test
//    @DisplayName("Integration Register")
//    void registerTest() throws Exception {
//    	UserDTO user=new UserDTO("pon3@mail.com", "P@ssw0rd", "15000", "0943219114", "test 1","1100300101010");
//    	HttpHeaders headers = new HttpHeaders();
//    	headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//    	
//    	HttpEntity<UserDTO> entity = new HttpEntity<>(user, headers);
//        System.out.println(">>>>>>>>>>>>>>>>>> "+this.mapper.writeValueAsString(user));
//        ResponseEntity<String> response = restTemplate
//        		.postForEntity(new URL("http://localhost:" + port + "/api/mobile/register").toString()
//        				, entity, String.class);
//        BaseResponse<Object> baseRes = mapper.readValue(response.getBody(), BaseResponse.class);
//        System.out.println(">>>>>>>>>>>>>>>>>> "+this.mapper.writeValueAsString(response.getBody()));
//        assertEquals("00", baseRes.getStatusCode());
//    }
    
//    
//    @DisplayName("Integration RoleTest")
//    void roleTest() throws Exception {
//    	
//    	HttpHeaders headers = new HttpHeaders();
//    	headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//    	headers.set("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwb25AbWFpbC5jb20iLCJyb2xlcyI6IlJPTEVfU0lMVkVSIiwiaWF0IjoxNjE1MTI2NTUwLCJleHAiOjE2MTUxNDQ1NTB9.XUfCFNPA2uPJ288Jxam2hR4Cs-_4ZaYR6NRZSv4J5ac");
//    	
//    	HttpEntity<String> entity = new HttpEntity<>("", headers);
//
//        ResponseEntity<String> response = restTemplate.exchange(new URL("http://localhost:" + port + "/api/mobile/silver").toString(), HttpMethod.GET, entity, String.class);
//        assertEquals("You are type SILVER", response.getBody());
//    }
}
