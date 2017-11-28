package com.packtpub.springsecurity.service;

import java.util.List;

import com.packtpub.springsecurity.repository.CalendarUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.packtpub.springsecurity.dataaccess.CalendarUserDao;
import com.packtpub.springsecurity.dataaccess.EventDao;
import com.packtpub.springsecurity.domain.CalendarUser;
import com.packtpub.springsecurity.domain.Event;

import javax.annotation.security.RolesAllowed;

/**
 * A default implementation of {@link CalendarService} that delegates to {@link EventDao} and {@link CalendarUserDao}.
 *
 * @author Rob Winch
 * @author Mick Knutson
 *
 */
@Repository
public class DefaultCalendarService implements CalendarService {

    private static final Logger logger = LoggerFactory
            .getLogger(DefaultCalendarService.class);

    private final EventDao eventDao;
    private final CalendarUserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DefaultCalendarService(final EventDao eventDao,
                                  final CalendarUserDao userDao,
                                  final PasswordEncoder passwordEncoder) {
        if (eventDao == null) {
            throw new IllegalArgumentException("eventDao cannot be null");
        }
        if (userDao == null) {
            throw new IllegalArgumentException("userDao cannot be null");
        }
        if (passwordEncoder == null) {
            throw new IllegalArgumentException("passwordEncoder cannot be null");
        }
        this.eventDao = eventDao;
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Event getEvent(int eventId) {
        return eventDao.getEvent(eventId);
    }

    @Override
    public int createEvent(Event event) {
        return eventDao.createEvent(event);
    }

    @Override
    public List<Event> findForUser(int userId) {
        return eventDao.findForUser(userId);
    }

    @RolesAllowed({"ROLE_ADMIN"})
    @Override
    public List<Event> getEvents() {
        return eventDao.getEvents();
    }

    @Override
    public CalendarUser getUser(int id) {
        return userDao.getUser(id);
    }

    @Override
    public CalendarUser findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    @Override
    public List<CalendarUser> findUsersByEmail(String partialEmail) {
        return userDao.findUsersByEmail(partialEmail);
    }

    /**
     * Create a new Signup User
     * @param user
     *            the new {@link CalendarUser} to create. The {@link CalendarUser#getId()} must be null.
     * @return
     */
    @Override
    public int createUser(CalendarUser user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        int userId = userDao.createUser(user);

        return userId;
    }


} // The End...
