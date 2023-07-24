package com.api.app.controller.security;

import com.api.app.model.Employee;
import com.api.app.model.exception.ForbiddenException;
import com.api.app.service.EmployeeService;
import com.api.app.service.handler.SessionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
@AllArgsConstructor
public class AuthProvider {
    private final ObjectFactory<HttpSession> sessionFactory;
    private final ObjectMapper objectMapper;
    private final EmployeeService employeeService;
    private final SessionHandler sessionHandler = SessionHandler.getInstance();

    public void authenticate(Principal principal) {
        Employee auth = employeeService.getEmployeeByEmail(principal.getUsername());
        if (auth != null){
            sessionFactory.getObject().setAttribute(String.valueOf(Instant.now()), principal);
            sessionHandler.setSessionFactory(sessionFactory);
        } else {
            throw new ForbiddenException("Access denied");
        }
    }

    public String isUserAuthenticated(String template) {
        sessionHandler.setSessionFactory(sessionFactory);
        Iterator<String> iterator = sessionHandler.getSessionFactory().getAttributeNames().asIterator();
        List<Principal> principals = new ArrayList<>();
        if (iterator.hasNext()) {
            Object current = sessionHandler.getSessionFactory().getAttribute(iterator.next());
            Principal principal = objectMapper.convertValue(current, Principal.class);
            principals.add(principal);
        }
        if (!principals.isEmpty()) {
            return template;
        } else {
            throw new ForbiddenException("Access denied");
        }
    }

    public void clearSession() {
        Iterator<String> iterator = sessionHandler.getSessionFactory().getAttributeNames().asIterator();
        if (iterator.hasNext()) {
            sessionHandler.getSessionFactory().removeAttribute(iterator.next());
        }
    }
}
