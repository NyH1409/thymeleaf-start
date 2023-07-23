package com.api.app.controller;

import com.api.app.controller.response.ModelEmployee;
import com.api.app.controller.response.ModelToCSV;
import com.api.app.model.Company;
import com.api.app.model.Employee;
import com.api.app.model.mapper.EmployeeMapper;
import com.api.app.service.CompanyService;
import com.api.app.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@AllArgsConstructor
public class EmployeeController {
    private final EmployeeService service;
    private final EmployeeMapper mapper;
    private final CompanyService companyService;

    @GetMapping("/create")
    public String createPage(Model model) {
        model.addAttribute("employee", new ModelEmployee());
        List<Company> companies = companyService.getCompanies();
        model.addAttribute("company", companies.get(0));
        return "create";
    }

    @GetMapping("/")
    public String getEmployees(ModelMap model,
                               @RequestParam(value = "firstName", required = false) String fistName,
                               @RequestParam(value = "lastName", required = false) String lastName,
                               @RequestParam(value = "sex", required = false) String sex,
                               @RequestParam(value = "job", required = false) String job,
                               @RequestParam(value = "firstNameOrder", required = false) String firstNameOrder,
                               @RequestParam(value = "lastNameOrder", required = false) String lastNameOrder,
                               @RequestParam(value = "sexOrder", required = false) String sexOrder,
                               @RequestParam(value = "jobOrder", required = false) String jobOrder,
                               @RequestParam(value = "page", required = false) Integer page,
                               @RequestParam(value = "pageSize", required = false) Integer pageSize
                               ) {
        List<Employee> employees = service.getEmployees(
          fistName, lastName, sex, job, firstNameOrder, lastNameOrder, sexOrder, jobOrder, page, pageSize);
        List<Company> companies = companyService.getCompanies();
        ModelToCSV modelToCSV = new ModelToCSV(employees);
        model.addAttribute("company", companies.get(0));
        model.addAttribute("employees", employees);
        model.addAttribute("modelToCSV", modelToCSV);
        return "index";
    }

    @GetMapping("/employees/{id}")
    public String getEmployee(ModelMap model, @PathVariable("id") String employeeId) {
        Employee employee = service.getEmployee(employeeId);
        List<Company> companies = companyService.getCompanies();
        model.addAttribute("company", companies.get(0));
        model.addAttribute("employee", employee);
        return "profile";
    }

    @GetMapping("/employees/{id}/edit")
    public String updateEmployee(ModelMap model, @PathVariable("id") String employeeId) {
        Employee employee = service.getEmployee(employeeId);
        ModelEmployee modelEmployee = mapper.toRest(employee);
        List<Company> companies = companyService.getCompanies();
        model.addAttribute("company", companies.get(0));
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
