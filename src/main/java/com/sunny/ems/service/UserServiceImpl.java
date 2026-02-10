package com.sunny.ems.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sunny.ems.entity.Role;
import com.sunny.ems.entity.User;
import com.sunny.ems.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	// ✅ REGISTER
	@Override
	public User register(User user) {
		user.setRole(Role.USER);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	// ✅ LOGIN / AUTHENTICATE
	@Override
	public User authenticate(String email, String Password) {

		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

		if (!passwordEncoder.matches(Password, user.getPassword())) {
			throw new RuntimeException("Invalid password");
		}

		return user;
	}

	// ✅ FIND USER
	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email).orElse(null);
	}
}
