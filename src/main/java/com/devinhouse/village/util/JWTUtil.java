package com.devinhouse.village.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {

	private String secret;
	private Long expiration;

	public JWTUtil(@Value("${jwt.secret}") String secret, @Value("${jwt.expiration}") Long expiration) {
		this.secret = secret;
		this.expiration = expiration;
	}

	public String generateToken(String email) {
		return Jwts.builder().setSubject(email).setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public boolean validToken(String token) {
		Claims claims = getClaims(token);
		if (claims != null) {
			String email = claims.getSubject();
			Date expiration = claims.getExpiration();
			Date now = new Date(System.currentTimeMillis());
			return email != null && expiration != null && now.before(expiration);
		}
		return false;
	}

	private Claims getClaims(String token) {
		try {
			Jws<Claims> parseClaimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			return parseClaimsJws.getBody();
		} catch (Exception e) {
			return null;
		}
	}

	public String getEmailByToken(String token) {
		Claims claims = getClaims(token);
		if (claims != null) {
			return claims.getSubject();
		}
		return null;
	}

}