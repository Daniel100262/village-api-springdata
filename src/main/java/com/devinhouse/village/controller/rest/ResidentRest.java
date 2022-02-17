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
import com.devinhouse.village.model.transport.ResidentDTO;
import com.devinhouse.village.service.ResidentService;

@RestController
@RequestMapping("/resident")
public class ResidentRest {
	
	@Autowired
	private ResidentService residentService;
	
	
	@GetMapping("/list-all")
	public List<ResidentDTO> listAvengers() throws SQLException{
		return residentService.listResidents();
	}
	
	@GetMapping("/listbyid/{id}")
	public ResidentDTO getResident(@PathVariable("id") Integer id) throws SQLException{
		return residentService.getResidentById(id);
	}
	
	@GetMapping("/listbyname")
	public List<ResidentDTO> getResidentByName(@RequestParam("name") String name) throws SQLException{
		System.out.println("Procurando por: "+name);
		return residentService.getResidentByName(name);
	}
	
	@GetMapping("/listbyage")
	public List<ResidentDTO> getResidentByAge(@RequestParam("ageGreaterThanOrEqualTo") Integer age) throws SQLException{
		return residentService.getResidentByAge(age);
	}
	
	@GetMapping("/listbymonth")
	public List<ResidentDTO> getResidentByMonth(@RequestParam("month") Integer month) throws SQLException{
		return residentService.getResidentByMonth(month);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/add")
	public ResponseEntity<String> addResident(@RequestBody ResidentDTO resident) throws SQLException{
		
		Integer response = this.residentService.create(resident);
		
		if (response == InsertResidentResponseType.DUPLICATED.getResponseCode()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Cadastro duplicado!");
		} 
		
		else if (response == InsertResidentResponseType.SUCCESS_ADDED.getResponseCode()) {
			return ResponseEntity.status(HttpStatus.CREATED).body("Cadastrado com sucesso!");
		} 
		
		else if (response.equals(InsertResidentResponseType.INVALID_DATA.getResponseCode())) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Dados invalidos!");
		} 	
		
		else if (response.equals(InsertResidentResponseType.INVALID_PATTERN.getResponseCode())) {
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
