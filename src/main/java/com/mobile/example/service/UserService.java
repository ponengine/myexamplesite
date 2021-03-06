package com.mobile.example.service;

import com.mobile.example.dto.UserDTO;
import com.mobile.example.entity.User;

public interface UserService {

	User findUser(String username);

	User saveUser(UserDTO user);

}
