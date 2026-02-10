package com.sunny.ems.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	// ✅ CREATE (ADMIN only)
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<EmployeeResponseDTO> save(@Valid @RequestBody EmployeeRequestDTO dto) {

		Employee emp = EmployeeMapper.toEntity(dto);
		Employee saved = service.saveEmployee(emp);

		return new ResponseEntity<>(EmployeeMapper.toDTO(saved), HttpStatus.CREATED);
	}

	// ✅ PAGINATION + JWT (USER & ADMIN)
	@GetMapping
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<List<EmployeeResponseDTO>> getAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {

		Page<Employee> employees = service.getAllEmployees(page, size);

		List<EmployeeResponseDTO> response = employees.getContent().stream().map(EmployeeMapper::toDTO)
				.collect(Collectors.toList());

		return ResponseEntity.ok(response);
	}

	// ✅ GET BY ID
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<EmployeeResponseDTO> getById(@PathVariable Long id) {
		return ResponseEntity.ok(EmployeeMapper.toDTO(service.getEmployeeById(id)));
	}

	// ✅ UPDATE (ADMIN)
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<EmployeeResponseDTO> update(@PathVariable Long id,
			@Valid @RequestBody EmployeeRequestDTO dto) {

		Employee emp = EmployeeMapper.toEntity(dto);
		Employee updated = service.updateEmployee(id, emp);

		return ResponseEntity.ok(EmployeeMapper.toDTO(updated));
	}

	// ✅ DELETE (ADMIN)
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.deleteEmployee(id);
		return ResponseEntity.noContent().build();
	}
}
