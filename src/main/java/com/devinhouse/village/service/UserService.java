package com.devinhouse.village.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.devinhouse.village.exception.NullUserException;
import com.devinhouse.village.model.dao.Resident;
import com.devinhouse.village.model.dao.User;
import com.devinhouse.village.model.dao.UserDAO;
import com.devinhouse.village.model.dao.UserSpringSecurity;
import com.devinhouse.village.model.transport.UserDTO;
import com.devinhouse.village.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

	private UserDAO userDAO;

	private UserRepository userRepository;

	public User getUserByEmail(String email) {
		return userRepository.getUserByEmail(email);
	}

	public User getUserById(Integer id) {
		return userRepository.getById(id);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // TODO: Modificado aqui
		User user = null;
		try {
			user = getUserByEmail(username);
		} catch (NullPointerException e) {
			System.out.println("Deu null pointer no load user by username");
			e.printStackTrace();
		}
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		return new UserSpringSecurity(user.getEmail(), user.getPassword(), user.getRoles());
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
		if (resident.getEmail() == null || resident.getPassword() == null || resident.getRoles() == null) {
			throw new IllegalArgumentException("O usuario contém parâmetros nulos!"); // TODO: Remover
		}

		if (!isPasswordValid(resident.getPassword())) {
			throw new IllegalArgumentException("O usuario contém uma senha fora dos padrões estabelecidos!"); // TODO:
																												// Remover
		}

		if (resident.getUser().equals(null)) {

			StringBuilder message = new StringBuilder();
			message.append("O usuário que estava sendo criado para o morador ").append(resident.getFirstName())
					.append(" ").append(resident.getLastName()).append(" não existe!");

			throw new NullUserException(message.toString());
		}

		User createdUser = this.userRepository.save(resident.getUser());

		resident.setUser(createdUser);
	}

	public void updateUser(User user, String newPassword) throws SQLException {
		userRepository.save(user);
	}

	public Boolean isPasswordValid(String password) {
		Pattern pattern = java.util.regex.Pattern
				.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[*@#$%^&+=])(?=\\S+$).{8,}$");
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}

	public Boolean deleteUserById(Integer userid) {
		if (userid == null) {
			throw new IllegalArgumentException("O usuario está nulo!");
		}
		return this.userDAO.deleteByUserId(userid);
	}
}
