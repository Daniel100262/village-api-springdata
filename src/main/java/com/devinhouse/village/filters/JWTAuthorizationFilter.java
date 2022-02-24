package com.devinhouse.village.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.devinhouse.village.service.UserService;
import com.devinhouse.village.util.JWTUtil;


public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private JWTUtil jwtUtil;
	private UserService userService;

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil,
			UserService userService) {
		super(authenticationManager);
		this.jwtUtil = jwtUtil;
		this.userService = userService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String header = request.getHeader("Authorization");
		if (header != null && header.startsWith("Bearer ")) {
			System.out.println("Cabeçalho token authorization: "+header.substring(7));
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = getAuthentication(
					header.substring(7));
			
			if (usernamePasswordAuthenticationToken != null) {
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			} else {
				System.out.println("usernamePasswordAuthenticationToken veio nulo em JWTAuthorizationFilter linha 42");
			}
		}
		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(String token) {
		if (jwtUtil.validToken(token)) {
			String email = jwtUtil.getEmailByToken(token);
			UserDetails user = userService.loadUserByUsername(email);
			System.out.println("UsernamePasswordAuthenticationToken Email: "+email+" user: "+user);
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
					user.getUsername(), null, user.getAuthorities());
			return usernamePasswordAuthenticationToken;
		}
		return null;
	}
}