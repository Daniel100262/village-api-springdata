package com.devinhouse.village.model.transport;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class UserDTO {

	private String email;
	private String password;
	private Set<String> roles = new HashSet<String>();

	public UserDTO(String email, String password, Set<String> roles) {
		this.email = email;
		this.password = password;
		this.roles = roles;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserDTO(String email) {
		this.email = email;
	}

	public UserDTO(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDTO other = (UserDTO) obj;
		return Objects.equals(email, other.email);
	}

}
