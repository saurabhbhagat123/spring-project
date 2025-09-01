package com.example.controller;

import com.example.dao.EmployeeDto;
import com.example.exception.EmployeeNotFoundException;
import com.example.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {


    private final EmployeeService employeeService;

    public EmployeeController(final EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    /*@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EmployeeDto> getEmployees(@RequestParam(name = "department", required = false) String department,
                                          @RequestParam(name = "age", required = false) String age) {
        System.out.println(department);
        System.out.println(age);
        return List.of(new EmployeeDto("abc", 1000L), new EmployeeDto("def", 2000L));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeDto getEmployee(@PathVariable("id") final int id) {
        return new EmployeeDto("abc", 1,1000L);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDto> addEmployee(@RequestBody final EmployeeDto employeeDto) {
        return new ResponseEntity<>(new EmployeeDto("abc", 1000L), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeDto updateEmployee(@PathVariable("id") final int id, @RequestBody final EmployeeDto employeeDto) {
        return new EmployeeDto("abc", 1000L);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") final int id) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }*/


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EmployeeDto> getEmployees(@RequestParam(name = "department", required = false) String department,
                                          @RequestParam(name = "age", required = false) String age) {
        return employeeService.findAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeDto getEmployee(@PathVariable("id") final Long id) {
        return employeeService.getEmployee(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDto> addEmployee(@RequestBody final EmployeeDto employeeDto) {
        return new ResponseEntity<>(employeeService.saveEmployee(employeeDto), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeDto updateEmployee(@PathVariable("id") final Long id, @RequestBody final EmployeeDto employeeDto) {
        employeeDto.setId(id);
        return employeeService.saveEmployee(employeeDto);
    }
}
