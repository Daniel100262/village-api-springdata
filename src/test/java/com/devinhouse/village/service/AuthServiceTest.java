package com.devinhouse.village.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.devinhouse.village.repositories.UserCredentialRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthServiceTest {
	
	private UserService userService;
	private PasswordEncoder passwordEncoder;
	private EmailService emailService;
	private UserCredentialRepository userRepository;
	private AuthService authService;
	
	@BeforeEach
    public void beforeEach() {
		this.userService = mock(UserService.class);
		this.passwordEncoder = mock(PasswordEncoder.class);
		this.emailService = mock(EmailService.class);
		this.userRepository = mock(UserCredentialRepository.class);

		this.authService = new AuthService(userService, passwordEncoder, emailService, userRepository);
	}
	
	@Test
	void shoutReturnValidPasswordOf16Characters() {
		String generatedPassword = AuthService.generatePassword(16).toString();
		assertTrue(isPasswordValid(generatedPassword));
	}
	

	
	public Boolean isPasswordValid(String password) {
		Pattern pattern = java.util.regex.Pattern
				.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[*@#$%^&+=])(?=\\S+$).{8,}$");
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}
	
}
