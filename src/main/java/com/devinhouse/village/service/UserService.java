package com.devinhouse.village.service;

import java.util.ArrayList;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.devinhouse.village.exception.DuplicatedUserException;
import com.devinhouse.village.exception.NullUserException;
import com.devinhouse.village.model.Resident;
import com.devinhouse.village.model.UserCredential;
import com.devinhouse.village.model.UserSpringSecurity;
import com.devinhouse.village.repositories.UserCredentialRepository;

@Service
public class UserService implements UserDetailsService {

	
	private UserCredentialRepository userRepository;
	
	private UserRoleService userRoleService;

	
	@Autowired
	public UserService(UserCredentialRepository userRepository, UserRoleService userRoleService) {
		this.userRepository = userRepository;
		this.userRoleService = userRoleService;
	}
	
	public UserService() {
		
	}

	public UserCredential getUserById(Integer id) {
		return userRepository.getById(id);
	}
	
	public UserService(UserCredentialRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public void delete(UserCredential userCredential) {
		this.userRepository.delete(userCredential);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserCredential user = null;
		user = userRepository.getUserByEmail(email);

		if (user != null) {
			return new UserSpringSecurity(user.getEmail(), user.getPassword(), user.getUserRoles());
		} else {
			throw new UsernameNotFoundException("Login invalido! Nenhum morador encontrado com o e-mail: "+email);
		}

	}

	public static UserSpringSecurity authenticated() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			return new UserSpringSecurity((String) authentication.getPrincipal(), null, new ArrayList<>());
		} catch (Exception e) {
			return null;
		}
	}

	public void create(Resident resident) {

		try {
			if (resident.getUser().getEmail() == null || resident.getUser().getPassword() == null
					|| resident.getUser().getUserRoles() == null) {
				throw new IllegalArgumentException("O usuario contém parâmetros nulos!");
			}

			if (!resident.getUser().hasValidPassword()) {
				throw new IllegalArgumentException("O usuario contém uma senha fora dos padrões estabelecidos!");
			}
		} catch (NullPointerException e) {
			StringBuilder message = new StringBuilder();
			message.append("O usuário que estava sendo criado para o morador ").append(resident.getFirstName())
					.append(" ").append(resident.getLastName()).append(" não existe!");

			throw new NullUserException(message.toString());
		}

		BCryptPasswordEncoder pe = new BCryptPasswordEncoder();

		try {
			resident.getUser().setUserRoles(userRoleService.getRolesListByRoleName(resident.getRole()));
			resident.getUser().setPassword(pe.encode(resident.getUser().getPassword()));
			UserCredential createdUser = this.userRepository.save(resident.getUser());
			resident.setUser(createdUser);
		} catch (EntityExistsException e) {
			throw new DuplicatedUserException(
					"O usuário com e-mail " + resident.getUser().getEmail() + "já existe no banco de dados!");
		}

	}

	public UserCredential updateUser(UserCredential user, String newPassword) {
		return userRepository.save(user);
	}
}
