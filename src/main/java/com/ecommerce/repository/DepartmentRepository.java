package com.ecommerce.repository;

import com.ecommerce.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    // Find department by name
    Optional<Department> findByName(String name);

    // Find all departments ordered by name
    List<Department> findAllByOrderByNameAsc();

    // Check if department exists by name
    boolean existsByName(String name);

    // Get department names only
    @Query("SELECT d.name FROM Department d ORDER BY d.name")
    List<String> findAllDepartmentNames();

    // Count departments
    @Query("SELECT COUNT(d) FROM Department d")
    long countDepartments();
} 