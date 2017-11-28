package com.packtpub.springsecurity.service;

import com.packtpub.springsecurity.domain.CalendarUser;

/**
 * Manages the current {@link CalendarUser}. This demonstrates how in larger applications it is good to abstract out
 * accessing the current user to return the application specific user rather than interacting with Spring Security
 * classes directly.
 *
 * @author Rob Winch
 *
 */
public interface UserContext {

    /**
     * Gets the currently logged in {@link CalendarUser} or null if there is no authenticated user.
     *
     * @return current {@link CalendarUser}.
     */
    CalendarUser getCurrentUser();

    /**
     * Sets the currently logged in {@link CalendarUser}.
     * @param user the logged in {@link CalendarUser}. Cannot be null.
     * @throws IllegalArgumentException if the {@link CalendarUser} is null.
     */
    void setCurrentUser(final CalendarUser user);
}
