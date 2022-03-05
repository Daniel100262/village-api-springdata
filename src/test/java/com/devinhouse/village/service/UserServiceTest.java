package com.devinhouse.village.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.devinhouse.village.exception.NullUserException;
import com.devinhouse.village.model.Resident;
import com.devinhouse.village.model.UserCredential;
import com.devinhouse.village.model.UserSpringSecurity;
import com.devinhouse.village.repositories.UserCredentialRepository;
import com.devinhouse.village.utils.ResidentUtils;
import com.devinhouse.village.utils.UserCredentialUtils;

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
    	Resident resident = ResidentUtils.residentsFullfilledWithUser.get(3);
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
    	UserCredential expectedUser = UserCredentialUtils.userCredentialsFilled.get(2);
    	String email = expectedUser.getEmail();
    	
    	when(userRepository.getUserByEmail(email)).thenReturn(expectedUser);
    	
    	UserCredential user = userRepository.getUserByEmail(email);
    	
    	assertEquals(expectedUser, user);
    }
    
    @Test
    void ShouldThrowUsernameNotFoundExecptionWhenEmailIsNotFound() {
    	String email = "inexistent@company.com";
    	
    	when(userRepository.getUserByEmail(email)).thenReturn(null);
    	
    	UsernameNotFoundException exception = assertThrows(
    			UsernameNotFoundException.class,
    	           () -> this.userSevice.loadUserByUsername(email),
    	           "Login invalido! Nenhum morador encontrado com o e-mail: "+email
    	    ); 
    	
    	assertTrue(exception.getMessage().contains("Login invalido! Nenhum morador encontrado com o e-mail: "+email));
    }
    
    @Test
    void ShouldLoadCorrentUserByEmail() {
    	UserCredential user = UserCredentialUtils.userCredentialsFilled.get(0);
    	String email = user.getEmail();
    	UserSpringSecurity expectedUserSpringSecurity = new UserSpringSecurity(email, user.getPassword(), user.getUserRoles());
    	
    	when(userRepository.getUserByEmail(email)).thenReturn(user);
    	
    	UserSpringSecurity userSpringSecurity = (UserSpringSecurity) userSevice.loadUserByUsername(email);
    	
    	assertEquals(expectedUserSpringSecurity.getAuthorities(), userSpringSecurity.getAuthorities());
    }
    
    @Test
    void ShoudNotCreateUserIfUserHaveInvalidParameters() {
    	Resident resident = ResidentUtils.residentsFullfilledWithUser.get(1);
    	resident.getUser().setPassword(null);
    	resident.getUser().setEmail(null);
    	resident.getUser().setUserRoles(null);
  
    	IllegalArgumentException exception = assertThrows(
    			IllegalArgumentException.class,
    	           () -> userSevice.create(resident),
    	           "O usuario contém parâmetros nulos!"
    	    );

    	assertTrue(exception.getMessage().contains("O usuario contém parâmetros nulos!"));
    }
    
    @Test
    void ShoudThrowNullUserExceptionIfResidentHasNoUser() {
    	Resident resident = ResidentUtils.residentsFullfilledWithUser.get(0);
    	resident.setUser(null);
    	
    	NullUserException exception = assertThrows(
    			NullUserException.class,
    	           () -> userSevice.create(resident),
    	           "O usuário que estava sendo criado para o morador "+resident.getFirstName()+" "+resident.getLastName()+" não existe!"
    	    );

    	assertTrue(exception.getMessage().contains("O usuário que estava sendo criado para o morador "+resident.getFirstName()+" "+resident.getLastName()+" não existe!"));
    }
  
    
    
    @Test
    void ShouldUpdateUser() {
    	UserCredential expectedUser = UserCredentialUtils.userCredentialsFilled.get(3);
    	String expectedNewPassword = "*FC@MgsiQr9Rr@uCUTz";
    	
    	
    	when(userRepository.save(expectedUser)).thenReturn(new UserCredential(expectedUser.getEmail(), expectedNewPassword, expectedUser.getUserRoles()));
    	
    	String newPassword = userSevice.updateUser(expectedUser, expectedNewPassword).getPassword();
    	
    	assertEquals(expectedNewPassword, newPassword);
    }
    
    @Test
    void ShouldThrowInvalidUserExceptinWhenUserIsInvalid() {

    }
    
}
