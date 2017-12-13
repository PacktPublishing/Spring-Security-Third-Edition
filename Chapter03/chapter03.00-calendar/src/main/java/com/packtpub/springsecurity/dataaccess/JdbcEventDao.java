package com.packtpub.springsecurity.dataaccess;

import com.packtpub.springsecurity.domain.CalendarUser;
import com.packtpub.springsecurity.domain.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

/**
 * A jdbc implementation of {@link EventDao}.
 *
 * @author Rob Winch
 *
 */
@Repository
public class JdbcEventDao implements EventDao {

    // --- members ---

    private final JdbcOperations jdbcOperations;

    // --- constructors ---

    @Autowired
    public JdbcEventDao(JdbcOperations jdbcOperations) {
        if (jdbcOperations == null) {
            throw new IllegalArgumentException("jdbcOperations cannot be null");
        }
        this.jdbcOperations = jdbcOperations;
    }

    // --- EventService ---

    @Override
    @Transactional(readOnly = true)
    public Event getEvent(int eventId) {
        return jdbcOperations.queryForObject(EVENT_QUERY + " and e.id = ?", EVENT_ROW_MAPPER, eventId);
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
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcOperations.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(
                        "insert into events (when,summary,description,owner,attendee) values (?, ?, ?, ?, ?)",
                        new String[] { "id" });
                ps.setDate(1, new java.sql.Date(when.getTimeInMillis()));
                ps.setString(2, event.getSummary());
                ps.setString(3, event.getDescription());
                ps.setInt(4, owner.getId());
                ps.setObject(5, attendee == null ? null : attendee.getId());
                return ps;
            }
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Event> findForUser(int userId) {
        return jdbcOperations.query(EVENT_QUERY + " and (e.owner = ? or e.attendee = ?) order by e.id", EVENT_ROW_MAPPER, userId, userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Event> getEvents() {
        return jdbcOperations.query(EVENT_QUERY+" order by e.id", EVENT_ROW_MAPPER);
    }

    // --- non-public static members ---

    /**
     * A RowMapper for mapping a {@link Event}
     */
    private static final RowMapper<Event> EVENT_ROW_MAPPER = new RowMapper<Event>() {
        public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
            CalendarUser attendee = ATTENDEE_ROW_MAPPER.mapRow(rs, rowNum);
            CalendarUser owner = OWNER_ROW_MAPPER.mapRow(rs, rowNum);

            Event event = new Event();
            event.setId(rs.getInt("events.id"));
            event.setSummary(rs.getString("events.summary"));
            event.setDescription(rs.getString("events.description"));
            Calendar when = Calendar.getInstance();
            when.setTime(rs.getDate("events.when"));
            event.setWhen(when);
            event.setAttendee(attendee);
            event.setOwner(owner);
            return event;
        }
    };

    private static final RowMapper<CalendarUser> ATTENDEE_ROW_MAPPER = new JdbcCalendarUserDao.CalendarUserRowMapper("attendee_");
    private static final RowMapper<CalendarUser> OWNER_ROW_MAPPER = new JdbcCalendarUserDao.CalendarUserRowMapper("owner_");

    private static final String EVENT_QUERY = "select e.id, e.summary, e.description, e.when, " +
            "owner.id as owner_id, owner.email as owner_email, owner.password as owner_password, owner.first_name as owner_first_name, owner.last_name as owner_last_name, " +
            "attendee.id as attendee_id, attendee.email as attendee_email, attendee.password as attendee_password, attendee.first_name as attendee_first_name, attendee.last_name as attendee_last_name " +
            "from events as e, calendar_users as owner, calendar_users as attendee " +
            "where e.owner = owner.id and e.attendee = attendee.id";
}
