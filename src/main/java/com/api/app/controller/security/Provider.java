package com.api.app.controller.security;

import com.api.app.model.Employee;
import com.api.app.model.Session;
import com.api.app.model.exception.ForbiddenException;
import com.api.app.service.EmployeeService;
import com.api.app.service.SessionService;
import com.api.app.service.handler.SessionHandler;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Base64;
import java.util.Iterator;

import static java.util.UUID.randomUUID;

@Component
@AllArgsConstructor
public class Provider {
    private final Base64.Decoder decoder = Base64.getDecoder();
    @Autowired
    private ObjectFactory<HttpSession> sessionFactory;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private SessionService sessionService;
    private final SessionHandler sessionHandler = SessionHandler.getInstance();

    public void authenticate(Principal principal) {
        Employee auth = employeeService.getEmployeeByEmail(principal.getUsername());
        Principal authPrincipal = auth != null ? auth.getPrincipal() : null;
        if (authPrincipal != null && Arrays.equals(decoder.decode(authPrincipal.getPassword()), principal.getPassword().getBytes())) {
            Session currentSession = sessionService.save(new Session(randomUUID().toString(), authPrincipal));
            sessionFactory.getObject().setAttribute(currentSession.getId(), currentSession.getId());
            sessionHandler.setSessionFactory(sessionFactory);
        } else {
            throw new ForbiddenException("Access denied");
        }
    }

    public void clearSession() {
        Iterator<String> iterator = sessionHandler.getSessionFactory().getAttributeNames().asIterator();
        if (iterator.hasNext()) {
            sessionHandler.getSessionFactory().removeAttribute(iterator.next());
            sessionService.deleteSession(iterator.next());
        }
    }
}
