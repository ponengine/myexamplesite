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
import com.mobile.example.service.RoleService;
import com.mobile.example.service.UserService;

@Service
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
    public User findUser(String username) {
        return userRepository.findByUserName(username);
    }
    
    @Override
    public User saveUser(UserDTO user) {

     User userForSave = user.transferUser();
     userForSave.setPassword(bcryptEncoder.encode(user.getPassword()));
//set role by salary
//     Role role = roleService.findByName("USER");
//     Set<Role> roleSet = new HashSet<>();
//     roleSet.add(role);
//
//     if(userForSave.getEmail().split("@")[1].equals("admin.edu")){
//         role = roleService.findByName("ADMIN");
//         roleSet.add(role);
//     }
//
//     userForSave.setRoles(roleSet);
     return userRepository.save(userForSave);
 }

}
