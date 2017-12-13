package com.packtpub.springsecurity.service;

import com.packtpub.springsecurity.domain.CalendarUser;
import com.packtpub.springsecurity.domain.Event;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

public interface CalendarService {

    /**
     * Gets a {@link CalendarUser} for a specific {@link CalendarUser#getId()}.
     *
     * @param id
     *            the {@link CalendarUser#getId()} of the {@link CalendarUser} to find.
     * @return a {@link CalendarUser} for the given id. Cannot be null.
     * @throws EmptyResultDataAccessException
     *             if the {@link CalendarUser} cannot be found
     */
    CalendarUser getUser(int id);

    /**
     * Finds a given {@link CalendarUser} by email address.
     *
     * @param email
     *            the email address to use to find a {@link CalendarUser}. Cannot be null.
     * @return a {@link CalendarUser} for the given email or null if one could not be found.
     * @throws IllegalArgumentException
     *             if email is null.
     */
    CalendarUser findUserByEmail(String email);

    /**
     * Finds any {@link CalendarUser} that has an email that starts with {@code partialEmail}.
     *
     * @param partialEmail
     *            the email address to use to find {@link CalendarUser}s. Cannot be null or empty String.
     * @return a List of {@link CalendarUser}s that have an email that starts with given partialEmail. The returned
     *         value will never be null. If no results are found an empty List will be returned.
     * @throws IllegalArgumentException
     *             if email is null or empty String.
     */
    List<CalendarUser> findUsersByEmail(String partialEmail);

    /**
     * Creates a new {@link CalendarUser}.
     *
     * @param user
     *            the new {@link CalendarUser} to create. The {@link CalendarUser#getId()} must be null.
     * @return the new {@link CalendarUser#getId()}.
     * @throws IllegalArgumentException
     *             if {@link CalendarUser#getId()} is non-null.
     */
    int createUser(CalendarUser user);

    /**
     * Given an id gets an {@link Event}.
     *
     * @param eventId
     *            the {@link Event#getId()}
     * @return the {@link Event}. Cannot be null.
     * @throws RuntimeException
     *             if the {@link Event} cannot be found.
     */
    Event getEvent(int eventId);

    /**
     * Creates a {@link Event} and returns the new id for that {@link Event}.
     *
     * @param event
     *            the {@link Event} to create. Note that the {@link Event#getId()} should be null.
     * @return the new id for the {@link Event}
     * @throws RuntimeException
     *             if {@link Event#getId()} is non-null.
     */
    int createEvent(Event event);

    /**
     * Finds the {@link Event}'s that are intended for the {@link CalendarUser}.
     *
     * @param userId
     *            the {@link CalendarUser#getId()} to obtain {@link Event}'s for.
     * @return a non-null {@link List} of {@link Event}'s intended for the specified {@link CalendarUser}. If the
     *         {@link CalendarUser} does not exist an empty List will be returned.
     */
    List<Event> findForUser(int userId);

    /**
     * Gets all the available {@link Event}'s.
     *
     * @return a non-null {@link List} of {@link Event}'s
     */
    List<Event> getEvents();
}
