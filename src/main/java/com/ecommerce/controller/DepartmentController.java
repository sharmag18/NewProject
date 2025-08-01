package com.ecommerce.controller;

import com.ecommerce.entity.Department;
import com.ecommerce.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/departments")
@CrossOrigin(origins = "*")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    // Get all departments
    @GetMapping
    public ResponseEntity<Object> getAllDepartments() {
        try {
            List<Department> departments = departmentService.getAllDepartments();
            return ResponseEntity.ok(departments);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to retrieve departments");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Get department by ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getDepartmentById(@PathVariable Integer id) {
        try {
            Optional<Department> department = departmentService.getDepartmentById(id);
            if (department.isPresent()) {
                return ResponseEntity.ok(department.get());
            } else {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "Department not found");
                errorResponse.put("message", "Department with ID " + id + " does not exist");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        } catch (NumberFormatException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid ID format");
            errorResponse.put("message", "ID must be a valid number");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to retrieve department");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Get department by name
    @GetMapping("/name/{name}")
    public ResponseEntity<Object> getDepartmentByName(@PathVariable String name) {
        try {
            Optional<Department> department = departmentService.getDepartmentByName(name);
            if (department.isPresent()) {
                return ResponseEntity.ok(department.get());
            } else {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "Department not found");
                errorResponse.put("message", "Department with name '" + name + "' does not exist");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to retrieve department");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Create new department
    @PostMapping
    public ResponseEntity<Object> createDepartment(@RequestBody Map<String, String> request) {
        try {
            String name = request.get("name");
            if (name == null || name.trim().isEmpty()) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "Invalid request");
                errorResponse.put("message", "Department name is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }

            Department department = departmentService.createDepartment(name.trim());
            return ResponseEntity.status(HttpStatus.CREATED).body(department);
        } catch (IllegalArgumentException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Department already exists");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to create department");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Get department statistics
    @GetMapping("/stats")
    public ResponseEntity<Object> getDepartmentStats() {
        try {
            long totalDepartments = departmentService.countDepartments();
            List<String> departmentNames = departmentService.getAllDepartmentNames();

            Map<String, Object> stats = new HashMap<>();
            stats.put("totalDepartments", totalDepartments);
            stats.put("departmentNames", departmentNames);

            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to retrieve department statistics");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
} 