package com.devinhouse.village.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.devinhouse.village.model.dao.UserCredential;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredential, Integer> {
	
	@Transactional
    //@Modifying
    //@Query("SELECT UserCredential(u.id, u.email, u.userRoles) FROM UserCredential u where u.email = :email")
    @Query("SELECT u FROM UserCredential u where u.email = :email")
    UserCredential getUserByEmail(String email);
	
}
