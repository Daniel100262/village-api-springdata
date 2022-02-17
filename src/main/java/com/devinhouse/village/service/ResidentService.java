package com.devinhouse.village.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.devinhouse.village.model.dao.InsertResidentResponseType;
import com.devinhouse.village.model.dao.ResidentDAO;
import com.devinhouse.village.model.transport.ResidentDTO;
import com.devinhouse.village.model.transport.VillageReportDTO;

@Service
public class ResidentService {
	
	@Autowired
	private ResidentDAO residentDAO;
	
	@Autowired UserService userService;
	
	@Value("${village.budget}")
	private Float budgetOfVillage;

	public ResidentService(ResidentDAO residentDAO) {
		this.residentDAO = residentDAO;
	}

	public ResidentDAO getResidentDAO() {
		return residentDAO;
	}

	
	public void setResidentDAO(ResidentDAO residentDAO) {
		this.residentDAO = residentDAO;
	}
	
	public List<ResidentDTO> listResidents() throws SQLException {
		return this.residentDAO.listAllResidents(false);
	}

	public BigDecimal getVillageCosts() throws SQLException {
		List<ResidentDTO> residents = this.residentDAO.listAllResidents(true);
		BigDecimal costsSum = BigDecimal.valueOf(0);
		for (ResidentDTO resident : residents) {
			costsSum = costsSum.add(resident.getIncome());
		}
		return costsSum;
	}
	
	
	public List<ResidentDTO> allResidentsWithAllFields() throws SQLException {
		return this.residentDAO.listAllResidents(true);
		
	}

	public ResidentDTO getResidentById(Integer id) throws SQLException {
		return residentDAO.getResidentById(id);
	}

	public List<ResidentDTO> getResidentByName(String name) throws SQLException {
		return residentDAO.getResident(name);
	}

	public Integer create(ResidentDTO resident) throws SQLException {
		if (resident == null) {
			throw new IllegalArgumentException("O morador está nulo!");
		} else if (isResidentAlreadyOnList(this.residentDAO.listAllResidents(true), resident)) {
			throw new IllegalArgumentException("O morador já existe na lista!");
		}
	
		Integer returnCode = InsertResidentResponseType.UNKNOW_ERROR.getResponseCode();
		
		userService.create(resident);
		
		resident.setAge(calculateAge(resident.getBornDate(), LocalDate.now()));
		
		try {
			returnCode = this.residentDAO.create(resident);
			System.out.println("Codigo retornado: "+returnCode); //TODO: Remover isso
		} catch (Exception e) {
			e.printStackTrace();
			userService.deleteUserById(resident.getUserid());
			
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

	private boolean isResidentAlreadyOnList(List<ResidentDTO> residents, ResidentDTO resident) {
		return residents.stream().anyMatch(residentInList -> residentInList.equals(resident));
	}

	public Boolean delete(Integer id) {
		if (id == null) {
			throw new IllegalArgumentException("O morador está nulo!");
		}
		return this.residentDAO.delete(id);
	}
	
	 public static boolean isValidPassword(String password) {
	        final Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
	        return pattern.matcher(password).matches();
	 }

	public List<ResidentDTO> getResidentByAge(Integer age) throws SQLException {
		
		if(age.equals(null)) {
			throw new IllegalArgumentException("A idade está nula!");
		}
		
		return this.residentDAO.getResidentByAge(age);
	}

	public List<ResidentDTO> getResidentByMonth(Integer month) throws SQLException {
		if(month.equals(null)) {
			throw new IllegalArgumentException("O mês está nulo!");
		}
		return this.residentDAO.getResidentByMonth(month);
	}

	//TODO: Refatorar
	public VillageReportDTO genereteReport() throws SQLException {
		List<ResidentDTO> residents = this.residentDAO.listAllResidents(true);
	
	        final BigDecimal villageTotalCost = residents.stream().reduce(
	                BigDecimal.ZERO,(accumulator, resident) -> resident.getIncome().add((accumulator)),
	                BigDecimal::add
	        );
	        final Float cost = budgetOfVillage - villageTotalCost.floatValue();
	        final ResidentDTO villagerWithHigherCost = residents.stream().max(ResidentDTO.compareByIncome).orElse(null);


	        final String villagerName = String.format("%s %s", villagerWithHigherCost.getFirstName(),villagerWithHigherCost.getLastName());
	        return new VillageReportDTO(cost, budgetOfVillage, villageTotalCost, villagerName);
	
	}
}
