package com.devinhouse.village.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.devinhouse.village.model.transport.UserDTO;


@Repository
public class UserDAO {

	
	public UserDTO getUserByEmail(String email) throws SQLException {
		
		Connection connection = new ConnectionFactory().getConnection();
		Statement stmt = connection.createStatement();
		
		stmt.execute("select * from \"user\" where email = '"+email+"'");
		ResultSet rs = stmt.getResultSet();
		
		UserDTO user = null;
	
		while (rs.next()) {
			String userEmail = rs.getString(2);
			String password = rs.getString(3);
			String[] roles = (String[]) rs.getArray(4).getArray();
			
			user = new UserDTO(userEmail, password, Arrays.stream(roles).collect(Collectors.toSet()));
		
		}
				
		return user;
	}
	
	public void updateUser(UserDTO user,String newPassword) throws SQLException {
		
		Connection connection = new ConnectionFactory().getConnection();
		
		String sql = "update \"user\" set email=?, password=?, roles=? where email=?";
		
		PreparedStatement preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
		
		BCryptPasswordEncoder pe = new BCryptPasswordEncoder();
	
		preparedStatement.setString(1, user.getEmail());
		preparedStatement.setString(2, pe.encode(newPassword));
		preparedStatement.setArray(3, connection.createArrayOf("VARCHAR", user.getRoles().toArray()));
		preparedStatement.setString(4, user.getEmail());
		
		preparedStatement.execute();
	}
	
	public Integer create(String email, String password, Set<String> roles) throws SQLException {
	
			Connection connection = new ConnectionFactory().getConnection();
			
			String sql = "insert into \"user\"(email, password, roles) values (?,?,?)";
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			
			BCryptPasswordEncoder pe = new BCryptPasswordEncoder();
		
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, pe.encode(password));
			preparedStatement.setArray(3, connection.createArrayOf("VARCHAR", roles.toArray()));
			
			preparedStatement.execute();
			
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			
			Integer createdUserId = null;
			 
			 while (resultSet.next()) {
				 createdUserId = resultSet.getInt(1);
			 }
			
			return createdUserId;
		
	}
	
	public UserDTO getById(Integer residentId) throws SQLException {
		
		Connection connection = new ConnectionFactory().getConnection();
		Statement stmt = connection.createStatement();
		
		stmt.execute("select * from \"user\" where id = '"+residentId+"'");
		ResultSet rs = stmt.getResultSet();
		
		UserDTO user = null;
	
		while (rs.next()) {
			String email = rs.getString(2);
			String password = rs.getString(3);
			String[] roles = (String[]) rs.getArray(4).getArray();
			
			user = new UserDTO(email, password, Arrays.stream(roles).collect(Collectors.toSet()));
		
		}
				
		return user;	
				
	}
	
	public Boolean deleteByUserId(Integer userId) {
		
		try {
			Connection connection = new ConnectionFactory().getConnection();
			Statement stmt = connection.createStatement();
			
			stmt.execute("delete from \"user\" where id="+userId);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
