package com.api.app.model.mapper;

import com.api.app.controller.response.ModelEmployee;
import com.api.app.model.Employee;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import static java.util.UUID.randomUUID;

@Component
public class EmployeeMapper {
    private final Base64.Encoder base64Encoder = Base64.getEncoder();

    public Employee toDomain(ModelEmployee employee) {
        try {
            String encodedImage = base64Encoder.encodeToString(employee.getImage().getBytes());
            return Employee.builder()
              .id(randomUUID().toString())
              .matriculate(employee.getMatriculate())
              .firstName(employee.getFirstName())
              .lastName(employee.getLastName())
              .sex(sexFromString(employee.getSex()))
              .birthDate(employee.getBirthDate())
              .image(encodedImage)
              .nic(employee.getNic())
              .emailPerso(employee.getEmailPerso())
              .emailPro(employee.getEmailPro())
              .phoneNumbers(List.of(employee.getPhoneNumber()))
              .category(categoryFromString(employee.getCategory()))
              .children(employee.getChildren())
              .cnaps(employee.getCnaps())
              .job(employee.getJob())
              .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Employee.Sex sexFromString(String sex) {
        switch (sex) {
            case "H":
                return Employee.Sex.H;
            case "F":
                return Employee.Sex.F;
            default:
                throw new RuntimeException("Invalid sex");
        }
    }

    private Employee.Category categoryFromString(String category) {
        switch (category) {
            case "M1":
                return Employee.Category.M1;
            case "M2":
                return Employee.Category.M2;
            case "OS1":
                return Employee.Category.OS1;
            case "OS2":
                return Employee.Category.OS2;
            case "OS3":
                return Employee.Category.OS3;
            case "OP1":
                return Employee.Category.OP1;
            default:
                throw new RuntimeException("Invalid category");
        }
    }
}
