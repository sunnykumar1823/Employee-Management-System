package com.sunny.ems.service;

import org.springframework.data.domain.Page;

import com.sunny.ems.entity.Employee;

public interface EmployeeService {

	Employee saveEmployee(Employee employee);

	Page<Employee> getAllEmployees(int page, int size);

	Employee updateEmployee(Long id, Employee emp);

	Employee getEmployeeById(Long id);

	void deleteEmployee(Long id);
}
