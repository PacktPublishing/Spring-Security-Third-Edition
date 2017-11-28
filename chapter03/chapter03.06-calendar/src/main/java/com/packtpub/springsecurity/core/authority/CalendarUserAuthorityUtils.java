package com.packtpub.springsecurity.core.authority;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import com.packtpub.springsecurity.domain.CalendarUser;

/**
 * A utility class used for creating the {@link GrantedAuthority}'s given a {@link CalendarUser}. In a real solution
 * this would be looked up in the existing system, but for simplicity our original system had no notion of authorities.
 *
 * @author Rob Winch
 * @author Mick Knutson
 *
 */
public final class CalendarUserAuthorityUtils {
    private static final List<GrantedAuthority> ADMIN_ROLES = AuthorityUtils.createAuthorityList("ROLE_ADMIN",
            "ROLE_USER");
    private static final List<GrantedAuthority> USER_ROLES = AuthorityUtils.createAuthorityList("ROLE_USER");

    public static Collection<? extends GrantedAuthority> createAuthorities(CalendarUser calendarUser) {
        String username = calendarUser.getEmail();
        if (username.startsWith("admin")) {
            return ADMIN_ROLES;
        }
        return USER_ROLES;
    }

    private CalendarUserAuthorityUtils() {
    }

}
