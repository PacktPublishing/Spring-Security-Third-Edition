package com.packtpub.springsecurity.dataaccess;

import java.util.List;

import com.packtpub.springsecurity.domain.Event;
import com.packtpub.springsecurity.domain.CalendarUser;

/**
 * An interface for managing {@link Event}'s.
 *
 * @author Rob Winch
 *
 */
public interface EventDao {

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
