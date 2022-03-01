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

import com.devinhouse.village.exception.DuplicatedResidentException;
import com.devinhouse.village.exception.NullUserException;
import com.devinhouse.village.model.Resident;
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
		
		try {
			this.residentService.create(resident);
		} catch (DuplicatedResidentException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		} catch (NullUserException e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro desconhecido!");
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body("Cadastrado com sucesso!");
	
	}
	
	
	@PostMapping("/delete/{id}")
	public ResponseEntity<String> deleteResident(@PathVariable("id") Integer id){
		
		
		Boolean response = this.residentService.delete(id);
		
		if (response == false) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falha ao remover o morador!");
		}
		
		return ResponseEntity.status(HttpStatus.OK).body("Morador removido com sucesso!");
	}
}
