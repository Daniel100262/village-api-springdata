package com.devinhouse.village.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Resident implements Serializable {

	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue
	private Integer id;
    
	private String firstName;
	private String lastName;
	private Integer age;
	private LocalDate bornDate;
	private BigDecimal income;
	private String cpf;
	private String password;
	private String email;
	
	@OneToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name = "user_id")
	private UserCredential user;	
	
	public Resident() {
		
	}
	
	public Resident(Integer id, String firstName, String lastName) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	public UserCredential getUser() {
		return user;
	}
	public void setUser(UserCredential user) {
		this.user = user;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public LocalDate getBornDate() {
		return bornDate;
	}
	public void setBornDate(LocalDate bornDate) {
		this.bornDate = bornDate;
	}
	public BigDecimal getIncome() {
		return income;
	}
	public void setIncome(BigDecimal income) {
		this.income = income;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public static final Comparator<Resident> compareByIncome = (Resident r1, Resident r2) -> {
        return r1.getIncome().compareTo(r2.getIncome());
    };
}
