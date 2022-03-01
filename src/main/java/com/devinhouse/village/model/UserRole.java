package com.devinhouse.village.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "roles")
public class UserRole implements GrantedAuthority {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "role_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String roleName;
	
	public UserRole(Integer id, String role) {
		this.id = id;
		this.roleName = role;
	}
	
	public UserRole() {
		
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String role) {
		this.roleName = role;
	}
	@Override
	public String getAuthority() {
		return roleName;
	}
}
