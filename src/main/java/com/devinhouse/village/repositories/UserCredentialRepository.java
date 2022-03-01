package com.devinhouse.village.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.devinhouse.village.model.UserCredential;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredential, Integer> {
	
	@Transactional
    @Query("SELECT u FROM UserCredential u where u.email = :email")
    UserCredential getUserByEmail(String email);
	
}
