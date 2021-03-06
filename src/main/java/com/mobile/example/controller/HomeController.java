package com.mobile.example.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mobile.example.config.TokenProvider;
import com.mobile.example.dto.LoginDTO;
import com.mobile.example.dto.UserDTO;
import com.mobile.example.response.BaseResponse;
import com.mobile.example.service.UserService;

@RestController
@RequestMapping("/api/mobile/")
public class HomeController {
	
	private AuthenticationManager authenticationManager;
	
	private TokenProvider tokenProvider;
	
	private UserService userService;
	
	@Autowired
	public HomeController(AuthenticationManager authenticationManager,TokenProvider tokenProvider,UserService userService) {
		this.authenticationManager=authenticationManager;
		this.tokenProvider=tokenProvider;
		this.userService=userService;
	}
	
    @PostMapping(value = "/authenticate")
    public ResponseEntity<BaseResponse<String>> generateToken(@RequestBody LoginDTO loginUser) throws AuthenticationException {
    	Authentication authentication = null;
    	String token = null;
    	BaseResponse<String> response =null;
    	try {
        response = new BaseResponse<String>();
        if(StringUtils.isEmpty(loginUser.getUsername())||StringUtils.isEmpty(loginUser.getPassword())) {
        	response.setStatusCode("02");
        	response.setMessageError("username and password not empty or null");
        	return new ResponseEntity<BaseResponse<String>>(response,HttpStatus.OK); 
        }
        authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        token = tokenProvider.generateToken(authentication);
        
        if(StringUtils.isEmpty(token)) {
        	response.setStatusCode("01");
        	response.setMessageError("message not found");
        	return new ResponseEntity<BaseResponse<String>>(response,HttpStatus.OK); 
        }
        response.setData(token);
    	response.setStatusCode("00");
        
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
        return new ResponseEntity<BaseResponse<String>>(response,HttpStatus.OK);
    }
    
    @PostMapping(value="/register")
    public ResponseEntity<BaseResponse> saveUser(@RequestBody UserDTO user){
    	try {
    		userService.saveUser(user);
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
        return new ResponseEntity<BaseResponse>(HttpStatus.OK);
    }
	
    @RequestMapping(value="/userinfo", method = RequestMethod.POST)
    public ResponseEntity<BaseResponse>  userinfo(@RequestBody LoginDTO loginUser){
    	try {
    		userService.findUser(loginUser.getUsername());
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
        return new ResponseEntity<BaseResponse>(HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('GOLD')")
    @GetMapping("/gold")
    public String testGold() {
    	return "You are type GOLD";
    }
    
    @PreAuthorize("hasRole('SILVER')")
    @GetMapping("/silver")
    public String testSilver() {
    	return "You are type SILVER";
    }
    
    @PreAuthorize("hasRole('PLATINUM')")
    @GetMapping("/platinum")
    public String testPlatinum() {
    	return "You are type PLATINUM";
    }
    
    
}
