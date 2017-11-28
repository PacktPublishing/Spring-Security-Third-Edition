package com.packtpub.springsecurity.authentication;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.packtpub.springsecurity.domain.CalendarUser;

public final class DomainUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private final String domain;

    public DomainUsernamePasswordAuthenticationToken(String principal, String credentials, String domain) {
        super(principal, credentials);
        this.domain = domain;
    }

    public DomainUsernamePasswordAuthenticationToken(CalendarUser principal, String credentials, String domain,
            Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
        this.domain = domain;
    }

    public String getDomain() {
        return domain;
    }

    private static final long serialVersionUID = -5138870746127783L;
}
