package com.devinhouse.village.model.transport;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ResidentDTO {
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer id;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String firstName; 
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String lastName;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer age;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private LocalDate bornDate;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private BigDecimal income;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String cpf;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String password;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String email;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer userid;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Set<String> roles;

	public ResidentDTO() {
		
	}


	public ResidentDTO(Integer id, String firstName, String lastName, Integer age, LocalDate bornDate, BigDecimal income,
			String cpf, String password, String email) {

		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.bornDate = bornDate;
		this.income = income;
		this.cpf = cpf;
		this.password = password;
		this.email = email;
	}
	
	public ResidentDTO(Integer id, String firstName, String lastName, Integer age, LocalDate bornDate, BigDecimal income,
			String cpf, String email) {

		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.bornDate = bornDate;
		this.income = income;
		this.cpf = cpf;
		this.email = email;
	}
	
	public ResidentDTO(Integer id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
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


	public Integer getUserid() {
		return userid;
	}


	public void setUserid(Integer userid) {
		this.userid = userid;
	}


	public Set<String> getRoles() {
		return roles;
	}


	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}	
	
	
	public static final Comparator<ResidentDTO> compareByIncome = (ResidentDTO r1, ResidentDTO r2) -> {
        return r1.getIncome().compareTo(r2.getIncome());
    };
	
	
}
