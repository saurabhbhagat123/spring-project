package com.example.service;

import com.example.dao.Employee;
import com.example.dao.EmployeeDto;
import com.example.dao.EmployeeRepository;
import com.example.exception.EmployeeNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(final EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeeDto> findAll() {
        return employeeRepository.findAll()
                .stream()
                .map(this::mapEmployee)
                .collect(Collectors.toList());
    }

    public EmployeeDto getEmployee(Long id) {
        return employeeRepository.findById(id)
                .map(this::mapEmployee)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
    }

    public EmployeeDto saveEmployee(final EmployeeDto employeeDto) {
        if (employeeDto.getId() != null) {
            getEmployee(employeeDto.getId());
        }

        Employee employee = mapEmployeeDto(employeeDto);
        employee = employeeRepository.save(employee);

        return mapEmployee(employee);
    }

    private EmployeeDto mapEmployee(final Employee employee) {
        return EmployeeDto.builder()
                .id(employee.getId())
                .name(employee.getName())
                .age(employee.getAge())
                .salary(employee.getSalary())
                .build();
    }

    private Employee mapEmployeeDto(final EmployeeDto dto) {
        return Employee.builder()
                .id(dto.getId())
                .name(dto.getName())
                .age(dto.getAge())
                .salary(dto.getSalary())
                .build();
    }
}
