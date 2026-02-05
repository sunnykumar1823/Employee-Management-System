package com.sunny.ems.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sunny.ems.dto.LoginRequest;
import com.sunny.ems.entity.RefreshToken;
import com.sunny.ems.entity.User;
import com.sunny.ems.security.JwtUtil;
import com.sunny.ems.service.RefreshTokenService;
import com.sunny.ems.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;
	private final RefreshTokenService refreshTokenService; // ✅ added

	public UserController(UserService userService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil,
			RefreshTokenService refreshTokenService // ✅ added
	) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
		this.refreshTokenService = refreshTokenService;
	}

	// ✅ REGISTER
	@PostMapping("/register")
	public User register(@RequestBody User user) {
		return userService.register(user);
	}

	// ✅ LOGIN + ACCESS TOKEN + REFRESH TOKEN
	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request) {

		User user = userService.authenticate(request.getEmail(), request.getPassword());

		String accessToken = jwtUtil.generateToken(user.getEmail(), user.getRole());

		RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

		Map<String, String> response = new HashMap<>();
		response.put("accessToken", accessToken);
		response.put("refreshToken", refreshToken.getToken());

		return ResponseEntity.ok(response);
	}

	// 🔐 PROTECTED API
	@GetMapping("/profile")
	public String profile() {
		return "USER PROFILE ACCESS ✅";
	}
}
