package com.api.app.repository.implementation;

import com.api.app.cnaps.mapper.CnapsMapper;
import com.api.app.cnaps.model.CnapsEmployee;
import com.api.app.cnaps.repository.CnapsRepository;
import com.api.app.model.Employee;
import com.api.app.model.exception.NotFoundException;
import com.api.app.repository.jpa.EmployeeRepository;
import com.api.app.repository.Repository;
import lombok.AllArgsConstructor;

import java.util.Optional;


@org.springframework.stereotype.Repository
@AllArgsConstructor
public class RepositoryImpl implements Repository {
    private final EmployeeRepository employeeJpaRepository;
    private final CnapsRepository cnapsRepository;
    private final CnapsMapper cnapsMapper;

    @Override
    public Employee save(Employee employee) {
        CnapsEmployee cnapsEmployee = cnapsRepository.findByEndToEndId(employee.getId()).orElse(null);
        if (cnapsEmployee != null){
            return employeeJpaRepository.save(employee);
        }
        return employeeJpaRepository.save(cnapsMapper.toDomain(employee, cnapsEmployee));
    }

    @Override
    public Employee getEmployeeById(String id) {
        CnapsEmployee cnapsEmployee = cnapsRepository.findByEndToEndId(id).orElse(null);
        Employee employee = employeeJpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee not found"));
        if (cnapsEmployee != null) {
            return employeeJpaRepository.save(cnapsMapper.toDomain(employee, cnapsEmployee));
        }
        return employeeJpaRepository.save(employee);
    }

    @Override
    public Optional<Employee> findByPrincipalUsername(String username) {
        return employeeJpaRepository.findByPrincipalUsername(username);
    }
}
