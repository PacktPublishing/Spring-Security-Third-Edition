package com.packtpub.springsecurity.web.controllers;

import com.packtpub.springsecurity.CalendarUserStub;
import com.packtpub.springsecurity.configuration.SecurityConfig;
import com.packtpub.springsecurity.domain.Event;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Rob Winch
 * @author Mick Knutson
 *
 */
//@DirtiesContext

@WithUserDetails("user1@example.com")

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventsControllerIntegrationTests {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TestRestTemplate template;


    @Test
    public void test__AllEvents_user1() {

        Map<String, List<Event>> events;

        ResponseEntity resonse = this.template.getForEntity(
                "/events", String.class);

        logger.info("*****************************************************");
        logger.info("*****************************************************");
        logger.info("*****************************************************");
        logger.info("getAllEvents: {}", resonse);
        /*
        getAllEvents: <401 Unauthorized,{"timestamp":1511109671176,"status":401,"error":"Unauthorized","message":"Full authentication is required to access this resource","path":"/events"},{Expires=[0], Cache-Control=[no-cache, no-store, max-age=0, must-revalidate], X-XSS-Protection=[1; mode=block], Pragma=[no-cache], X-Frame-Options=[DENY], Date=[Sun, 19 Nov 2017 16:41:11 GMT], Connection=[keep-alive], WWW-Authenticate=[Basic realm="Realm"], X-Content-Type-Options=[nosniff], Transfer-Encoding=[chunked], Content-Type=[application/json;charset=UTF-8]}>
         */
    }


    @Test
    public void noop() throws Exception {}

/*

    @Test
    @WithUserDetails("user1@example.com")
    public void test_User_MyEvents() throws Exception {
        mockMvc
                .perform(get("/events/my"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("user1@example.com")
    public void test_User_Event_by_User() throws Exception {
        mockMvc
                .perform(get("/events/my?userId=0"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("user1@example.com")
    public void test_Admin_Event_by_User() throws Exception {
        mockMvc
                .perform(get("/events/my?userId=1"))
                .andExpect(status().is5xxServerError())
                .andExpect(view().name("error"))
                .andExpect(content()
                        .string(
                                containsString("Exception during execution of Spring Security application!")
                        )
                ).andExpect(content()
                        .string(
                                containsString("Access is denied")
                        )
                );
    }

    @Test
    @WithUserDetails("user1@example.com")
    public void test_AllEvents_USER() throws Exception {
        mockMvc
                .perform(get("/events/"))
                .andExpect(status().isForbidden());
    }*/


    private Event toCreate() {
        Event toCreate = new Event();
        toCreate.setWhen(Calendar.getInstance());
        toCreate.setSummary("This is a test..");
        toCreate.setDescription("of the emergency broadcast system");
        toCreate.setOwner(CalendarUserStub.admin1());
        toCreate.setAttendee(CalendarUserStub.user1());
        return toCreate;
    }


} // The End...