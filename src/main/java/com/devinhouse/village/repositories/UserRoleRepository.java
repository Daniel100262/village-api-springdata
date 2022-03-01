package com.devinhouse.village.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.devinhouse.village.model.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer>{
	
	@Transactional
    @Query("SELECT ur FROM UserRole ur where ur.roleName = :roleName")
    List<UserRole> getRolesListByRoleName(String roleName);
	
	@Transactional
    @Query("SELECT ur FROM UserRole ur where ur.roleName = :roleName")
    UserRole getRoleByRoleName(String roleName);
	
}
