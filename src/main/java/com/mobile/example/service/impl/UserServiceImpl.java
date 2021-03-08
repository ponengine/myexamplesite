package com.mobile.example.service.impl;


import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mobile.example.config.TokenProvider;
import com.mobile.example.dto.LoginDTO;
import com.mobile.example.dto.UserDTO;
import com.mobile.example.entity.Role;
import com.mobile.example.entity.User;
import com.mobile.example.repository.UserRepository;
import com.mobile.example.response.BaseResponse;
import com.mobile.example.service.RoleService;
import com.mobile.example.service.UserService;
import com.mobile.example.util.UserUtil;

@Service
public class UserServiceImpl implements UserService{

	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	
    public UserServiceImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	private TokenProvider tokenProvider;
	private UserRepository userRepository;
    private BCryptPasswordEncoder bcryptEncoder;
    private RoleService roleService;
    private AuthenticationManager authenticationManager;
    
    @Autowired
    public UserServiceImpl(UserRepository userRepository,BCryptPasswordEncoder bcryptEncoder,RoleService roleService,TokenProvider tokenProvider,AuthenticationManager authenticationManager) {
    	this.userRepository=userRepository;
    	this.bcryptEncoder=bcryptEncoder;
    	this.roleService=roleService;
    	this.tokenProvider=tokenProvider;
    	this.authenticationManager=authenticationManager;
    }
	

    @Override
    public BaseResponse<Object> findUser(String username) {
    	log.debug("Method : "+"findUser");
    	log.debug("Input : "+username);
    	BaseResponse<Object> response = null;
    	User user = null;
    	try {
    		response = new BaseResponse<Object>();
    		user=userRepository.findByUserName(username);
    		log.debug("Output : "+user);
    	log.debug("FindUser : "+user);
    		if(user!=null) {
    			user.setPassword("");
    			user.setPhone(UserUtil.replacePhone(user.getPhone()));
    			response.setData(user);
        		response.setStatusCode("00");
    		}else {
    			response.setMessageError("User not found");
        		response.setStatusCode("07");
    		}
    	log.debug("Status : "+response.getStatusCode());
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
        return response;
    }
    
    @Override
    public BaseResponse<Object> saveUser(UserDTO user) {
    log.debug("Method : "+"saveUser");
    BaseResponse<Object> response =null;
    User userForSave  = null;
    Role role = null;
    int resultSalary=0;
    String error=null;
    try {
     role = new Role();
     response = new BaseResponse<Object>();
     user.setPhone(user.getPhone().replaceAll("[^0-9]+",""));
     error=user.validate();
     if(error.length()>0) {
    	response.setStatusCode("06");
      	response.setMessageError(error);
      	return response; 
     }
     resultSalary=Integer.parseInt(user.getSalary());
     if(resultSalary<15000) {
    	response.setStatusCode("03");
     	response.setMessageError("The amount does not reach the threshold");
     	return response; 
     }
     if(findDuplicateUser(user.getUserName())!=null) {
    	 response.setStatusCode("05");
      	response.setMessageError("this user is duplicate");
      	return response; 
     }
     userForSave = user.transferUser();
     userForSave.setPassword(bcryptEncoder.encode(user.getPassword()));
     userForSave.setReference(UserUtil.setReference(user.getPhone()));
     Set<Role> roleSet = new HashSet<>();
     role = roleService.findByName(UserUtil.setRole(resultSalary));
     roleSet.add(role);
     userForSave.setRoles(roleSet);
     userRepository.save(userForSave);
     response.setStatusCode("00");
    }catch(Exception e) {
    	response.setStatusCode("04");
     	response.setMessageError("error exception");
    	e.printStackTrace();
    }
     return response;
    }
    
    
    public User findDuplicateUser(String username) {
    	
    	User userRes=userRepository.findByUserName(username);
    	log.debug("Duplicate : "+userRes);
    return	userRes;
    }
    @Override
    public BaseResponse<Object> getAuthenticate(LoginDTO loginUser) {
    	Authentication authentication = null;
    	String token = null;
    	BaseResponse<Object> response =null;
    	try {
    		 response = new BaseResponse<Object>();
    		 if(StringUtils.isEmpty(loginUser.getUsername())||StringUtils.isEmpty(loginUser.getPassword())) {
    	        	response.setStatusCode("02");
    	        	response.setMessageError("username and password not empty or null");
    	        	return response; 
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
    	        	return response;
    	        }
    	        response.setData("Bearer "+token);
    	    	response.setStatusCode("00");
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	return response;
    }


	@Override
	public String testresdata(String string) {
	
		return string;
	}

}
