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

import com.devinhouse.village.model.dao.UserDAO;
import com.devinhouse.village.model.dao.UserSpringSecurity;
import com.devinhouse.village.model.transport.ResidentDTO;
import com.devinhouse.village.model.transport.UserDTO;

@Service
public class UserService implements UserDetailsService {

	private UserDAO userDAO;

	public UserService(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public UserDTO getUserByEmail(String email) throws SQLException {
		return userDAO.getUserByEmail(email);
	}
	
	public UserDTO getUserById(Integer id) throws SQLException {
		return userDAO.getById(id);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { //TODO: Modificado aqui
		UserDTO user = null;
		try {
			user = getUserByEmail(username);
		} catch (SQLException e) {
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
			return new UserSpringSecurity ((String) authentication.getPrincipal(), null, new ArrayList<>());
		} catch (Exception e) {
			return null;
		}
	}
	
	public void create(ResidentDTO resident) throws SQLException {
		if (resident.getEmail() == null || resident.getPassword() == null || resident.getRoles() == null) {
			throw new IllegalArgumentException("O usuario contém parâmetros nulos!"); //TODO: Remover
		} 
		
		if(!isPasswordValid(resident.getPassword())) {
			throw new IllegalArgumentException("O usuario contém uma senha fora dos padrões estabelecidos!"); //TODO: Remover
		}

		Integer createdUserId =  this.userDAO.create(resident.getEmail(), resident.getPassword(), resident.getRoles());
		resident.setUserid(createdUserId);
		
	}

	public void updateUser(UserDTO user, String newPassword) throws SQLException {
		userDAO.updateUser(user,newPassword);
	}
	
	public Boolean isPasswordValid(String password) {
		Pattern pattern = java.util.regex.Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[*@#$%^&+=])(?=\\S+$).{8,}$");
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
