package com.mobile.example.repository;

import org.springframework.data.repository.CrudRepository;

import com.mobile.example.entity.Role;

public interface RolesRepository extends CrudRepository<Role, Long>{
	Role findByName(String name);
}
