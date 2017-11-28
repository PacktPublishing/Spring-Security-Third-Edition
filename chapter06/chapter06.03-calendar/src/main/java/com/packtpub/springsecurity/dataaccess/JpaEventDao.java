package com.packtpub.springsecurity.dataaccess;

import com.packtpub.springsecurity.domain.CalendarUser;
import com.packtpub.springsecurity.domain.Event;
import com.packtpub.springsecurity.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

/**
 * A jdbc implementation of {@link EventDao}.
 *
 * @author Rob Winch
 * @author Mick Knutson
 *
 */
@Repository
public class JpaEventDao implements EventDao {

    // --- members ---

    private EventRepository repository;

    // --- constructors ---

    @Autowired
    public JpaEventDao(EventRepository repository) {
        if (repository == null) {
            throw new IllegalArgumentException("repository cannot be null");
        }
        this.repository = repository;
    }

    // --- EventService ---

    @Override
    @Transactional(readOnly = true)
    public Event getEvent(int eventId) {
        return repository.findOne(eventId);
    }

    @Override
    public int createEvent(final Event event) {
        if (event == null) {
            throw new IllegalArgumentException("event cannot be null");
        }
        if (event.getId() != null) {
            throw new IllegalArgumentException("event.getId() must be null when creating a new Message");
        }
        final CalendarUser owner = event.getOwner();
        if (owner == null) {
            throw new IllegalArgumentException("event.getOwner() cannot be null");
        }
        final CalendarUser attendee = event.getAttendee();
        if (attendee == null) {
            throw new IllegalArgumentException("attendee.getOwner() cannot be null");
        }
        final Calendar when = event.getWhen();
        if(when == null) {
            throw new IllegalArgumentException("event.getWhen() cannot be null");
        }
        Event newEvent = repository.save(event);
        return newEvent.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Event> findForUser(final int userId) {
        Event example = new Event();
        CalendarUser cu = new CalendarUser();
        cu.setId(userId);
        example.setOwner(cu);

        return repository.findAll(Example.of(example));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Event> getEvents() {
        return repository.findAll();
    }

} // The End...
