package com.devinhouse.village.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {
	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userSevice;
    
    @Test
    void ShoudReturnInvalidCredentialPropertyExceptionOnWeakUserPassword() {
    	
    }
    
    @Test
    void ShouldLoadUserByEmail() {
    	
    }
    
    @Test
    void ShouldThrowUsernameNotFoundExecptionWhenEmailIsNotFound() {
    	
    }
    
    @Test
    void ShouldCreateUser() {
    	
    }
    
    @Test
    void ShouldThrowDuplicatedUsernameExceptionWhenUsernameIsDuplicated() {
    	
    }
    
    @Test
    void ShouldUpdateUser() {
    	
    }
    
    @Test
    void ShouldThrowInvalidUserExceptinWhenUserIsInvalid() {
    	
    }
    
}
