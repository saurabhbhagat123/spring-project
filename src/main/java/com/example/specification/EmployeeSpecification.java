package com.example.specification;

import com.example.dao.Employee;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpecification {

    public static Specification<Employee> departmentEquals(String department) {
        return (root, query, builder) -> {
            if (department == null) {
                return builder.conjunction();
            }

            return builder.equal(root.get("department"), department);
        };
    }


    public static Specification<Employee> ageEquals(Integer age) {
        return (root, query, builder) -> {
            if (age == null) {
                return builder.conjunction();
            }

            return builder.equal(root.get("age"), age);
        };
    }
}
