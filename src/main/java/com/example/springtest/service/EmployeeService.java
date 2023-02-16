package com.example.springtest.service;

import com.example.springtest.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Employee save(Employee employee);

    List<Employee> getAllEmployee();

    Optional<Employee> getById(long id);

    Optional<Employee> findByEmail(String email);

    Employee updateEmployee(Employee employee);

    void deleteEmployeeById(long id);
}

