package com.packtpub.springsecurity.service;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.packtpub.springsecurity.core.authority.CalendarUserAuthorityUtils;
import com.packtpub.springsecurity.domain.CalendarUser;

/**
 * An implementation of {@link UserContext} that looks up the {@link CalendarUser} using the Spring Security's
 * {@link Authentication} by principal name.
 *
 * @author Rob Winch
 *
 */
@Component
public class SpringSecurityUserContext implements UserContext {
    /**
     * Get the {@link CalendarUser} by casting the {@link Authentication}'s principal to a {@link CalendarUser}.
     */
    @Override
    public CalendarUser getCurrentUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (authentication == null) {
            return null;
        }
        return (CalendarUser) authentication.getPrincipal();
    }

    /**
     * Sets the {@link CalendarUser} as the current {@link Authentication}'s principal. It uses
     */
    @Override
    public void setCurrentUser(CalendarUser user) {
        if (user == null) {
            throw new IllegalArgumentException("user cannot be null");
        }
        Collection<? extends GrantedAuthority> authorities = CalendarUserAuthorityUtils.createAuthorities(user);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user,
                user.getPassword(),authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
