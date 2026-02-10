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

	// 🔁 ROTATING REFRESH
	@PostMapping("/refresh")
	public ResponseEntity<Map<String, String>> refresh(@RequestParam String refreshToken) {

		RefreshToken newToken = refreshService.verifyAndRotate(refreshToken);

		User user = newToken.getUser();

		String newAccess = jwtUtil.generateToken(user.getEmail(), user.getRole());

		Map<String, String> response = new HashMap<>();
		response.put("accessToken", newAccess);
		response.put("refreshToken", newToken.getToken());

		return ResponseEntity.ok(response);
	}

	// 🚪 LOGOUT
	@PostMapping("/logout")
	public ResponseEntity<String> logout(@RequestParam Long userId) {

		refreshService.deleteByUserId(userId);
		return ResponseEntity.ok("Logged out");
	}
}
