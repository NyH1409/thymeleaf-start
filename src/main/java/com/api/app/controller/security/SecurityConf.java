package com.api.app.controller.security;

import com.api.app.model.Session;
import com.api.app.model.exception.ForbiddenException;
import com.api.app.service.SessionService;
import com.api.app.service.handler.SessionHandler;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpSession;
import java.util.Iterator;

@Configuration
@AllArgsConstructor
public class SecurityConf {
    private final ObjectFactory<HttpSession> session;
    private final SessionService sessionService;
    private final SessionHandler sessionHandler = SessionHandler.getInstance();

    public Session configure() {
        sessionHandler.setSessionFactory(session);
        HttpSession currentSession = sessionHandler.getSessionFactory();
        Iterator<String> attributes = currentSession.getAttributeNames().asIterator();
        if (attributes.hasNext()) {
            Session session = sessionService.getSessionById(attributes.next());
            if (session != null) {
                return session;
            }
            throw new ForbiddenException("Access denied");
        }
        throw new ForbiddenException("Access denied");
    }
}
