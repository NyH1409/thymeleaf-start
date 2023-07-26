package com.api.app.model.mapper;

import com.api.app.controller.response.ModelEmployee;
import com.api.app.model.Employee;
import com.api.app.model.exception.ApiException;
import com.api.app.model.exception.BadRequestException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import static java.util.UUID.randomUUID;

@Component
public class EmployeeMapper {
    private final Base64.Encoder base64Encoder = Base64.getEncoder();

    public Employee toDomain(ModelEmployee employee) {
        MultipartFile multipartFile = employee.getImage();
        try {
            String encodedImage = multipartFile != null ? base64Encoder.encodeToString(employee.getImage().getBytes()) : null;
            return Employee.builder()
                    .id(employee.getId() != null ? employee.getId() : randomUUID().toString())
                    .matriculate(employee.getMatriculate())
                    .firstName(employee.getFirstName())
                    .lastName(employee.getLastName())
                    .sex(employee.getSex())
                    .birthDate(employee.getBirthDate())
                    .image(encodedImage)
                    .principal(employee.getPrincipal())
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
            throw new ApiException(e.getMessage());
        }
    }

    public ModelEmployee toRest(Employee employee) {
            return ModelEmployee.builder()
              .id(employee.getId() != null ? employee.getId() : randomUUID().toString())
              .matriculate(employee.getMatriculate())
              .firstName(employee.getFirstName())
              .lastName(employee.getLastName())
              .sex(employee.getSex().toString())
              .birthDate(employee.getBirthDate())
              .image(null)
              .nic(employee.getNic())
              .emailPerso(employee.getEmailPerso())
              .emailPro(employee.getEmailPro())
              .phoneNumber(employee.getPhoneNumbers().get(0))
              .category(employee.getCategory().toString())
              .children(employee.getChildren())
              .cnaps(employee.getCnaps())
              .job(employee.getJob())
              .build();
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
                throw new BadRequestException("Invalid category");
        }
    }
}
