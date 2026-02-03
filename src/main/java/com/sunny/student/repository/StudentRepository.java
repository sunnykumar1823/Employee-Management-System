package com.sunny.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunny.student.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
