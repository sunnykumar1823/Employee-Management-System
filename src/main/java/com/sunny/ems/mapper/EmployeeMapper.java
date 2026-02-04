package com.sunny.ems.mapper;

import com.sunny.ems.dto.EmployeeRequestDTO;
import com.sunny.ems.dto.EmployeeResponseDTO;
import com.sunny.ems.entity.Employee;

public class EmployeeMapper {

	public static Employee toEntity(EmployeeRequestDTO dto) {

		Employee emp = new Employee();
		emp.setName(dto.getName());
		emp.setEmail(dto.getEmail());
		emp.setDepartment(dto.getDepartment());
		emp.setSalary(dto.getSalary());

		return emp;
	}

	public static EmployeeResponseDTO toDTO(Employee emp) {

		EmployeeResponseDTO dto = new EmployeeResponseDTO();
		dto.setId(emp.getId());
		dto.setName(emp.getName());
		dto.setEmail(emp.getEmail());
		dto.setDepartment(emp.getDepartment());
		dto.setSalary(emp.getSalary());

		return dto;
	}
}
