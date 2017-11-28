package com.packtpub.springsecurity.web.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.packtpub.springsecurity.authentication.DomainUsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

/**
 * An extension to the existing {@link UsernamePasswordAuthenticationFilter} that obtains a domain parameter and then
 * creates a {@link DomainUsernamePasswordAuthenticationToken}.
 *
 * @author Rob Winch
 *
 */
//@Component
public final class DomainUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    public DomainUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager){
        super.setAuthenticationManager(authenticationManager);
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        String username = obtainUsername(request);
        String password = obtainPassword(request);
        String domain = request.getParameter("domain");

        DomainUsernamePasswordAuthenticationToken authRequest = new DomainUsernamePasswordAuthenticationToken(username,
                password, domain);

        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }
}