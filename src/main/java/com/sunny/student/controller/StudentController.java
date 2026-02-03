package com.sunny.student.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

	@GetMapping("/")
	public String home() {
		return "Student Management Project Running Successfully 🚀";
	}
}
