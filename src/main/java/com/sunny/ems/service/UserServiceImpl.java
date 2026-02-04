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

	@Override
	public User register(User user) {

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		// ✅ DEFAULT ROLE
		user.setRole(Role.USER);

		return userRepository.save(user);
	}

	@Override
	public User login(String email, String password) {

		User user = findByEmail(email);

		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new RuntimeException("Invalid credentials");
		}

		return user;
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
	}
}
