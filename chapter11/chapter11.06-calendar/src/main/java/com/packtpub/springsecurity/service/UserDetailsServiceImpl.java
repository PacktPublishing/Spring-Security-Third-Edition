package com.packtpub.springsecurity.service;

import com.packtpub.springsecurity.authentication.CalendarUserAuthenticationProvider;
import com.packtpub.springsecurity.dataaccess.CalendarUserDao;
import com.packtpub.springsecurity.domain.CalendarUser;
import com.packtpub.springsecurity.domain.Role;
import com.packtpub.springsecurity.repository.CalendarUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * Custom JPA based {@link UserDetailsService}
 * Note, this does not support both User and Role objects, only User.
 *
 * We dont use this {@link UserDetailsService}, we need to use:
 * @see {@link CalendarUserAuthenticationProvider}
 *
 * @author Mick Knutson
 */
//@Service
@Deprecated
public class UserDetailsServiceImpl implements UserDetailsService {

    private final CalendarUserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(CalendarUserRepository userRepository) {
        if (userRepository == null) {
            throw new IllegalArgumentException("userRepository cannot be null");
        }
        this.userRepository = userRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

        CalendarUser user = userRepository.findByEmail(username);

        if (user == null)
            throw new UsernameNotFoundException("username " + username
                    + " not found");

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
    }

} // The End...
