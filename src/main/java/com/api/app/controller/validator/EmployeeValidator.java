package com.api.app.controller.validator;

import com.api.app.controller.response.RestEmployee;
import org.springframework.web.servlet.view.RedirectView;

import java.util.function.Consumer;

public class EmployeeValidator{
    public RedirectView accept(RestEmployee restEmployee) {
        var stringBuilder = new StringBuilder();
        if (restEmployee.getFirstName() == null){
            stringBuilder.append("FirstName is Mandatory");
        }if (restEmployee.getLastName() == null){
            stringBuilder.append("LastName is Mandatory");
        }
        if (!stringBuilder.toString().isEmpty()){
            return new RedirectView("/error");
        }
        return new RedirectView("/employees");
    }
}
