package com.devinhouse.village.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.devinhouse.village.model.UserRole;
import com.devinhouse.village.repositories.UserRoleRepository;
import com.devinhouse.village.utils.UserCredentialUtils;


@SpringBootTest
@AutoConfigureMockMvc
public class UserRoleServiceTest {
	
	private UserRoleRepository userRoleRepository;
	private UserRoleService userRoleService;
	
	@BeforeEach
    public void beforeEach() {
		this.userRoleRepository = mock(UserRoleRepository.class);
		this.userRoleService = new UserRoleService(userRoleRepository);
	}
	
	@Test
	void shoudGetRolesListByRoleName() {
		String roleName = "ADMIN";
		List<UserRole> expectedRoles = UserCredentialUtils.userCredentialsFilled.get(0).getUserRoles();
		
		when(userRoleRepository.getRolesListByRoleName(roleName)).thenReturn(expectedRoles);
		
		List<UserRole> roles = userRoleService.getRolesListByRoleName(roleName);
		
		assertEquals(expectedRoles, roles);
	}
	
	@Test
	void shoudGetRoleByRoleName() {
		String roleName = "ADMIN";
		UserRole expectedRoles = UserCredentialUtils.userCredentialsFilled.get(0).getUserRoles().get(0);
		
		when(userRoleRepository.getRoleByRoleName(roleName)).thenReturn(expectedRoles);
		
		UserRole role = userRoleService.getRoleByRoleName(roleName);
		
		assertEquals(expectedRoles, role);
	}
}
