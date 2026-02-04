package com.sunny.ems.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sunny.ems.dto.EmployeeRequestDTO;
import com.sunny.ems.dto.EmployeeResponseDTO;
import com.sunny.ems.entity.Employee;
import com.sunny.ems.mapper.EmployeeMapper;
import com.sunny.ems.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	private final EmployeeService service;

	public EmployeeController(EmployeeService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<EmployeeResponseDTO> save(@Valid @RequestBody EmployeeRequestDTO dto) {

		Employee emp = EmployeeMapper.toEntity(dto);
		Employee saved = service.saveEmployee(emp);

		return new ResponseEntity<>(EmployeeMapper.toDTO(saved), HttpStatus.CREATED);
	}

	@GetMapping
	public List<EmployeeResponseDTO> getAll() {

		return service.getAllEmployees().stream().map(EmployeeMapper::toDTO).collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	public EmployeeResponseDTO getById(@PathVariable Long id) {

		return EmployeeMapper.toDTO(service.getEmployeeById(id));
	}

	@PutMapping("/{id}")
	public EmployeeResponseDTO update(@PathVariable Long id, @Valid @RequestBody EmployeeRequestDTO dto) {

		Employee emp = EmployeeMapper.toEntity(dto);
		return EmployeeMapper.toDTO(service.updateEmployee(id, emp));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {

		service.deleteEmployee(id);
		return ResponseEntity.noContent().build();
	}
}
