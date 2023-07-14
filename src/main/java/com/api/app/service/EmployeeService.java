package com.api.app.service;

import com.api.app.model.Employee;
import com.api.app.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepository repository;

    public List<Employee> getEmployees() {
        return repository.findAll()
                .stream()
                .sorted(Comparator.comparing(Employee::getMatriculate))
                .collect(Collectors.toList());
    }

    public Employee getEmployee(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    public Employee crupdateEmployee(Employee employee) {
        return repository.save(employee);
    }
}
