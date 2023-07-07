package com.api.app.model.mapper;

import com.api.app.controller.response.RestEmployee;
import com.api.app.model.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {
    public RestEmployee toRest(Employee employee){
        return RestEmployee.builder()
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .birthDate(employee.getBirthDate())
                .image(null)
                .build();
    }
}
