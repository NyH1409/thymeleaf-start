package com.api.app.model.mapper;

import com.api.app.controller.response.RestEmployee;
import com.api.app.model.Employee;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

import static java.util.UUID.randomUUID;

@Component
public class EmployeeMapper {
    private final Base64.Encoder base64Encoder = Base64.getEncoder();

    public Employee toDomain(RestEmployee employee) {
        try {
            String encodedImage = base64Encoder.encodeToString(employee.getImage().getBytes());
            return Employee.builder()
                    .id(randomUUID().toString())
                    .matriculate(employee.getMatriculate())
                    .firstName(employee.getFirstName())
                    .lastName(employee.getLastName())
                    .birthDate(employee.getBirthDate())
                    .image(encodedImage)
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
