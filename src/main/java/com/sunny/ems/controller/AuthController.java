package com.sunny.ems.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sunny.ems.entity.RefreshToken;
import com.sunny.ems.entity.User;
import com.sunny.ems.security.JwtUtil;
import com.sunny.ems.service.RefreshTokenService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final RefreshTokenService refreshService;
	private final JwtUtil jwtUtil;

	public AuthController(RefreshTokenService refreshService, JwtUtil jwtUtil) {
		this.refreshService = refreshService;
		this.jwtUtil = jwtUtil;
	}

	// ✅ REFRESH TOKEN API
	@PostMapping("/refresh")
	public ResponseEntity<Map<String, String>> refreshToken(@RequestParam String refreshToken) {

		RefreshToken token = refreshService.verifyToken(refreshToken);

		User user = token.getUser();

		String newAccessToken = jwtUtil.generateToken(user.getEmail(), user.getRole());

		Map<String, String> response = new HashMap<>();
		response.put("accessToken", newAccessToken);
		response.put("refreshToken", token.getToken()); // same token reused

		return ResponseEntity.ok(response);
	}

	// ✅ LOGOUT
	@PostMapping("/logout")
	public ResponseEntity<String> logout(@RequestParam Long userId) {

		refreshService.deleteByUserId(userId);

		return ResponseEntity.ok("Logged out successfully");
	}
}
