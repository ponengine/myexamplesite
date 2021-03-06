package com.mobile.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter{
	    @Resource(name = "userService")
	    private UserDetailsService userDetailsService;

	    
	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http.cors().and().csrf().disable() 
	                .authorizeRequests()
	                .antMatchers("/api/mobile/authenticate", "/api/mobile/register").permitAll()
	                .anyRequest().authenticated()
	                .and()
	                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
	    }
	    
	    @Bean
	    public BCryptPasswordEncoder encoder(){
	        return new BCryptPasswordEncoder();
	    }
	    
	    @Override
	    @Bean
	    public AuthenticationManager authenticationManagerBean() throws Exception {
	        return super.authenticationManagerBean();
	    }

	    @Bean
	    public JwtAuthenticationFilter authenticationTokenFilterBean() throws Exception {
	        return new JwtAuthenticationFilter();
	    }
}
