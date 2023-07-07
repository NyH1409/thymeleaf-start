package com.api.app.handler;

import org.springframework.beans.factory.ObjectFactory;

import javax.servlet.http.HttpSession;

public class SessionHandler {
    private static SessionHandler SESSION_HANDLER;
    private ObjectFactory<HttpSession> session;

    private SessionHandler(){};

    public static SessionHandler getInstance(){
        if (SESSION_HANDLER == null){
            SESSION_HANDLER = new SessionHandler();
        }
        return SESSION_HANDLER;
    }


    @Override
    public String toString() {
        return super.toString();
    }

    public HttpSession getSession() {
        return session.getObject();
    }

    public void setSession(ObjectFactory<HttpSession> session) {
        this.session = session;
    }
}
