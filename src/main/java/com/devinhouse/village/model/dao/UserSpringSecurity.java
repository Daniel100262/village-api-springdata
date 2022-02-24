package com.devinhouse.village.model.dao;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserSpringSecurity implements UserDetails{

	private static final long serialVersionUID = 1L;
	
	private String email;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;

	public UserSpringSecurity(String email, String password, Collection<? extends GrantedAuthority> authorities) {
		
		System.out.println("Passou no construtor ruim ##################");
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}
	
//	public UserSpringSecurity(String email, String password, Set<String>  authorities) {
//		this.email = email;
//		this.password = password;
//		this.authorities = authorities.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toSet());
//	}

	public UserSpringSecurity(String email, String password, List<UserRole> userRoles) {
		this.email = email;
		this.password = password;
		//this.authorities = userRoles;
		System.out.println("Role --> "+userRoles.get(0).getAuthority());
		System.out.println("Passou no user spring security ##################");
		this.authorities = userRoles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getAuthority())).collect(Collectors.toSet());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
}
