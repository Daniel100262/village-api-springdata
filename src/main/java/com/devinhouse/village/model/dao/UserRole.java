package com.devinhouse.village.model.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.security.core.GrantedAuthority;

@Entity
public class UserRole implements GrantedAuthority{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Integer id;
	private String role;
	
	@ManyToOne
	@JoinColumn(name = "userCredential_id")
	private UserCredential userCredential;
	
	public UserRole(Integer id, String role) {
		this.id = id;
		this.role = role;
	}
	
	public UserRole() {
		
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String getAuthority() {
		return role;
	}
}
