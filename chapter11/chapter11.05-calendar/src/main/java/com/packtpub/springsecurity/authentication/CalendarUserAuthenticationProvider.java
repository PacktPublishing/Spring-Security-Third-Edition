package com.packtpub.springsecurity.authentication;

import com.packtpub.springsecurity.core.authority.CalendarUserAuthorityUtils;
import com.packtpub.springsecurity.core.userdetails.CalendarUserDetailsService;
import com.packtpub.springsecurity.domain.CalendarUser;
import com.packtpub.springsecurity.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * A Spring Security {@link AuthenticationProvider} that uses our {@link CalendarService} for authentication. Compare
 * this to our {@link CalendarUserDetailsService} which is called by Spring Security's {@link DaoAuthenticationProvider}.
 *
 * @author Rob Winch
 * @see CalendarUserDetailsService
 */
// Resolves a circular dependency:
//@Component
public class CalendarUserAuthenticationProvider implements AuthenticationProvider {

    private final CalendarService calendarService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CalendarUserAuthenticationProvider(final CalendarService calendarService,
                                              final PasswordEncoder passwordEncoder) {
        if (calendarService == null) {
            throw new IllegalArgumentException("calendarService cannot be null");
        }
        if (passwordEncoder == null) {
            throw new IllegalArgumentException("passwordEncoder cannot be null");
        }

        this.calendarService = calendarService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        String email = token.getName();
        CalendarUser user = email == null ? null : calendarService.findUserByEmail(email);
        if(user == null) {
            throw new UsernameNotFoundException("Invalid username/password");
        }
        // Database Password already encrypted:
        String password = user.getPassword();

        boolean passwordsMatch = passwordEncoder.matches(token.getCredentials().toString(), password);

        if(!passwordsMatch) {
            throw new BadCredentialsException("Invalid username/password");
        }
        Collection<? extends GrantedAuthority> authorities = CalendarUserAuthorityUtils.createAuthorities(user);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, password, authorities);
        return usernamePasswordAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}
