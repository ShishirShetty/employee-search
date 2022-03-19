package com.example.demoApp.repository;

import com.example.demoApp.entity.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
    Employee findByName(String name);
}
