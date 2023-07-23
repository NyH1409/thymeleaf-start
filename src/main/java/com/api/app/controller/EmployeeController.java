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
import java.util.stream.Collectors;

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

    @GetMapping("/employees/{id}/edit")
    public String updateEmployee(ModelMap model, @PathVariable("id") String employeeId) {
        Employee employee = service.getEmployee(employeeId);
        ModelEmployee modelEmployee = new ModelEmployee();
        model.addAttribute("employee", employee);
        model.addAttribute("modelEmployee", modelEmployee);
        return "edit";
    }

    @PostMapping(value = "/employees")
    public RedirectView createEmployee(@ModelAttribute ModelEmployee employee) {
        service.crupdateEmployee(mapper.toDomain(employee));
        return new RedirectView("/");
    }

    @PostMapping(value = "/employees/{id}")
    public RedirectView updateEmployee(@PathVariable("id") String id, @ModelAttribute ModelEmployee employee) {
        service.crupdateEmployee(mapper.toDomain(employee.toBuilder().id(id).build()));
        return new RedirectView("/");
    }

    @PostMapping(value = "/employees/raw")
    public RedirectView generateCSV(@ModelAttribute ModelToCSV modelToCSV, HttpServletResponse response) {
        service.generateCSV(modelToCSV.getEmployees(), response);
        return new RedirectView("/");
    }

}
