package com.packtpub.springsecurity.userdetails;

import java.util.Collection;

import com.packtpub.springsecurity.core.authority.CalendarUserAuthorityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.packtpub.springsecurity.dataaccess.CalendarUserDao;
import com.packtpub.springsecurity.domain.CalendarUser;

/**
 * Integrates with Spring Security using our existing {@link CalendarUserDao} by looking up a {@link CalendarUser} and
 * converting it into a {@link UserDetails} so that Spring Security can do the username/password comparison for us.
 *
 * @author Rob Winch
 *
 */
@Component
public class CalendarUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory
            .getLogger(CalendarUserDetailsService.class);

    private final CalendarUserDao calendarUserDao;

    @Autowired
    public CalendarUserDetailsService(final CalendarUserDao calendarUserDao) {

        if (calendarUserDao == null) {
            throw new IllegalArgumentException("calendarUserDao cannot be null");
        }
        this.calendarUserDao = calendarUserDao;
    }

    /**
     * Lookup a {@link CalendarUser} by the username representing the email address. Then, convert the
     * {@link CalendarUser} into a {@link UserDetails} to conform to the {@link UserDetails} interface.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CalendarUser user = calendarUserDao.findUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username/password.");
        }
        Collection<? extends GrantedAuthority> authorities = CalendarUserAuthorityUtils.createAuthorities(user);
        return new User(user.getEmail(), user.getPassword(), authorities);
    }
}
