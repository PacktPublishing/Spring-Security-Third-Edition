package com.packtpub.springsecurity.web.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.packtpub.springsecurity.configuration.SecurityConfig;
import com.packtpub.springsecurity.domain.CalendarUser;
import com.packtpub.springsecurity.domain.Event;
import com.packtpub.springsecurity.service.CalendarService;
import com.packtpub.springsecurity.service.UserContext;
import com.packtpub.springsecurity.web.model.CreateEventForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/events")
public class EventsController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CalendarService calendarService;
    private final UserContext userContext;

    @Autowired
    public EventsController(CalendarService calendarService, UserContext userContext) {
        this.calendarService = calendarService;
        this.userContext = userContext;
    }


    /**
     * Must be admin1 to execute:
     * http://localhost:8080/events/
     *
     * @return
     */
    @GetMapping("/")
    public Map<String, List<Event>> events() {
        Map<String, List<Event>> result = new HashMap<>();
        result.put("events", calendarService.getEvents());
        return result;
    }

    //-----------------------------------------------------------------------//

    /**
     * We add this method for demonstrating incorporating method parameters with Spring Security's @PreAuthorize based
     * security.
     *
     * http://localhost:8080/events/my/0/
     *
     * @param userId
     * @return
     */
    @GetMapping(path= "/my/{userId}")
    public Map<String, List<Event>> userEvents(@PathVariable int userId) {
        CalendarUser user = calendarService.getUser(userId);
        return myEvents(user);
    }

    private Map<String, List<Event>> myEvents(CalendarUser user) {
        Integer userId = user.getId();
        Map<String, List<Event>> result = new HashMap<>();
        result.put("currentUser", calendarService.findForUser(userId));
        return result;
    }

    //-----------------------------------------------------------------------//

    /**
     * http://localhost:8080/events/my/
     * FIXME: timestamp error
     * @return
     */
    @GetMapping("/my")
    public Map<String, List<Event>> myEvents() {
        CalendarUser currentUser = userContext.getCurrentUser();
        logger.info("/my currentUser: {}", currentUser);
        Integer currentUserId = currentUser.getId();

        Map<String, List<Event>> result = new HashMap<>();
        result.put("currentUser", calendarService.findForUser(currentUserId));

        return result;
    }

    //-----------------------------------------------------------------------//

    @GetMapping("/{eventId}")
    public String show(@PathVariable int eventId)
    throws JsonProcessingException {
        Event event = calendarService.getEvent(eventId);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(event);
        return json;
    }

    /**
     * Populates the form for creating an event with valid information. Useful so that users do not have to think when
     * filling out the form for testing.
     * http://localhost:8080/new?auto
     *
     * @param createEventForm
     * @return
     */
    @PostMapping(path = "/new", params = "auto")
    public CreateEventForm createEventFormAutoPopulate(@ModelAttribute CreateEventForm createEventForm) {
        // provide default values to make user submission easier
        createEventForm.setSummary("A new event....");
        createEventForm.setDescription("This was autopopulated to save time creating a valid event.");
        createEventForm.setWhen(Calendar.getInstance());

        // make the attendee not the current user
        CalendarUser currentUser = userContext.getCurrentUser();
        int attendeeId = currentUser.getId() == 0 ? 1 : 0;
        CalendarUser attendee = calendarService.getUser(attendeeId);
        createEventForm.setAttendeeEmail(attendee.getEmail());

        return createEventForm;
    }

    @PostMapping(value = "/new")
    public Map<String, List<?>> createEvent(@Valid CreateEventForm createEventForm,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {

        Map<String, List<?>> result = new HashMap<>();

        if (bindingResult.hasErrors()) {
            result.put("bindingResultErrors", bindingResult.getFieldErrors());
            return result;
        }
        CalendarUser attendee = calendarService.findUserByEmail(createEventForm.getAttendeeEmail());

        if (attendee == null) {
            bindingResult.rejectValue("attendeeEmail", "attendeeEmail.missing",
                    "Could not find a user for the provided Attendee Email");
        }

        if (bindingResult.hasErrors()) {
            result.put("bindingResultErrors", bindingResult.getFieldErrors());
        }

        Event event = new Event();
        event.setAttendee(attendee);
        event.setDescription(createEventForm.getDescription());
        event.setOwner(userContext.getCurrentUser());
        event.setSummary(createEventForm.getSummary());
        event.setWhen(createEventForm.getWhen());
        int eventId = calendarService.createEvent(event);

        List<String> success = new ArrayList<>();
        success.add(String.valueOf(eventId));
        success.add("Successfully added the new event");

        result.put("message", success);
        return result;
    }

} // The End...