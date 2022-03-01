package com.devinhouse.village.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import com.devinhouse.village.exception.InvalidCredentialPropertyException;
import com.devinhouse.village.exception.NullUserException;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class UserCredential implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	private String email;
	private String password;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinTable(
			name="users_role",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
			)
    @JsonIgnore
	private List<UserRole> userRoles = new ArrayList<>();
	
	@OneToOne(mappedBy = "user")
	@JsonIgnore
	private Resident resident;
	
	public UserCredential() {
		
	}

	public UserCredential(String email, String password, List<UserRole> userRoles) {
		this.email = email;
		this.password = password;
		this.userRoles = userRoles;
	}
	
	public UserCredential(Integer id, String email, List<UserRole> userRoles, Resident resident) {
		this.id = id;
		this.email = email;
		this.userRoles = userRoles;
		this.resident = resident;
	}
	
	public Resident getResident() {
		return resident;
	}

	public void setResident(Resident resident) {
		this.resident = resident;
	}


	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserCredential(String email) {
		this.email = email;
	}

	public UserCredential(String email, String password) {
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
		UserCredential other = (UserCredential) obj;
		return Objects.equals(email, other.email);
	}

	public Boolean isValid() {
		if(equals(null)) {
			throw new NullUserException("Usuário nulo ou não informado!");
		}
		
		if(getEmail().equals(null) || getEmail().length() < 1) {
			throw new InvalidCredentialPropertyException("A propriedade \"Email \"está nula ou não foi preenchida corretamente!");
		}
		
		if(getPassword().equals(null) || getPassword().length() < 1) {
			throw new InvalidCredentialPropertyException("A propriedade \"Email \"está nula ou não foi preenchida corretamente!");
		}
		
		if(getUserRoles().equals(null) || getUserRoles().size() < 1) {
			
		}
		
		
		return true;
	}

}
