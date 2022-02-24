package com.devinhouse.village.controller.rest;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devinhouse.village.model.dao.InsertResidentResponseType;
import com.devinhouse.village.model.dao.Resident;
import com.devinhouse.village.service.ResidentService;

@RestController
@RequestMapping("/resident")
public class ResidentRest {
	
	@Autowired
	private ResidentService residentService;
	
	
	@GetMapping("/list-all")
	public List<Resident> listAvengers() {
		return residentService.listResidents();
	}
	
	@GetMapping("/listbyid/{id}")
	public Resident getResident(@PathVariable("id") Integer id) {
		return residentService.getResidentById(id);
	}
	
	@GetMapping("/listbyname")
	public List<Resident> getResidentByName(@RequestParam("name") String name) {
		System.out.println("Procurando por: "+name);
		return residentService.getResidentByName(name);
	}
	
	@GetMapping("/listbyage")
	public List<Resident> getResidentByAge(@RequestParam("ageGreaterThanOrEqualTo") Integer age) {
		return residentService.getResidentByAge(age);
	}
	
	@GetMapping("/listbymonth")
	public List<Resident> getResidentByMonth(@RequestParam("month") Integer month) {
		return residentService.getResidentByMonth(month);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/add")
	public ResponseEntity<String> addResident(@RequestBody Resident resident) throws SQLException{
		
		Integer sucessfullCreated = this.residentService.create(resident);
		
		if (sucessfullCreated == InsertResidentResponseType.DUPLICATED.getResponseCode()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Cadastro duplicado!");
		} 
		
		else if (sucessfullCreated == InsertResidentResponseType.SUCCESS_ADDED.getResponseCode()) {
			return ResponseEntity.status(HttpStatus.CREATED).body("Cadastrado com sucesso!");
		} 
		
		else if (sucessfullCreated.equals(InsertResidentResponseType.INVALID_DATA.getResponseCode())) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Dados invalidos!");
		} 	
		
		else if (sucessfullCreated.equals(InsertResidentResponseType.INVALID_PATTERN.getResponseCode())) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("A senha informada não esta em conformidade com os padrões aceitos!");
		}
		
		else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro desconhecido!");
		}
		
	}
	
	
	@PostMapping("/delete/{id}")
	public ResponseEntity<String> deleteResident(@PathVariable("id") Integer id){
		
		Boolean response = this.residentService.delete(id);
		
		if (response == false) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Morador removido com sucesso!");
		}
		
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}