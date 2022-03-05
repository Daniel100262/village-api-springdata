package com.devinhouse.village.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.devinhouse.village.exception.ResidentNotFoundException;
import com.devinhouse.village.model.Resident;
import com.devinhouse.village.model.UserCredential;
import com.devinhouse.village.repositories.ResidentRepository;
import com.devinhouse.village.repositories.UserCredentialRepository;
import com.devinhouse.village.utils.ResidentUtils;
import com.devinhouse.village.utils.UserCredentialUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {

    private UserCredentialRepository userRepository;
    private UserRoleService userRoleService;
    private UserService userSevice;
    
    @BeforeEach
    public void beforeEach() {
    	this.userRepository = mock(UserCredentialRepository.class);
    	this.userRoleService = mock(UserRoleService.class);
    	this.userSevice = new UserService(userRepository, userRoleService);
    }
    
    @Test
    void ShoudReturnIllegalArgumentExceptionOnWeakUserPassword() {
    	Resident resident = ResidentUtils.residentsFullfilledWithUser.get(0);
    	resident.getUser().setPassword("123");
  
    	IllegalArgumentException exception = assertThrows(
    			IllegalArgumentException.class,
    	           () -> userSevice.create(resident),
    	           "O usuario contém uma senha fora dos padrões estabelecidos!"
    	    );

    	assertTrue(exception.getMessage().contains("O usuario contém uma senha fora dos padrões estabelecidos!"));
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
