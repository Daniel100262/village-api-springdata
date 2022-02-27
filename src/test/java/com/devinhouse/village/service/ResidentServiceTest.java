package com.devinhouse.village.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class ResidentServiceTest {
	
	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private ResidentService residentService;
    
    @Test
    void ShouldReturnAllResidents() {
    	
    }
    
    @Test
    void ShouldReturnResidentById() {
    	
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
    
}

