package com.api.app.service;

import com.api.app.model.Employee;
import com.api.app.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepository repository;

    public List<Employee> getEmployees() {
        return repository.findAll();
    }

    public void createEmployee(Employee employee) {
        repository.save(employee);
    }
}
