package com.packtpub.springsecurity.service;

import com.packtpub.springsecurity.dataaccess.CalendarUserDao;
import com.packtpub.springsecurity.dataaccess.EventDao;
import com.packtpub.springsecurity.domain.CalendarUser;
import com.packtpub.springsecurity.domain.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * A default implementation of {@link CalendarService} that delegates to {@link EventDao} and {@link CalendarUserDao}.
 *
 * @author Rob Winch
 * @author Mick Knutson
 *
 */
@Repository
public class DefaultCalendarService implements CalendarService {
    private final EventDao eventDao;
    private final CalendarUserDao userDao;
    private final UserDetailsManager userDetailsManager;

    @Autowired
    public DefaultCalendarService(final EventDao eventDao,
                                  final CalendarUserDao userDao,
                                  final UserDetailsManager userDetailsManager) {
        if (eventDao == null) {
            throw new IllegalArgumentException("eventDao cannot be null");
        }
        if (userDao == null) {
            throw new IllegalArgumentException("userDao cannot be null");
        }
        if (userDetailsManager == null) {
            throw new IllegalArgumentException("userDetailsManager cannot be null");
        }
        this.eventDao = eventDao;
        this.userDao = userDao;
        this.userDetailsManager = userDetailsManager;
    }

    public Event getEvent(final int eventId) {
        return eventDao.getEvent(eventId);
    }

    public int createEvent(final Event event) {
        return eventDao.createEvent(event);
    }

    public List<Event> findForUser(final int userId) {
        return eventDao.findForUser(userId);
    }

    public List<Event> getEvents() {
        return eventDao.getEvents();
    }

    public CalendarUser getUser(final int id) {
        return userDao.getUser(id);
    }

    public CalendarUser findUserByEmail(final String email) {
        return userDao.findUserByEmail(email);
    }

    public List<CalendarUser> findUsersByEmail(final String partialEmail) {
        return userDao.findUsersByEmail(partialEmail);
    }

    public int createUser(final CalendarUser user) {
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
        UserDetails userDetails = new User(user.getEmail(), user.getPassword(), authorities);
        userDetailsManager.createUser(userDetails);

        return userDao.createUser(user);
    }
}