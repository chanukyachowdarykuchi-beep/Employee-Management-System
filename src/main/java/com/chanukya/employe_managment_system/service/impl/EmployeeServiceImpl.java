package com.chanukya.employe_managment_system.service.impl;

import com.chanukya.employe_managment_system.dto.EmployeeDto;
import com.chanukya.employe_managment_system.entity.Employee;
import com.chanukya.employe_managment_system.exception.ResourceNotFoundException;
import com.chanukya.employe_managment_system.repository.EmployeeRepository;
import com.chanukya.employe_managment_system.service.EmployeeService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        return mapToDto(employeeRepository.save(mapToEntity(employeeDto)));
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeDto getEmployeeById(Long employeeId) {
        return mapToDto(findEmployeeOrThrow(employeeId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll().stream().map(this::mapToDto).toList();
    }

    @Override
    @Transactional
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto dto) {
        Employee employee = findEmployeeOrThrow(employeeId);

        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setEmail(dto.getEmail());
        employee.setDepartment(dto.getDepartment());
        employee.setSalary(dto.getSalary());
        employee.setPhoneNumber(dto.getPhoneNumber());

        return mapToDto(employeeRepository.save(employee));
    }

    @Override
    @Transactional
    public void deleteEmployee(Long employeeId) {
        employeeRepository.delete(findEmployeeOrThrow(employeeId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDto> searchEmployees(String name, String department) {
        return employeeRepository
                .searchEmployees(normalize(name), normalize(department))
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    private Employee findEmployeeOrThrow(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id: " + employeeId));
    }

    private String normalize(String value) {
        return (value == null || value.isBlank()) ? null : value.trim();
    }

    // Entity to DTO
    private EmployeeDto mapToDto(Employee employee) {
        EmployeeDto dto = new EmployeeDto();

        dto.setId(employee.getId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setEmail(employee.getEmail());
        dto.setDepartment(employee.getDepartment());
        dto.setSalary(employee.getSalary());
        dto.setPhoneNumber(employee.getPhoneNumber());

        return dto;
    }

    // DTO to Entity
    private Employee mapToEntity(EmployeeDto dto) {
        Employee employee = new Employee();

        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setEmail(dto.getEmail());
        employee.setDepartment(dto.getDepartment());
        employee.setSalary(dto.getSalary());
        employee.setPhoneNumber(dto.getPhoneNumber());

        return employee;
    }
}