package com.devinhouse.village.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devinhouse.village.model.UserRole;
import com.devinhouse.village.repositories.UserRoleRepository;

@Service
public class UserRoleService {
	
	private UserRoleRepository userRoleRepository;
	
	@Autowired
	public UserRoleService(UserRoleRepository userRoleRepository) {
		super();
		this.userRoleRepository = userRoleRepository;
	}

	public List<UserRole> getRolesListByRoleName(String roleName) {
		return userRoleRepository.getRolesListByRoleName(roleName);
	}
	
	public UserRole getRoleByRoleName(String roleName) {
		return userRoleRepository.getRoleByRoleName(roleName);
	}	
}
