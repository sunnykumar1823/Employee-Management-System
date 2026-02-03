package com.sunny.ems.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sunny.ems.dto.LoginRequest;
import com.sunny.ems.dto.LoginResponse;
import com.sunny.ems.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	// LOGIN API
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

		String token = userService.login(request.getEmail(), request.getPassword());

		return ResponseEntity.ok(new LoginResponse(token));
	}
}
