package com.devinhouse.village.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.devinhouse.village.exception.NullResidentException;
import com.devinhouse.village.model.InsertResidentResponseType;
import com.devinhouse.village.model.Resident;
import com.devinhouse.village.model.transport.VillageReportDTO;
import com.devinhouse.village.repositories.ResidentRepository;
import com.devinhouse.village.repositories.UserCredentialRepository;

@Service
public class ResidentService {
	
	private UserCredentialRepository userCredentialRepository;
	
	private ResidentRepository residentRepository;
	
	UserService userService;
	
	@Value("${village.budget}")
	private Float budgetOfVillage;

	public ResidentService(UserService userService, UserCredentialRepository userCredentialRepository, ResidentRepository residentRepository) {
		this.userService = userService;
		this.userCredentialRepository = userCredentialRepository;
		this.residentRepository = residentRepository;
	}

	public ResidentRepository getResident() {
		return residentRepository;
	}

	
	public void setResident(ResidentRepository residentRepository) {
		this.residentRepository = residentRepository;
	}
	
	public List<Resident> listResidents() {
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
		return residentRepository.findByIdFiltered(id).get(0);
	}

	public List<Resident> getResidentByName(String name) {
		return residentRepository.getResidentsByName(name);
	}

	public Integer create(Resident resident) {
		
		Integer returnCode = InsertResidentResponseType.UNKNOW_ERROR.getResponseCode();
		
		if (resident == null) {
			
			returnCode = InsertResidentResponseType.INVALID_DATA.getResponseCode();
			throw new IllegalArgumentException("O morador está nulo!");
			
		} else if (isResidentAlreadyOnList(this.residentRepository.findAll(), resident)) {
			
			throw new IllegalArgumentException("O morador já existe na lista!");
			
		} else if (resident.getUser().equals(null)) {
			returnCode = InsertResidentResponseType.INVALID_DATA.getResponseCode();
			throw new IllegalArgumentException("O morador contém um usuário inválido!");
		}
		
		if(resident.getUser().isValid()) {
			userService.create(resident); //TODO: arrumar userService
		}
		
		resident.setAge(calculateAge(resident.getBornDate(), LocalDate.now()));
		
		try {
			resident = this.residentRepository.save(resident);
			returnCode = InsertResidentResponseType.SUCCESS_ADDED.getResponseCode();
		} catch (Exception e) {
			e.printStackTrace();
			userCredentialRepository.deleteById(returnCode);
		}

		return returnCode;
	}
	
	private int calculateAge(LocalDate bornDate, LocalDate currentDate) {
        if ((bornDate != null) && (currentDate != null)) {
            return Period.between(bornDate, currentDate).getYears();
        } else {
            return 0;
        }
    }

	private boolean isResidentAlreadyOnList(List<Resident> residents, Resident resident) {
		return residents.stream().anyMatch(residentInList -> residentInList.equals(resident));
	}

	public Boolean delete(Integer id) {
		if (id == null) {
			throw new NullResidentException("O morador está nulo!");
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
	
	 public boolean hasValidPassword(String password) {
	        final Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
	        return pattern.matcher(password).matches();
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

	//TODO: Refatorar
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
