package com.devinhouse.village.controller.rest;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devinhouse.village.model.UserSpringSecurity;
import com.devinhouse.village.model.transport.MailDTO;
import com.devinhouse.village.service.AuthService;
import com.devinhouse.village.service.UserService;
import com.devinhouse.village.util.JWTUtil;

@RestController
@RequestMapping("/auth")
public class AuthRest {
	
	private JWTUtil jwtUtil;
	private AuthService authService;
	
	public AuthRest(JWTUtil jwtUtil,AuthService authService) {
		this.jwtUtil = jwtUtil;
		this.authService = authService;
	}

	@PostMapping("/refresh_token")
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSpringSecurity userSpringSecurity = UserService.authenticated();
		String newToken = jwtUtil.generateToken(userSpringSecurity.getUsername());
		response.addHeader("Authorization", newToken);
		return ResponseEntity.noContent().build();
	}
	

	@PostMapping("/forgot")
	public ResponseEntity<Void> forgot(@RequestBody MailDTO mail) throws Exception {
		authService.sendNewPass(mail.getEmail());
		return ResponseEntity.noContent().build();
	}
}
