package com.sunny.ems.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sunny.ems.dto.LoginRequest;
import com.sunny.ems.entity.User;
import com.sunny.ems.security.JwtUtil;
import com.sunny.ems.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

	public UserController(UserService userService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
	}

	// ✅ REGISTER
	@PostMapping("/register")
	public User register(@RequestBody User user) {
		return userService.register(user);
	}

	// ✅ LOGIN + JWT ROLE TOKEN
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request) {

		User user = userService.findByEmail(request.getEmail());

		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			return ResponseEntity.status(401).body("Invalid credentials");
		}

		String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

		return ResponseEntity.ok(token);
	}

	// 🔐 USER access
	@GetMapping("/profile")
	public String profile() {
		return "USER PROFILE ACCESS ✅";
	}
}
