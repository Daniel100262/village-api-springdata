package com.devinhouse.village.model.dao;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.devinhouse.village.model.transport.ResidentDTO;
import com.devinhouse.village.service.UserService;

public class ResidentDAO {
	
	public List<ResidentDTO> listAllResidents(boolean withAllFields) throws SQLException {
		
		if(withAllFields) {
			return getResidentWithAllFields();
		}
		
		return getResidentWithFilteredFields();
	}

	@Autowired
	UserService userService;

	private List<ResidentDTO> getResidentWithFilteredFields() throws SQLException {
		Connection connection = new ConnectionFactory().getConnection();
		Statement stmt = connection.createStatement();
		
		stmt.execute("select id, firstname, lastname from resident");
		ResultSet rs = stmt.getResultSet();
		
		List<ResidentDTO> residentsViaSQL = new ArrayList<>();
		while (rs.next()) {
			Integer id = rs.getInt(1);
			String firstName = rs.getString(2);
			String lastName = rs.getString(3);
			
			ResidentDTO resident = new ResidentDTO(id, firstName, lastName);
			
			residentsViaSQL.add(resident);
		}
		
		return residentsViaSQL;
	}
	
	private List<ResidentDTO> getResidentWithAllFields() throws SQLException {
		Connection connection = new ConnectionFactory().getConnection();
		Statement stmt = connection.createStatement();
		
		stmt.execute("select * from resident");
		ResultSet rs = stmt.getResultSet();
		
		List<ResidentDTO> residentsViaSQL = new ArrayList<>();
		while (rs.next()) {
			Integer id = rs.getInt(1);
			String firstName = rs.getString(2);
			String lastName = rs.getString(3);
			Integer age = rs.getInt(4);
			LocalDate bornDate = rs.getDate(5).toLocalDate();
			BigDecimal income = rs.getBigDecimal(6);
			String cpf = rs.getString(7);
			String password = userService.getUserById(rs.getInt(8)).getPassword();
			String email = userService.getUserById(rs.getInt(8)).getEmail();
			
			ResidentDTO resident = new ResidentDTO(id, firstName, lastName, age, bornDate, income, cpf, password, email);
			
			residentsViaSQL.add(resident);
		}
		
		return residentsViaSQL;
	}
	
	

	public ResidentDTO getResidentById(Integer idReceived) throws SQLException {
		
		Connection connection = new ConnectionFactory().getConnection();
		Statement stmt = connection.createStatement();
		
		stmt.execute("select * from resident where id ="+idReceived);
		
		ResultSet rs = stmt.getResultSet();
		
		ResidentDTO resident = new ResidentDTO();
		
		while (rs.next()) {
			String firstName = rs.getString(2);
			String lastName = rs.getString(3);
			Integer age = rs.getInt(4);
			LocalDate bornDate = rs.getDate(5).toLocalDate();
			BigDecimal income = rs.getBigDecimal(6);
			String cpf = rs.getString(7);
			Integer userId = rs.getInt(8);
			String email = userService.getUserById(rs.getInt(8)).getEmail();
			
			resident.setFirstName(firstName);
			resident.setLastName(lastName);
			resident.setAge(age);
			resident.setBornDate(bornDate);
			resident.setIncome(income);
			resident.setCpf(cpf);
			resident.setEmail(email);
			resident.setUserid(userId);
		}
		return resident;
	}
	
	public List<ResidentDTO> getResident(String name) throws SQLException {
		
		Connection connection = new ConnectionFactory().getConnection();
		Statement stmt = connection.createStatement();
		
		stmt.execute("select id, firstname, lastname from resident where firstname ilike '%"+name+"%' or lastname ilike  '%"+name+"%' ");
		
		ResultSet rs = stmt.getResultSet();
		
		List<ResidentDTO> residentsViaSQL = new ArrayList<>();
		while (rs.next()) {
			Integer id = rs.getInt(1);
			String firstName = rs.getString(2);
			String lastName = rs.getString(3);
			
			ResidentDTO resident = new ResidentDTO(id, firstName, lastName);
			
			residentsViaSQL.add(resident);
		}
		
		return residentsViaSQL;
	}

	
	
