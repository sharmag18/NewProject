package com.ecommerce.service;

import com.ecommerce.entity.Department;
import com.ecommerce.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    // Get all departments
    public List<Department> getAllDepartments() {
        return departmentRepository.findAllByOrderByNameAsc();
    }

    // Get department by ID
    public Optional<Department> getDepartmentById(Integer id) {
        return departmentRepository.findById(id);
    }

    // Get department by name
    public Optional<Department> getDepartmentByName(String name) {
        return departmentRepository.findByName(name);
    }

    // Create department
    public Department createDepartment(String name) {
        if (departmentRepository.existsByName(name)) {
            throw new IllegalArgumentException("Department with name '" + name + "' already exists");
        }
        Department department = new Department(name);
        return departmentRepository.save(department);
    }

    // Get or create department (useful for data migration)
    public Department getOrCreateDepartment(String name) {
        return departmentRepository.findByName(name)
                .orElseGet(() -> {
                    Department newDepartment = new Department(name);
                    return departmentRepository.save(newDepartment);
                });
    }

    // Get all department names
    public List<String> getAllDepartmentNames() {
        return departmentRepository.findAllDepartmentNames();
    }

    // Count departments
    public long countDepartments() {
        return departmentRepository.countDepartments();
    }

    // Check if department exists
    public boolean departmentExists(String name) {
        return departmentRepository.existsByName(name);
    }
} 