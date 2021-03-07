package com.mobile.example.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.mobile.example.entity.User;
import com.mobile.example.repository.UserRepository;

public class UserDetailsServiceImp implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
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

}
