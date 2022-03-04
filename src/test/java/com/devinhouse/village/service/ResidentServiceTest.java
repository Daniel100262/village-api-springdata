package com.devinhouse.village.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.devinhouse.village.exception.DuplicatedResidentException;
import com.devinhouse.village.exception.NullResidentException;
import com.devinhouse.village.exception.ResidentNotFoundException;
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
    	
    	List<Resident> expectedResidentsMock = residentsFullfilledWithUser;
    	
    	when(residentRepository.findAllFiltered()).thenReturn(expectedResidentsMock);	
    	
    	List<Resident> foundResidents = this.residentService.getAllResidents();
    	
    	assertEquals(foundResidents, expectedResidentsMock);
    }
    
    @Test
    void ShouldReturnResidentById() {
    	Integer residentIdOnMock = 0;
    	Integer expectedResidentId = 1;
    	List<Resident> mockResidents = residentsFullfilledWithUser;
    	
    	when(residentRepository.findByIdFiltered(expectedResidentId)).thenReturn(List.of(mockResidents.get(residentIdOnMock)));
    	
    	Integer receivedResidentId = residentService.getResidentById(expectedResidentId).getId();
    	
    	assertEquals(expectedResidentId, receivedResidentId);
    	
    }
    
    @Test
    void ShouldReturnResidentNotFoundExceptionWhenResidentIdNotFound() {
    	Integer expectedResidentId = 1;
    	
    	when(residentRepository.findByIdFiltered(expectedResidentId)).thenReturn(List.of());
    
    	
    	ResidentNotFoundException exception = assertThrows(
    			ResidentNotFoundException.class,
    	           () -> residentService.getResidentById(expectedResidentId).getId(),
    	           "Não existe nenhum morador com o ID "+expectedResidentId
    	    );

    	assertTrue(exception.getMessage().contains("Não existe nenhum morador com o ID "+expectedResidentId));
    }
    
    @Test
    void ShouldCreateResident() {
    	List<Resident> mockResidents = residentsFullfilledWithUser;
    	Resident expectedResident = mockResidents.get(0);
    	
    	when(residentRepository.save(expectedResident)).thenReturn(expectedResident);
    	
    	Resident createdResident = residentService.create(expectedResident);
    	
    	assertEquals(expectedResident, createdResident);
    	
    }
	
    @Test
    void ShouldCalculateAgeOfResident() {
    	List<Resident> mockResidents = residentsFullfilledWithUser;
    	Resident expectedResident = mockResidents.get(0);
    	Integer expectedAgeOfResident = expectedResident.getAge();
    	
    	expectedResident.setAge(null);
    	
    	when(residentRepository.save(expectedResident)).thenReturn(expectedResident);
    	
    	Integer ageOfCreatedResident = residentService.create(expectedResident).getAge();
    	
    	assertEquals(expectedAgeOfResident, ageOfCreatedResident);
    }
   
    
    @Test
    void ShouldSayThatResidentIsAlreadyOnList() {
    	List<Resident> mockResidents = residentsFullfilledWithUser;
    	Resident expectedResident = mockResidents.get(0);
    	
    	when(residentRepository.findAll()).thenReturn(mockResidents);
    
    	DuplicatedResidentException exception = assertThrows(
    			DuplicatedResidentException.class,
    	           () -> residentService.create(expectedResident),
    	           "O morador "+expectedResident.getFirstName()+" "+expectedResident.getLastName()+" com CPF "+expectedResident.getCpf()+" já existe na lista!"
    	    );

    	assertTrue(exception.getMessage().contains("O morador "+expectedResident.getFirstName()+" "+expectedResident.getLastName()+" com CPF "+expectedResident.getCpf()+" já existe na lista!"));
    }
    
	@Test
	void ShoudReturnCorrectVillageCosts() {
		List<Resident> mockResidents = residentsFullfilledWithUser;
		long expectedCostLong = mockResidents.stream()
				.mapToLong(resident -> Long.parseLong(resident.getIncome().toString())).sum();

		BigDecimal expectedCost = BigDecimal.valueOf(expectedCostLong);

		when(residentRepository.findAll()).thenReturn(mockResidents);
		BigDecimal receivedCost = residentService.getVillageCosts();
		
		assertEquals(expectedCost, receivedCost);

	}
    

    
	@Test
	void ShouldReturnResidentByBornMonth() {
		Integer month = 12;
		List<Resident> mockResidents = residentsFullfilledWithUser;

		List<Resident> residentsDezember = mockResidents.stream()
				.filter(resident -> resident.getBornDate().getMonthValue() == month).collect(Collectors.toList());
		;

		when(residentRepository.getResidentsByMonth(month)).thenReturn(residentsDezember);
		List<Resident> residentsToBeFetched = residentService.getResidentByMonth(month);

		assertEquals(residentsDezember, residentsToBeFetched);
	}
    
    @Test
    void ShouldReturnEmptyListIfNoResidentsOnSelectedMonth() {
    	Integer month = 04;

		List<Resident> emptyList = List.of();

		when(residentRepository.getResidentsByMonth(month)).thenReturn(emptyList);
		List<Resident> residentsToBeFetched = residentService.getResidentByMonth(month);

		assertEquals(residentsToBeFetched, emptyList);
    }
    
    @Test
	void ShouldReturnResidentByName() {
    	List<Resident> mockResidents = residentsFullfilledWithUser;
    	String name = mockResidents.get(0).getFirstName();
    	List<Resident> residentsWithName = mockResidents.stream()
				.filter(resident -> resident.getFirstName().equals(name)).collect(Collectors.toList());
		;
    	
    	when(residentRepository.getResidentsByName(name)).thenReturn(residentsWithName);
    	
    	List<Resident> residents = residentService.getResidentByName(name);
    	
    	assertEquals(residents, residentsWithName);
    }
   
    @Test
    void ShoudSucessfulDeleteResident() {
    	Integer id = 8;
    	Boolean expectedReturnStatus = true;
    	when(residentRepository.existsById(id)).thenReturn(true);
    	
    	
    	Boolean returnStatus = residentService.delete(id);
    	
    	assertEquals(returnStatus, expectedReturnStatus);
    }
    
    @Test
    void ShoudReturnFalseIfDeleteResidentIsUnsucessful() {
    	Integer id = 8;
    	Boolean expectedReturnStatus = false;
    	when(residentRepository.existsById(id)).thenReturn(false);
    	
    	Boolean returnStatus = residentService.delete(id);
    	
    	assertEquals(returnStatus, expectedReturnStatus);
    }
    
    @Test
	void ShouldThrowNullResidentExceptionOnDeleteNullResident() {
    	
    	NullResidentException exception = assertThrows(
    			NullResidentException.class,
    	           () -> residentService.delete(null),
    	           "O morador que você tentou apagar está nulo!"
    	    );
    	
    	assertTrue(exception.getMessage().contains("O morador que você tentou apagar está nulo!"));
    }
    
   private final List<Resident> residentsFullfilledWithUser =  
	    List.of(
			new Resident(1, "Jasmine ", "Benjamin", 47, LocalDate.parse("1974-03-19"), BigDecimal.valueOf(1500), "15881077016", new UserCredential("jarmine.benjamin@gmail.com", "iJwQ@194eMeI7a*Z"), "ADMIN", "jarmine.benjamin@gmail.com"),
    		new Resident(2, "Delilah", "Blackwell", 40, LocalDate.parse("1982-03-08"), BigDecimal.valueOf(1800), "94405573034", new UserCredential("delilah.blackwell@outlook.com", "iFIvjzO#hNy!qflT"), "USER", "delilah.blackwell@outlook.com"),
    		new Resident(3, "Scott", "England", 23, LocalDate.parse("1999-05-16"), BigDecimal.valueOf(2500), "19964520026", new UserCredential("dscott.england@yahoo.com", "OaFR&P!pgzVpowHd"), "ADMIN", "scott.england@yahoo.com"),
    		new Resident(4, "Nero", "Bradford", 26, LocalDate.parse("1996-08-24"), BigDecimal.valueOf(2800), "61895597005", new UserCredential("nero.bradford@hotmail.com", "CboPrnT#a3o@vssC"), "ADMIN", "nero.bradford@hotmail.com"),
    		new Resident(5, "Ralph", "Holt", 45, LocalDate.parse("1977-12-13"), BigDecimal.valueOf(3500), "27606479003", new UserCredential("ralph.holt@aol.com", "*z9s@#mEH^%7Bnuk"), "USER", "ralph.holt@aol.com"),
    		new Resident(6, "Garrett", "Henson", 30, LocalDate.parse("1992-01-03"), BigDecimal.valueOf(3800), "04315386030", new UserCredential("garrett.henson@icloud.com", "7XZ0^MwFLdGza@BP"), "ADMIN", "garrett.henson@icloud.com"),
    		new Resident(7, "Angela", "Grimes", 65, LocalDate.parse("1957-01-05"), BigDecimal.valueOf(4500), "24351281006", new UserCredential("angela.grimes@live.com", "bxM5Ku$sVWoYr#ZM"), "USER", "angela.grimes@live.com")
    	);
    
   
}

