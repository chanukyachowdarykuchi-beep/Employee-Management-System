
package com.chanukya.employe_managment_system.repository;

import com.chanukya.employe_managment_system.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Find employee by email
    Optional<Employee> findByEmail(String email);

    // Find employees by department
    List<Employee> findByDepartment(String department);

    // Search employees by name and department
    @Query("SELECT e FROM Employee e WHERE " +
            "(:name IS NULL OR LOWER(e.firstName) LIKE LOWER(CONCAT('%', :name, '%')) " +
            "OR LOWER(e.lastName) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:department IS NULL OR LOWER(e.department) = LOWER(:department))")
    List<Employee> searchEmployees(
            @Param("name") String name,
            @Param("department") String department
    );
}
