package com.example.service;

import com.example.dao.Employee;
import com.example.dao.EmployeeDto;
import com.example.dao.EmployeeRepository;
import com.example.exception.EmployeeNotFoundException;
import com.example.specification.EmployeeSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(final EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeeDto> findAll(String departmentFilter, Integer ageFilter) {
        /*
        Below one is not efficient approach

        List<EmployeeDto> list = employeeRepository.findAll()
                .stream()
                .map(this::mapEmployee)
                .collect(Collectors.toList());

        if (departmentFilter != null) {
            list = list.stream()
                    .filter(x -> x.getDepartment().equals(departmentFilter))
                    .collect(Collectors.toList());
        }
        */

        //1st Way using Specification => Dynamic Query

        /*Specification<Employee> employeeSpecification = EmployeeSpecification.departmentEquals(departmentFilter)
                .and(EmployeeSpecification.ageEquals(ageFilter));

        return employeeRepository.findAll(employeeSpecification)
                .stream()
                .map(this::mapEmployee)
                .collect(Collectors.toList());*/

        // 2nd Way Writing Custom methods in repository which in turn generate SQL
        /*return employeeRepository.findByDepartmentAndAge(departmentFilter, ageFilter)
                .stream()
                .map(this::mapEmployee)
                .collect(Collectors.toList());*/

        // 3rd way Writing JPQL
        return employeeRepository.getEmployees(departmentFilter, ageFilter)
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

    public void deleteEmployee(final Long id) {
        employeeRepository.deleteById(id);
    }

    private EmployeeDto mapEmployee(final Employee employee) {
        return EmployeeDto.builder()
                .id(employee.getId())
                .name(employee.getName())
                .age(employee.getAge())
                .salary(employee.getSalary())
                .department(employee.getDepartment())
                .build();
    }

    private Employee mapEmployeeDto(final EmployeeDto dto) {
        return Employee.builder()
                .id(dto.getId())
                .name(dto.getName())
                .age(dto.getAge())
                .salary(dto.getSalary())
                .department(dto.getDepartment())
                .build();
    }
}
