package com.sunny.ems.security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.sunny.ems.entity.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private static final String SECRET = "mysupersecretkeyformyemsproject1234567890123456";
	private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(SECRET.getBytes());
	}

	// Generate Token with ROLE
	public String generateToken(String email, Role role) {
		return Jwts.builder().setSubject(email).claim("role", "ROLE_" + role.name()).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();

	}

	// Extract Email
	public String extractEmail(String token) {
		return extractClaims(token).getSubject();
	}

	// Extract Role
	public String extractRole(String token) {
		return extractClaims(token).get("role", String.class);
	}

	// Validate Token
	public boolean isTokenValid(String token) {
		try {
			extractClaims(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private Claims extractClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
	}
}
