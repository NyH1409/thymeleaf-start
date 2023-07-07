package com.api.app.controller;

import com.api.app.model.Employee;
import com.api.app.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@AllArgsConstructor
public class EmployeeController {
    private final EmployeeService service;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("employee", new Employee());
        return "index";
    }

    @GetMapping("/employees")
    public String getEmployees(ModelMap model) {
        service.getEmployees(model);
        return "employee";
    }

    @PostMapping(value = "/employees")
    public String createEmployee(@ModelAttribute Employee employee, Model model) {
        service.createEmployee(employee);
        return "create";
    }
}
