package com.devinhouse.village.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.devinhouse.village.exception.DuplicatedResidentException;
import com.devinhouse.village.exception.NullResidentException;
import com.devinhouse.village.exception.NullUserException;
import com.devinhouse.village.exception.NullVillageBudgetException;
import com.devinhouse.village.exception.ResidentNotFoundException;
import com.devinhouse.village.model.Resident;
import com.devinhouse.village.model.transport.VillageReportDTO;
import com.devinhouse.village.repositories.ResidentRepository;

@Service
public class ResidentService {

	private ResidentRepository residentRepository;
	
	UserService userService;
	
	@Value("${village.budget}")
	private Float budgetOfVillage;

	@Autowired
	public ResidentService(ResidentRepository residentRepository, UserService userService, @Value("${village.budget}")Float budgetOfVillage) {
		this.residentRepository = residentRepository;
		this.userService = userService;
		this.budgetOfVillage = budgetOfVillage;
	}
	
	public ResidentService() {
		
	}

	public Float getBudgetOfVillage() {
		return budgetOfVillage;

	}

	public void setBudgetOfVillage(Float budgetOfVillage) {
		this.budgetOfVillage = budgetOfVillage;
	}

	public ResidentService(UserService userService, ResidentRepository residentRepository) {
		this.userService = userService;
		this.residentRepository = residentRepository;
	}
	
	public List<Resident> getAllResidents() {
		return this.residentRepository.findAllFiltered();
	}

	public BigDecimal getVillageCosts(){
		List<Resident> residents = this.residentRepository.findAll();
		BigDecimal costsSum = BigDecimal.valueOf(0);
		for (Resident resident : residents) {
			costsSum = costsSum.add(resident.getIncome());
		}
		return costsSum;
	}

	public Resident getResidentById(Integer id) {
		if(residentRepository.findByIdFiltered(id).size() < 1) {
			throw new ResidentNotFoundException("Não existe nenhum morador com o ID "+id);
		} else {
			return residentRepository.findByIdFiltered(id).get(0);
		}
	}

	public List<Resident> getResidentByName(String name) {
		return residentRepository.getResidentsByName(name);
	}

	public Resident create(Resident resident) {

		if (!isResidentAlreadyOnList(this.residentRepository.findAll(), resident)) {
			userService.create(resident);
		} else {
			throw new DuplicatedResidentException("O morador " + resident.getFirstName() + " " + resident.getLastName()
					+ " com CPF " + resident.getCpf() + " já existe na lista!");
		}

		resident.setAge(calculateAge(resident.getBornDate(), LocalDate.now()));

		try {
			resident = this.residentRepository.save(resident);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resident;
	}
	
	private int calculateAge(LocalDate bornDate, LocalDate currentDate) {
        if ((bornDate != null) && (currentDate != null)) {
            return Period.between(bornDate, currentDate).getYears();
        } else {
            return 0;
        }
        
    }

	private boolean isResidentAlreadyOnList(List<Resident> residents, Resident resident) {
		return residents.stream().anyMatch(residentInList -> residentInList.getCpf().contentEquals(resident.getCpf()));
	}

	public Boolean delete(Integer id) {
		if (id == null) {
			throw new NullResidentException("O morador que você tentou apagar está nulo!");
		}
		
		Boolean sucessfullDeleted = false;
		
		if(this.residentRepository.existsById(id)) {
			this.residentRepository.deleteById(id);
			sucessfullDeleted = true;
			return sucessfullDeleted;
		} else {
			return sucessfullDeleted;
		}
		
		
	}
	


	public List<Resident> getResidentByAge(Integer age) {
		
		if(age.equals(null)) {
			throw new IllegalArgumentException("A idade está nula!");
		}
		
		return this.residentRepository.getResidentsByAge(age);
	}

	public List<Resident> getResidentByMonth(Integer month) {
		if(month.equals(null)) {
			throw new IllegalArgumentException("O mês está nulo!");
		}
		return this.residentRepository.getResidentsByMonth(month);
	}

	public VillageReportDTO genereteReport(String emailDestination) {
		List<Resident> residents = this.residentRepository.findAll();
	
	        final BigDecimal villageTotalCost = residents.stream().reduce(
	                BigDecimal.ZERO,(accumulator, resident) -> resident.getIncome().add((accumulator)),
	                BigDecimal::add
	        );
	        final Float cost = budgetOfVillage - villageTotalCost.floatValue();
	        final Resident villagerWithHigherCost = residents.stream().max(Resident.compareByIncome).orElse(null);


	        final String villagerName = String.format("%s %s", villagerWithHigherCost.getFirstName(),villagerWithHigherCost.getLastName());
	        return new VillageReportDTO(cost, budgetOfVillage, villageTotalCost, villagerName, emailDestination);
	
	}
	
	public VillageReportDTO genereteReport() {
		List<Resident> residents = this.residentRepository.findAll();
	
	        final BigDecimal villageTotalCost = residents.stream().reduce(
	                BigDecimal.ZERO,(accumulator, resident) -> resident.getIncome().add((accumulator)),
	                BigDecimal::add
	        );
	        final Float cost = budgetOfVillage - villageTotalCost.floatValue();
	        final Resident villagerWithHigherCost = residents.stream().max(Resident.compareByIncome).orElse(null);


	        final String villagerName = String.format("%s %s", villagerWithHigherCost.getFirstName(),villagerWithHigherCost.getLastName());
	        return new VillageReportDTO(cost, budgetOfVillage, villageTotalCost, villagerName);
	
	}

}
