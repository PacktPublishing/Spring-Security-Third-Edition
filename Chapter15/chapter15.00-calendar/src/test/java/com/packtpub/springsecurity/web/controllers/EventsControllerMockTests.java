package com.packtpub.springsecurity.web.controllers;

import com.packtpub.springsecurity.CalendarStubs;
import com.packtpub.springsecurity.core.userdetails.CalendarUserDetailsService;
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
import org.springframework.test.web.servlet.MockMvc;

import java.util.Calendar;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 *
 * @author Mick Knutson
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(EventsControllerTests.class)
public class EventsControllerMockTests {

    @Autowired
    private MockMvc mvc;

//    @MockBean
//    private UserDetailsService userDetailsService;

    @MockBean
    private CalendarUserDetailsService calendarUserDetailsService;

    @MockBean
    private CalendarService calendarService;

    @MockBean
    private UserContext userContext;

//    @Before
//    public void setUp() throws Exception {
//        mvc = MockMvcBuilders.webAppContextSetup(wac).addFilters(springSecurityFilterChain).build();
//        MockHttpSession httpSession = new MockHttpSession(webAppContext.getServletContext(), UUID.randomUUID().toString());
//    }


        /*

    @MockBean
    private UserVehicleService userVehicleService;

    @Test
    public void getVehicleShouldReturnMakeAndModel() {
        given(this.userVehicleService.getVehicleDetails("sboot"))
            .willReturn(new VehicleDetails("Honda", "Civic"));

        this.mvc.perform(get("/sboot/vehicle")
            .accept(MediaType.TEXT_PLAIN))
            .andExpect(status().isOk())
            .andExpect(content().string("Honda Civic"));
    }
     */

//    @Test
//    public void noops(){
//        mvc.perform(get("/").session(mockSession)).perfor();
//    }

    @Test
    public void noops(){
    }

    /*

    @Test
    public void testLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
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
        mockMvc
                .perform(get("/admin/h2")
                        .header("X-Requested-With", "XMLHttpRequest")
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void test_User_MyEvents() throws Exception {
        mockMvc
                .perform(get("/events/my"))
                .andExpect(status().isOk());
    }

    @Test
    public void test_User_Event_by_User() throws Exception {
        mockMvc
                .perform(get("/events/my?userId=0"))
                .andExpect(status().isOk());
    }

    @Test
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
    public void test_AllEvents_USER() throws Exception {
        mockMvc
                .perform(get("/events/"))
                .andExpect(status().isForbidden());
    }*/


} // The End...