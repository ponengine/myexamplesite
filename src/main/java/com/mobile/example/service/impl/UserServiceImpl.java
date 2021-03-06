package com.mobile.example.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mobile.example.dto.UserDTO;
import com.mobile.example.entity.Role;
import com.mobile.example.entity.User;
import com.mobile.example.repository.UserRepository;
import com.mobile.example.response.BaseResponse;
import com.mobile.example.service.RoleService;
import com.mobile.example.service.UserService;

@Service(value ="userService")
public class UserServiceImpl implements UserDetailsService,UserService{

    private UserRepository userRepository;
    private BCryptPasswordEncoder bcryptEncoder;
    private RoleService roleService;
    
    @Autowired
    public UserServiceImpl(UserRepository userRepository,BCryptPasswordEncoder bcryptEncoder,RoleService roleService) {
    	this.userRepository=userRepository;
    	this.bcryptEncoder=bcryptEncoder;
    	this.roleService=roleService;
    }
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 User user = userRepository.findByUserName(username);
	        if(user == null){
	            throw new UsernameNotFoundException("Invalid username or password.");
	        }
	        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), getAuthority(user));
	}
    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    }
    @Override
    public BaseResponse<Object> findUser(String username) {
    	BaseResponse<Object> response = null;
    	try {
    		response = new BaseResponse<Object>();
    		response.setStatusCode("00");
    		response.setData(userRepository.findByUserName(username));
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
        return response;
    }
    
    @Override
    public BaseResponse<Object> saveUser(UserDTO user) {
    BaseResponse<Object> response =null;
    User userForSave  = null;
    Role role = null;
    int resultSalary=0;
    try {
     role = new Role();
     response = new BaseResponse<Object>();
     resultSalary=Integer.parseInt(user.getSalary());
     if(resultSalary<15000) {
    	response.setStatusCode("03");
     	response.setMessageError("The amount does not reach the threshold");
     	return response; 
     }
     userForSave = user.transferUser();
     userForSave.setPassword(bcryptEncoder.encode(user.getPassword()));
     Set<Role> roleSet = new HashSet<>();
     role = roleService.findByName(setRole(resultSalary));
     roleSet.add(role);
     userForSave.setRoles(roleSet);
     userRepository.save(userForSave);
    }catch(Exception e) {
    	response.setStatusCode("04");
     	response.setMessageError("Type error");
    	e.printStackTrace();
    }
     return response;
    }
    
    public String setRole(int nSalary) {
    	
    	String role=null;;
    	if(nSalary>50000) {
    		role="PLATINUM";
    	}else if(nSalary>=30000 || nSalary<=50000) {
    		role="GOLD";
    	}else if(nSalary<30000) {
    		role="SILVER";
    	}
    	return role;
    }
    
//    public String setReference(String phone) {
//    	String 
//    }

}
