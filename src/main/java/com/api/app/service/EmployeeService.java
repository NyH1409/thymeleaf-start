package com.api.app.service;

import com.api.app.handler.SessionHandler;
import com.api.app.model.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Slf4j
public class EmployeeService {
    private final ObjectFactory<HttpSession> session;
    private final SessionHandler sessionHandler = SessionHandler.getInstance();

    public EmployeeService(ObjectFactory<HttpSession> session) {
        this.session = session;
    }

    public void getEmployees(ModelMap model) {
        Iterator<String> keyList = sessionHandler.getSession().getAttributeNames().asIterator();
        List<Employee> employees = new ArrayList<>();
        var firstEmployee = sessionHandler.getSession().getAttribute(keyList.next());
        employees.add(new ObjectMapper().convertValue(firstEmployee, Employee.class));
        while (keyList.hasNext()) {
            var tempEmployee = sessionHandler.getSession().getAttribute(keyList.next());
            employees.add(new ObjectMapper().convertValue(tempEmployee, Employee.class));
        }
        model.addAttribute("employees", employees);
    }

    public void createEmployee(Employee employee) {
        session.getObject().setAttribute(String.valueOf(Instant.now()), employee);
        sessionHandler.setSession(session);
    }
}
