
package com.chanukya.employe_managment_system.controller;

import com.chanukya.employe_managment_system.dto.EmployeeDto;
import com.chanukya.employe_managment_system.service.EmployeeService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    // POST /api/employees
    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(
            @Valid @RequestBody EmployeeDto employeeDto) {

        EmployeeDto savedEmployee =
                employeeService.createEmployee(employeeDto);

        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    // GET /api/employees/{id}
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(
            @PathVariable Long id) {

        EmployeeDto employeeDto =
                employeeService.getEmployeeById(id);

        return ResponseEntity.ok(employeeDto);
    }

    // GET /api/employees
    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {

        List<EmployeeDto> employees =
                employeeService.getAllEmployees();

        return ResponseEntity.ok(employees);
    }

    // PUT /api/employees/{id}
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeDto updatedEmployee) {

        EmployeeDto employeeDto =
                employeeService.updateEmployee(id, updatedEmployee);

        return ResponseEntity.ok(employeeDto);
    }

    // DELETE /api/employees/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(
            @PathVariable Long id) {

        employeeService.deleteEmployee(id);

        return ResponseEntity.noContent().build();
    }

    // GET /api/employees/search
    @GetMapping("/search")
    public ResponseEntity<List<EmployeeDto>> searchEmployees(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String department) {

        List<EmployeeDto> employees =
                employeeService.searchEmployees(name, department);

        return ResponseEntity.ok(employees);
    }

    // GET /api/employees/test
    @GetMapping("/test")
    public String test() {
        return "Employee Controller is working!";
    }
}
