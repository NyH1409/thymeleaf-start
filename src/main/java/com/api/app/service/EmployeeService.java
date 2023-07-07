package com.api.app.service;

import com.api.app.handler.SessionHandler;
import com.api.app.model.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class EmployeeService {
    private final ObjectFactory<HttpSession> session;
    private final ObjectMapper objectMapper;
    private final SessionHandler sessionHandler = SessionHandler.getInstance();

    public EmployeeService(ObjectFactory<HttpSession> session, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.session = session;
    }

    public void getEmployees(ModelMap model) {
        Iterator<String> keyList = sessionHandler.getSession().getAttributeNames().asIterator();
        List<Employee> employees = new ArrayList<>();
        var firstEmployee = sessionHandler.getSession().getAttribute(keyList.next());
        employees.add(mapToEmployee(firstEmployee));
        while (keyList.hasNext()) {
            var tempEmployee = sessionHandler.getSession().getAttribute(keyList.next());
            employees.add(mapToEmployee(tempEmployee));
        }
        model.addAttribute("employees", employees);
    }

    public void createEmployee(Employee employee) {
        session.getObject().setAttribute(String.valueOf(Instant.now()), employee);
        sessionHandler.setSession(session);
    }

    private Employee mapToEmployee(Object stringEmployee) {
        return objectMapper
                .convertValue(stringEmployee, Employee.class)
                .toBuilder()
                .birthDate(Date.from(Instant.now()))
                .build();
    }
}
