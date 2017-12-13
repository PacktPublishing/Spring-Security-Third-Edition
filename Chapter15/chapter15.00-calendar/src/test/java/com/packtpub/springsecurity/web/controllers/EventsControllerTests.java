package com.packtpub.springsecurity.web.controllers;

import com.gargoylesoftware.htmlunit.WebClient;
import com.packtpub.springsecurity.CalendarStubs;
import com.packtpub.springsecurity.domain.Event;
import com.packtpub.springsecurity.service.CalendarService;
import com.packtpub.springsecurity.service.UserContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;

/**
 *
 * @author Mick Knutson
 *
 */
@DirtiesContext
@RunWith(SpringRunner.class)
//@SpringBootTest//(classes = CalendarApplication.class)
//@Transactional
//@WithUserDetails("user1@example.com")

@WebMvcTest(EventsController.class)
public class EventsControllerTests {

    @Autowired
    WebClient client;


    @MockBean
    private CalendarService calendarService;

    @MockBean
    private UserContext userContext;

    /*

    EXAMPLE:

    @MockBean
    private UserVehicleService userVehicleService;

    @Test
    public void getVehicleShouldReturnMakeAndModel() {
        given(this.userVehicleService.getVehicleDetails("sboot"))
            .willReturn(new VehicleDetails("Honda", "Civic"));
        this.client.perform(get("/sboot/vehicle")
            .accept(MediaType.TEXT_PLAIN))
            .andExpect(status().isOk())
            .andExpect(content().string("Honda Civic"));
    }
     */


//    @Before
//    public void setUp() throws Exception {
//        client = MockMvcBuilders.webAppContextSetup(wac).addFilters(springSecurityFilterChain).build();
//        MockHttpSession httpSession = new MockHttpSession(webAppContext.getServletContext(), UUID.randomUUID().toString());
//    }

//    @Test
//    public void test1(){
//        client.perform(get("/").session(mockSession)).perfor();
//    }

    @Test
    public void noops(){
    }


/*

    @Test
    public void testLogin() throws Exception {
        client.perform(MockMvcRequestBuilders.post("/login")
                .accept(MediaType.TEXT_HTML)
                .contentType(
                        MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "user1@example.com")
                .param("password", "user1").with(csrf())
        )
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithAnonymousUser
    public void securityEnabled() throws Exception {
        client
                .perform(get("/admin/h2")
                        .header("X-Requested-With", "XMLHttpRequest")
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
//    @WithUserDetails("user1@example.com")
    public void test_User_MyEvents() throws Exception {
        client
                .perform(get("/events/my"))
                .andExpect(status().isOk());
    }

    @Test
//    @WithUserDetails("user1@example.com")
    public void test_User_Event_by_User() throws Exception {
        client
                .perform(get("/events/my?userId=0"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("user1@example.com")
    public void test_Admin_Event_by_User() throws Exception {
        client
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
        client
                .perform(get("/events/"))
                .andExpect(status().isForbidden());
    }

*/


    private Event toCreate() {
        Event toCreate = new Event();
        toCreate.setWhen(Calendar.getInstance());
        toCreate.setSummary("This is a test..");
        toCreate.setDescription("of the emergency broadcast system");
        toCreate.setOwner(CalendarStubs.admin1());
        toCreate.setAttendee(CalendarStubs.user1());
        return toCreate;
    }


} // The End...