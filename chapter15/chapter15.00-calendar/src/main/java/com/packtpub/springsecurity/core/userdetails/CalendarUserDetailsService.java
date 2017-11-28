package com.packtpub.springsecurity.core.userdetails;

import com.packtpub.springsecurity.authentication.CalendarUserAuthenticationProvider;
import com.packtpub.springsecurity.core.authority.CalendarUserAuthorityUtils;
import com.packtpub.springsecurity.dataaccess.CalendarUserDao;
import com.packtpub.springsecurity.domain.CalendarUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Integrates with Spring Security using our existing {@link CalendarUserDao} by looking up a {@link CalendarUser} and
 * converting it into a {@link UserDetails} so that Spring Security can do the username/password comparison for us.
 *
 * @author Rob Winch
 * @see CalendarUserAuthenticationProvider
 */
@Component
public class CalendarUserDetailsService implements UserDetailsService {

    private final CalendarUserDao calendarUserDao;

    @Autowired
    public CalendarUserDetailsService(CalendarUserDao calendarUserDao) {
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
        return new CalendarUserDetails(user);
    }

    /**
     * There are advantages to creating a class that extends {@link CalendarUser}, our domain notion of a user, and
     * implements {@link UserDetails}, Spring Security's notion of a user.
     * <ul>
     * <li>First we can obtain all the custom information in the {@link CalendarUser}</li>
     * <li>Second, we can use this service to integrate with Spring Security in other ways (i.e. when implementing
     * Spring Security's <a
     * href="http://static.springsource.org/spring-security/site/docs/current/reference/remember-me.html">Remember-Me
     * Authentication</a></li>
     * </ul>
     *
     * @author Rob Winch
     *
     */
    private final class CalendarUserDetails extends CalendarUser implements UserDetails {
        CalendarUserDetails(CalendarUser user) {
            setId(user.getId());
            setEmail(user.getEmail());
            setFirstName(user.getFirstName());
            setLastName(user.getLastName());
            setPassword(user.getPassword());
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return CalendarUserAuthorityUtils.createAuthorities(this);
        }

        @Override
        public String getUsername() {
            return getEmail();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }

        private static final long serialVersionUID = 3384436451564509032L;
    }
}
