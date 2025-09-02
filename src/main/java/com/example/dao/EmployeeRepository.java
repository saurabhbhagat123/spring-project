package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

    List<Employee> findByDepartment(String department);

    List<Employee> findByDepartmentAndAge(String department, Integer age);


    @Query("""
        SELECT e FROM Employee e
        WHERE (:department IS NULL OR e.department = :department)
        AND (:age IS NULL OR e.age = :age)
        """)
    List<Employee> getEmployees(String department, Integer age);
}
