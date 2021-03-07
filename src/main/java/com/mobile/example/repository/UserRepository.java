package com.mobile.example.repository;

import org.springframework.data.repository.CrudRepository;

import com.mobile.example.entity.User;

public interface UserRepository extends CrudRepository<User, Long>{
	User findByUserName(String username);
	User findByUserNameAndPhone(String UserNmae,String Phone);
}
