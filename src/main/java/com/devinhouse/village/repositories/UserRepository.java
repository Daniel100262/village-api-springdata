package com.devinhouse.village.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.devinhouse.village.model.dao.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	@Transactional
    @Modifying
    @Query("SELECT new com.devinhouse.village.model.dao.User FROM User u where u.email = :email")
    User getUserByEmail(String email);
	
}
