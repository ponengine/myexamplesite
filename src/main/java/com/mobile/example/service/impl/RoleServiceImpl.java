package com.mobile.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobile.example.entity.Role;
import com.mobile.example.repository.RolesRepository;
import com.mobile.example.service.RoleService;
@Service
public class RoleServiceImpl implements RoleService{
	
	
    private RolesRepository rolesRepository;
    
    @Autowired
	public  RoleServiceImpl(RolesRepository rolesRepository) {
    	this.rolesRepository=rolesRepository;
	}

    @Override
    public Role findByName(String name) {
        Role role = rolesRepository.findByName(name);
        return role;
    }

}
