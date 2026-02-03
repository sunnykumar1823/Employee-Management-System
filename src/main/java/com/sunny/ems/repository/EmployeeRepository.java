package com.sunny.ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunny.ems.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
