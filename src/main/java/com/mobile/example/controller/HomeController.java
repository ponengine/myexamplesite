package com.mobile.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
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
@RequestMapping("/api/mobile")
public class HomeController {
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	private UserService userService;
	
	@Autowired
	public HomeController(UserService userService) {
		
		this.userService=userService;
	}
	
    @PostMapping(value = "/authenticate")
    public ResponseEntity<BaseResponse<Object>> generateToken(@RequestBody LoginDTO loginUser) throws AuthenticationException {
    	log.debug("Method : "+"generateToken");

    	BaseResponse<Object> response =null;
    	try {
        log.debug("USERNAME AUTHEN : "+loginUser.getUsername());
        response=userService.getAuthenticate(loginUser);
    	log.debug("RESULT : "+response.getStatusCode());
    	}catch(Exception e) {
    		e.printStackTrace();
    		log.error("ERROR : "+e.getMessage());
    	}
        return new ResponseEntity<BaseResponse<Object>>(response,HttpStatus.OK);
    }
    
    @PostMapping(value="/register")
    public ResponseEntity<BaseResponse<Object>> saveUser(@RequestBody UserDTO user){
    	log.debug("Method : "+"saveUser");
    	BaseResponse<Object> res=null;
    	try {
    		res=userService.saveUser(user);
    	}catch(Exception e) {
    		log.error("ERROR : "+e.getMessage());
    		e.printStackTrace();
    	}
        return new ResponseEntity<BaseResponse<Object>>(res,HttpStatus.OK);
    }

    @RequestMapping(value="/userinfo", method = RequestMethod.POST)
    public ResponseEntity<BaseResponse<Object>>  userinfo(@RequestBody LoginDTO loginUser){
    	log.debug("Method : "+"userinfo");
    	BaseResponse<Object> res=null;
    	try {
    		res=userService.findUser(loginUser.getUsername());
    	}catch(Exception e) {
    		log.error("ERROR : "+e.getMessage());
    		e.printStackTrace();
    	}
        return new ResponseEntity<BaseResponse<Object>>(res,HttpStatus.OK);
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
    
    @RequestMapping(value="/test", method = RequestMethod.POST)
    public String  test(){
    	
    	return userService.testresdata("Hello world");
    }
 
    
    
}
