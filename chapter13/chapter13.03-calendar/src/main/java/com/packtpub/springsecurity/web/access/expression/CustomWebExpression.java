package com.packtpub.springsecurity.web.access.expression;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class CustomWebExpression {

    public boolean isLocalHost(final Authentication authentication,
                               final HttpServletRequest request) {
//        System.out.println("Server name" + request.getServerName());
//        return "localhost".equals(request.getServerName());
        return true;
    }

} // The End...