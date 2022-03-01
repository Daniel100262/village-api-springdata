package com.devinhouse.village.model;

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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Resident implements Serializable {

	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue
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
	
	@OneToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name = "user_id")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private UserCredential user;
	
	@Transient
	private String role;
	
	public Resident() {
		
	}
	
	public Resident(Integer id, String firstName, String lastName) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public Resident(String firstName, String lastName, Integer age, LocalDate bornDate, BigDecimal income, String cpf,
			UserCredential user) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.bornDate = bornDate;
		this.income = income;
		this.cpf = cpf;
		this.user = user;
	}

	
	
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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
	
	public static final Comparator<Resident> compareByIncome = (Resident r1, Resident r2) -> {
        return r1.getIncome().compareTo(r2.getIncome());
    };
}
