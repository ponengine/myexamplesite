package com.mobile.example.service;

import com.mobile.example.dto.LoginDTO;
import com.mobile.example.dto.UserDTO;
import com.mobile.example.entity.User;
import com.mobile.example.response.BaseResponse;

public interface UserService {

	BaseResponse<Object> findUser(String username);

	BaseResponse<Object> saveUser(UserDTO user);

	BaseResponse<Object> getAuthenticate(LoginDTO loginUser);

	String testresdata(String string);

}
