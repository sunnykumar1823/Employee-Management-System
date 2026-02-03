package com.sunny.ems.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sunny.ems.entity.Employee;
import com.sunny.ems.exception.ResourceNotFoundException;
import com.sunny.ems.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeRepository repo;

	public EmployeeServiceImpl(EmployeeRepository repo) {
		this.repo = repo;
	}

	@Override
	public Employee saveEmployee(Employee employee) {
		return repo.save(employee);
	}

	@Override
	public List<Employee> getAllEmployees() {
		return repo.findAll();
	}

	@Override
	public Employee updateEmployee(Long id, Employee emp) {

		Employee existing = repo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

		existing.setName(emp.getName());
		existing.setEmail(emp.getEmail());
		existing.setDepartment(emp.getDepartment());
		existing.setSalary(emp.getSalary());

		return repo.save(existing);
	}

	@Override
	public void deleteEmployee(Long id) {

		Employee emp = repo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

		repo.delete(emp);
	}

	@Override
	public Employee getEmployeeById(Long id) {

		return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
	}

}
