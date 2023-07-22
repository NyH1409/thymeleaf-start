package com.api.app.controller;

import com.api.app.controller.response.ModelEmployee;
import com.api.app.controller.response.ModelToCSV;
import com.api.app.model.Employee;
import com.api.app.model.mapper.EmployeeMapper;
import com.api.app.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Controller
@AllArgsConstructor
public class EmployeeController {
    private final EmployeeService service;
    private final EmployeeMapper mapper;

    @GetMapping("/create")
    public String createPage(Model model) {
        model.addAttribute("employee", new ModelEmployee());
        return "create";
    }

    @GetMapping("/")
    public String getEmployees(ModelMap model) {
        List<Employee> employees = service.getEmployees();
        ModelToCSV modelToCSV = new ModelToCSV(employees);
        model.addAttribute("employees", employees);
        model.addAttribute("modelToCSV", modelToCSV);
        return "index";
    }

    @GetMapping("/employees/{id}")
    public String getEmployee(ModelMap model, @PathVariable("id") String employeeId) {
        Employee employee = service.getEmployee(employeeId);
        model.addAttribute("employee", employee);
        return "profile";
    }

    @PostMapping(value = "/employees")
    public RedirectView createEmployee(@ModelAttribute ModelEmployee employee) {
        service.crupdateEmployee(mapper.toDomain(employee));
        return new RedirectView("/");
    }

    @PostMapping(value = "/employees/raw")
    public RedirectView generateCSV(@ModelAttribute ModelToCSV modelToCSV, HttpServletResponse response) {
        service.generateCSV(modelToCSV.getEmployees(), response);
        return new RedirectView("/");
    }

    @PostMapping(value = "/employee/{id}")
    public RedirectView updateEmployee(@PathVariable("id") String employeeId,
                                       @RequestParam("photo") MultipartFile multipartFile) {
        Employee toUpdate = service.getEmployee(employeeId);
        try {
            service.crupdateEmployee(toUpdate.toBuilder()
                    .image(Base64.getEncoder()
                            .encodeToString(multipartFile.getBytes())).build());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new RedirectView("/");
    }
}
