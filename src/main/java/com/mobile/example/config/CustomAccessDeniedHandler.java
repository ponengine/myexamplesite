/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobile.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 *
 * @author sasanana
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler{

    @Override
    public void handle(HttpServletRequest hsr, HttpServletResponse hsr1, AccessDeniedException ade) throws IOException, ServletException {
        ObjectMapper objectMapper = new ObjectMapper();
        hsr1.setContentType("application/json");
        Map<String, Object> data = new HashMap<>();
        data.put(
          "statusCode", 
          "403");
        data.put(
          "messageError", 
          "AccessDenied");

        hsr1.getOutputStream()
          .println(objectMapper.writeValueAsString(data));
    }
    
}