	public Integer create(ResidentDTO resident) {
		
		
		if(isInvalidDataInserted(resident)) {
			return InsertResidentResponseType.INVALID_DATA.getResponseCode();
		}
		
		try {
			Connection connection = new ConnectionFactory().getConnection();
			
			String sql = "insert into resident(firstname, lastname, age, borndate,income,cpf, user_id) values (?,?,?,?,?,?,?)";
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, resident.getFirstName());
			preparedStatement.setString(2, resident.getLastName());
			preparedStatement.setInt(3, resident.getAge());
			preparedStatement.setDate(4, Date.valueOf(resident.getBornDate()));
			preparedStatement.setBigDecimal(5, resident.getIncome());
			preparedStatement.setString(6, resident.getCpf());
			preparedStatement.setInt(7, resident.getUserid());
			
			preparedStatement.execute();
			
			return InsertResidentResponseType.SUCCESS_ADDED.getResponseCode();
		} catch (SQLException sqlException) {
			if(isResidentDuplicated(sqlException)){
		        return InsertResidentResponseType.DUPLICATED.getResponseCode();
		    }
			sqlException.printStackTrace();
			
		}
		return InsertResidentResponseType.UNKNOW_ERROR.getResponseCode();
	}
	
	public boolean isInvalidDataInserted(ResidentDTO resident) {
		if((resident.getAge() > 120  ||resident.getAge() < 0 )||  
		   resident.getFirstName().isEmpty() ||
		   resident.getLastName().isEmpty() ||
		    isIncomeInvalid(resident.getIncome()) ||
		    isCpfInvalid(resident.getCpf()))
		     {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean isCpfInvalid(String cpf) {

		//No one CPF number can be composed by 11 equal numbers
		if (cpf.equals("00000000000") || cpf.equals("11111111111") || cpf.equals("22222222222")
				|| cpf.equals("33333333333") || cpf.equals("44444444444") || cpf.equals("55555555555")
				|| cpf.equals("66666666666") || cpf.equals("77777777777") || cpf.equals("88888888888")
				|| cpf.equals("99999999999") || (cpf.length() != 11)) {
			return true;
		}

		char character10, character11;
		int sum, characterPosition, r, currentNumber, weight;

		try {

			// 1st verifying digit
			sum = 0;
			weight = 10;
			for (characterPosition = 0; characterPosition < 9; characterPosition++) {
				
				currentNumber = (int) (cpf.charAt(characterPosition) - 48); //Convert the current character in a char number 
				sum = sum + (currentNumber * weight);
				weight = weight - 1;

			}

			r = 11 - (sum % 11);
			if ((r == 10) || (r == 11))
				character10 = '0';
			else
				character10 = (char) (r + 48); //Convert the current character in a char number 

			// 2nd verifying digit
			sum = 0;
			weight = 11;
			for (characterPosition = 0; characterPosition < 10; characterPosition++) {
				currentNumber = (int) (cpf.charAt(characterPosition) - 48);
				sum = sum + (currentNumber * weight);
				weight = weight - 1;
			}

			r = 11 - (sum % 11);
			if ((r == 10) || (r == 11))
				character11 = '0';
			else
				character11 = (char) (r + 48);

			// Checks if the calculated digits match the entered digits
			if ((character10 == cpf.charAt(9)) && (character11 == cpf.charAt(10)))
				return (false);
			else
				return (true);
		} catch (InputMismatchException error) {
			return (true);
		}
	}


	public boolean isIncomeInvalid(BigDecimal income) {
		
		if(income.compareTo(BigDecimal.ZERO) == 0 || income.compareTo(BigDecimal.ZERO) == -1) {
			return true;
		}
		return false;
	}
	
	public boolean isResidentDuplicated(SQLException e) {
		
		if(e.getStackTrace().length > 0) {
			
			StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            
            if(exceptionAsString.toLowerCase().contains("duplicate")) {
            	return true;
            }
            
		}
		
		return false;
	}

	public Boolean delete(Integer id) {
		
		try {
			Connection connection = new ConnectionFactory().getConnection();
			Statement stmt = connection.createStatement();
			
			ResidentDTO resident = this.getResidentById(id);
			
			stmt.execute("delete from resident where id="+id);
			
			System.out.println("O id do user é: "+resident.getUserid());

			
			
			System.out.println("Consulta executada: ");
			System.out.println("delete from resident where id="+id);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<ResidentDTO> getResidentByAge(Integer age) throws SQLException {
		
		
		Connection connection = new ConnectionFactory().getConnection();
		Statement stmt = connection.createStatement();
		
		stmt.execute("select id,firstname,lastname from resident where age > "+age);
		
		System.out.println("Consulta executada: ");
		System.out.println("select id,firstname,lastname from resident where age > "+age);
		
		ResultSet rs = stmt.getResultSet();
		
		List<ResidentDTO> residentsViaSQL = new ArrayList<>();
		while (rs.next()) {
			Integer id = rs.getInt(1);
			String firstName = rs.getString(2);
			String lastName = rs.getString(3);
			
			ResidentDTO resident = new ResidentDTO(id, firstName, lastName);
			
			residentsViaSQL.add(resident);
		}
		
		return residentsViaSQL;
	}

	public List<ResidentDTO> getResidentByMonth(Integer month) throws SQLException {
		
		Connection connection = new ConnectionFactory().getConnection();
		Statement stmt = connection.createStatement();
		
		stmt.execute("select id,firstname,lastname from resident where extract (month from borndate) ="+month);
		
		System.out.println("Consulta executada: ");
		System.out.println("select id,firstname,lastname from resident where extract (month from borndate) ="+month);
		
		ResultSet rs = stmt.getResultSet();
		
		List<ResidentDTO> residentsViaSQL = new ArrayList<>();
		while (rs.next()) {
			Integer id = rs.getInt(1);
			String firstName = rs.getString(2);
			String lastName = rs.getString(3);
			
			ResidentDTO resident = new ResidentDTO(id, firstName, lastName);
			
			residentsViaSQL.add(resident);
		}
		
		return residentsViaSQL;
	}
}
