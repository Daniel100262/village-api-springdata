package com.devinhouse.village.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityExistsException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.devinhouse.village.exception.DuplicatedUserException;
import com.devinhouse.village.exception.NullResidentException;
import com.devinhouse.village.exception.NullUserException;
import com.devinhouse.village.model.dao.Resident;
import com.devinhouse.village.model.dao.UserCredential;
import com.devinhouse.village.model.dao.UserSpringSecurity;
import com.devinhouse.village.repositories.UserCredentialRepository;

@Service
public class UserService implements UserDetailsService {

	private UserCredentialRepository userRepository;

	public UserCredential getUserById(Integer id) {
		return userRepository.getById(id);
	}
	
	public UserService(UserCredentialRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException { // TODO: Modificado aqui
		UserCredential user = null;
		 try {
		System.out.println("Usuário --> " + userRepository.getUserByEmail(email).getEmail());
		System.out.println("Senha --> " + userRepository.getUserByEmail(email).getPassword());
		user = userRepository.getUserByEmail(email);

		} catch (NullPointerException e) {
			//System.out.println("Deu null pointer no load user by username");
			throw new NullResidentException("Login invalido! Nenhum morador encontrado com o e-mail: "+email);
		}
		if (user == null) {
			throw new UsernameNotFoundException(email);
		}
		return new UserSpringSecurity(user.getEmail(), user.getPassword(), user.getUserRoles());
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
		if (resident.getUser().getEmail() == null || resident.getUser().getPassword() == null
				|| resident.getUser() == null||resident.getUser().getUserRoles() == null) {
			throw new IllegalArgumentException("O usuario contém parâmetros nulos!"); // TODO: Remover
		}

		if (!isPasswordValid(resident.getUser().getPassword())) {
			throw new IllegalArgumentException("O usuario contém uma senha fora dos padrões estabelecidos!"); // TODO:
																												// Remover
		}

		if (resident.getUser().equals(null)) {

			StringBuilder message = new StringBuilder();
			message.append("O usuário que estava sendo criado para o morador ").append(resident.getFirstName())
					.append(" ").append(resident.getLastName()).append(" não existe!");

			throw new NullUserException(message.toString());
		}
		
		BCryptPasswordEncoder pe = new BCryptPasswordEncoder();

		try {
			resident.getUser().setPassword(pe.encode(resident.getUser().getPassword()));
			UserCredential createdUser = this.userRepository.save(resident.getUser());
			resident.setUser(createdUser);
		} catch (EntityExistsException e) {
			throw new DuplicatedUserException("O usuário com e-mail "+resident.getUser().getEmail()+ "já existe no banco de dados!");
		}
		
	}

	public void updateUser(UserCredential user, String newPassword) throws SQLException {
		userRepository.save(user);
	}

	public Boolean isPasswordValid(String password) {
		Pattern pattern = java.util.regex.Pattern
				.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[*@#$%^&+=])(?=\\S+$).{8,}$");
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}

}
