package com.sunny.ems.service;

import java.util.List;

import com.sunny.ems.entity.Employee;

public interface EmployeeService {

	Employee saveEmployee(Employee employee);

	List<Employee> getAllEmployees();

	Employee updateEmployee(Long id, Employee emp);

	Employee getEmployeeById(Long id);

	void deleteEmployee(Long id);
}
