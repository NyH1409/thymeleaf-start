package com.api.app.controller;

import com.api.app.controller.response.RestEmployee;
import com.api.app.model.Employee;
import com.api.app.model.mapper.EmployeeMapper;
import com.api.app.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@AllArgsConstructor
public class EmployeeController {
    private final EmployeeService service;
    private final EmployeeMapper mapper;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("employee", new RestEmployee());
        return "index";
    }

    @GetMapping("/employees")
    public String getEmployees(ModelMap model) {
        List<Employee> employees = service.getEmployees();
        model.addAttribute("employees", employees);
        return "employee";
    }

    @PostMapping(value = "/employees")
    public RedirectView createEmployee(@ModelAttribute RestEmployee employee, Model model) {
        Employee domain = service.createEmployee(mapper.toDomain(employee));
        model.addAttribute("employees", List.of(domain));
        return new RedirectView("/employees");
    }
}
