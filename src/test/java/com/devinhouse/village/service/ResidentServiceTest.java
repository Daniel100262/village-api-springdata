package com.devinhouse.village.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.devinhouse.village.exception.DuplicatedResidentException;
import com.devinhouse.village.exception.NullResidentException;
import com.devinhouse.village.exception.ResidentNotFoundException;
import com.devinhouse.village.model.Resident;
import com.devinhouse.village.model.transport.VillageReportDTO;
import com.devinhouse.village.repositories.ResidentRepository;
import com.devinhouse.village.utils.ResidentUtils;

@SpringBootTest
@AutoConfigureMockMvc
public class ResidentServiceTest {
    
	
    private ResidentService residentService;
    private ResidentRepository residentRepository;
    private UserService userService;
    
    @Value("${village.budget}")
	private Float budgetOfVillage;
    
    @BeforeEach
    public void beforeEach() {
    	this.residentRepository = mock(ResidentRepository.class);
    	this.userService = mock(UserService.class);
    	this.residentService = new ResidentService(residentRepository, userService, budgetOfVillage);
    }

    @Test
    void ShouldReturnAllResidents() {
    	
    	List<Resident> expectedResidentsMock = ResidentUtils.residentsFullfilledWithUser;
    	
    	when(residentRepository.findAllFiltered()).thenReturn(expectedResidentsMock);	
    	
    	List<Resident> foundResidents = this.residentService.getAllResidents();
    	
    	assertEquals(foundResidents, expectedResidentsMock);
    }
    
    @Test
    void ShouldReturnResidentById() {
    	Integer residentIdOnMock = 0;
    	Integer expectedResidentId = 1;
    	List<Resident> mockResidents = ResidentUtils.residentsFullfilledWithUser;
    	
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
    	List<Resident> mockResidents = ResidentUtils.residentsFullfilledWithUser;
    	Resident expectedResident = mockResidents.get(0);
    	
    	when(residentRepository.save(expectedResident)).thenReturn(expectedResident);
    	
    	Resident createdResident = residentService.create(expectedResident);
    	
    	assertEquals(expectedResident, createdResident);
    	
    }
	
    @Test
    void ShouldCalculateAgeOfResident() {
    	List<Resident> mockResidents = ResidentUtils.residentsFullfilledWithUser;
    	Resident expectedResident = mockResidents.get(0);
    	Integer expectedAgeOfResident = expectedResident.getAge();
    	
    	expectedResident.setAge(null);
    	
    	when(residentRepository.save(expectedResident)).thenReturn(expectedResident);
    	
    	Integer ageOfCreatedResident = residentService.create(expectedResident).getAge();
    	
    	assertEquals(expectedAgeOfResident, ageOfCreatedResident);
    }
   
    
    @Test
    void ShouldSayThatResidentIsAlreadyOnList() {
    	List<Resident> mockResidents = ResidentUtils.residentsFullfilledWithUser;
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
		List<Resident> mockResidents = ResidentUtils.residentsFullfilledWithUser;
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
		List<Resident> mockResidents = ResidentUtils.residentsFullfilledWithUser;

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
    	List<Resident> mockResidents = ResidentUtils.residentsFullfilledWithUser;
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
    
    @Test
    void ShouldGenerateReportWithoutEmail() {
    	List<Resident> mockResidents = ResidentUtils.residentsFullfilledWithUser;
    	when(residentRepository.findAll()).thenReturn(mockResidents);
    	
    	BigDecimal expectedVillageTotalCost = mockResidents.stream().reduce(
                BigDecimal.ZERO,(accumulator, resident) -> resident.getIncome().add((accumulator)),
                BigDecimal::add
        );
    	
    	
    	Float expectedCost = budgetOfVillage - expectedVillageTotalCost.floatValue();
    	Resident expectedResidentWithHigherCost = mockResidents.stream().max(Resident.compareByIncome).orElse(null);
    	String expectedResidentWithHigherCostName = expectedResidentWithHigherCost.getFirstName()+" "+expectedResidentWithHigherCost.getLastName();
 
    	VillageReportDTO generatedReport = residentService.genereteReport();
    	
    	BigDecimal villageTotalCost = generatedReport.getResidentsCostSum();
    	Float cost = generatedReport.getCost();
    	String residentWithHigherCost = generatedReport.getResidentWithHigherCost();
    	
    	
    	
       	assertEquals(expectedVillageTotalCost, villageTotalCost);   	
    	assertEquals(expectedCost, cost);
    	assertEquals(expectedResidentWithHigherCostName, residentWithHigherCost);
 
    }
    
    @Test
    void ShouldGenerateReportWithEmail() {
    	String expectedEmail = "admin@company.com";
    	List<Resident> mockResidents = ResidentUtils.residentsFullfilledWithUser;
    	when(residentRepository.findAll()).thenReturn(mockResidents);
    	
    	BigDecimal expectedVillageTotalCost = mockResidents.stream().reduce(
                BigDecimal.ZERO,(accumulator, resident) -> resident.getIncome().add((accumulator)),
                BigDecimal::add
        );
    	
    	
    	Float expectedCost = budgetOfVillage - expectedVillageTotalCost.floatValue();
    	Resident expectedResidentWithHigherCost = mockResidents.stream().max(Resident.compareByIncome).orElse(null);
    	String expectedResidentWithHigherCostName = expectedResidentWithHigherCost.getFirstName()+" "+expectedResidentWithHigherCost.getLastName();
    	
    	VillageReportDTO generatedReport = residentService.genereteReport(expectedEmail);
    	
    	BigDecimal villageTotalCost = generatedReport.getResidentsCostSum();
    	Float cost = generatedReport.getCost();
    	String residentWithHigherCost = generatedReport.getResidentWithHigherCost();
    	String email = generatedReport.getReportEmailDestination();
    	
    	
       	assertEquals(expectedVillageTotalCost, villageTotalCost);   	
    	assertEquals(expectedCost, cost);
    	assertEquals(expectedResidentWithHigherCostName, residentWithHigherCost);
    	assertEquals(expectedEmail, email);
    }
   
}

