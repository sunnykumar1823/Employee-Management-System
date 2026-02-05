package com.sunny.ems.service;

import com.sunny.ems.entity.User;

public interface UserService {

	User register(User user);

	User login(String email, String password);

	User findByEmail(String email);

	User authenticate(String email, String password);

}
