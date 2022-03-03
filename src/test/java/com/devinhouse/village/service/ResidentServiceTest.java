package com.devinhouse.village.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.devinhouse.village.model.Resident;
import com.devinhouse.village.model.UserCredential;
import com.devinhouse.village.repositories.ResidentRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class ResidentServiceTest {
    
	
    private ResidentService residentService;
    private ResidentRepository residentRepository;
    private UserService userService;
    
    @BeforeEach
    public void beforeEach() {
    	this.residentRepository = mock(ResidentRepository.class);
    	this.userService = mock(UserService.class);
    	this.residentService = new ResidentService(userService, residentRepository);
    }

    @Test
    void ShouldReturnAllResidents() {
    	when(residentRepository.findAllFiltered()).thenReturn(residentsFullfilledWithUser());	
    	List<Resident> residents = this.residentService.listResidents();
    	assertEquals(residents,residentsFullfilledWithUser());
    }
    
    @Test
    void ShouldReturnResidentById() {
    	Integer residentIdOnMock = 0;
    	Integer expectedResidentId = 1;
    	
    	when(residentRepository.getById(expectedResidentId)).thenReturn(residentsFullfilledWithUser().get(residentIdOnMock));
    	
    	System.out.println("Pegou o morador "+residentService.getResidentById(expectedResidentId));
    	
    	Integer receivedResidentId = residentService.getResidentById(expectedResidentId).getId();
    	
    	assertEquals(expectedResidentId, receivedResidentId);
    	
    }
    
    @Test
    void ShouldReturnResidentNotFoundExceptionWhenResidentIdNotFound() {
    	
    }
    
    @Test
    void ShouldCreateResident() {
    	
    }
	
    @Test
    void ShouldCalculateAgeOfResident() {
    	
    }
    
    @Test
    void ShouldThrowDateTimeExceptionWhenAgeIsIncorrect() {
    	
    }
    
    @Test
    void ShouldSayThatResidentIsAlreadyOnList() {
    	
    }
    
    @Test
    void ShouldValidatePasswordPattern() {
    	
    }
    
    @Test
    void ShouldThrowInvalidPasswordExceptionIfPasswordIsNotCoveredByPattern() {
    	
    }
    
    @Test
    void ShouldReturnResidentByBornMonth() {
    	
    }
    
    @Test
    void ShouldReturnEmptyListIfNoResidentsOnSelectedMonth() {
    	
    }
    
   public List<Resident> residentsFullfilledWithUser() { 
	   return List.of(
			new Resident(1, "Jasmine ", "Benjamin", 47, LocalDate.parse("1974-03-19"), BigDecimal.valueOf(1500), "15881077016", new UserCredential("jarmine.benjamin@gmail.com", "iJwQ@194eMeI7a*Z"), "ADMIN", "jarmine.benjamin@gmail.com"),
    		new Resident(2, "Delilah", "Blackwell", 40, LocalDate.parse("1982-03-08"), BigDecimal.valueOf(1800), "94405573034", new UserCredential("delilah.blackwell@outlook.com", "iFIvjzO#hNy!qflT"), "USER", "delilah.blackwell@outlook.com"),
    		new Resident(3, "Scott", "England", 23, LocalDate.parse("1999-05-16"), BigDecimal.valueOf(2500), "19964520026", new UserCredential("dscott.england@yahoo.com", "OaFR&P!pgzVpowHd"), "ADMIN", "scott.england@yahoo.com"),
    		new Resident(4, "Nero", "Bradford", 26, LocalDate.parse("1996-08-24"), BigDecimal.valueOf(2800), "61895597005", new UserCredential("nero.bradford@hotmail.com", "CboPrnT#a3o@vssC"), "ADMIN", "nero.bradford@hotmail.com"),
    		new Resident(5, "Ralph", "Holt", 45, LocalDate.parse("1977-12-13"), BigDecimal.valueOf(3500), "27606479003", new UserCredential("ralph.holt@aol.com", "*z9s@#mEH^%7Bnuk"), "USER", "ralph.holt@aol.com"),
    		new Resident(6, "Garrett", "Henson", 30, LocalDate.parse("1992-01-03"), BigDecimal.valueOf(3800), "04315386030", new UserCredential("garrett.henson@icloud.com", "7XZ0^MwFLdGza@BP"), "ADMIN", "garrett.henson@icloud.com"),
    		new Resident(7, "Angela", "Grimes", 65, LocalDate.parse("1957-01-05"), BigDecimal.valueOf(4500), "24351281006", new UserCredential("angela.grimes@live.com", "bxM5Ku$sVWoYr#ZM"), "USER", "angela.grimes@live.com")
    	);
    
   }
}

