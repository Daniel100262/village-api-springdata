package com.devinhouse.village.service;

import java.util.Random;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.devinhouse.village.exception.NullUserException;
import com.devinhouse.village.model.UserCredential;
import com.devinhouse.village.repositories.UserCredentialRepository;

@Service
public class AuthService {
	
	private UserService userService;
	private PasswordEncoder passwordEncoder;
	private EmailService emailService;
	private UserCredentialRepository userRepository;
	
	public AuthService(UserService userService, PasswordEncoder passwordEncoder, EmailService emailService, UserCredentialRepository userRepository) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
		this.emailService = emailService;
	}
	
	public void sendNewPass(String email) {
		UserCredential user = userRepository.getUserByEmail(email);
		
		if(user != null) {
			String newPass = generatePassword();
			String encodePass = passwordEncoder.encode(newPass);
			user.setPassword(encodePass);
			
			userService.updateUser(user,newPass);
			
			emailService.sendNewPassword(user, newPass);
		} else {
			throw new NullUserException("Nenhum usuário com este e-mail não encontrado!");
		}
		
	}
	
	private static String generatePassword() {
		return new String(generatePassword(12));
	}
	
	   public static char[] generatePassword(int length) {
		      String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		      String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
		      String specialCharacters = "!@#$*&-=+";
		      String numbers = "1234567890";
		      String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
		      Random random = new Random();
		      char[] password = new char[length];

		      password[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
		      password[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
		      password[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
		      password[3] = numbers.charAt(random.nextInt(numbers.length()));
		   
		      int ii = 1;
		      
		      for(int i = 4; i < length ; i++) {
		    	  
		    	  System.out.println("Passou "+ii+" vezes, i="+i);
		    	  ii++;
		         password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
		      }
		      return password;
		   }
}
