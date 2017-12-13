package com.packtpub.springsecurity.web.controllers;

import com.packtpub.springsecurity.CalendarUserStub;
import com.packtpub.springsecurity.core.userdetails.CalendarUserDetailsService;
import com.packtpub.springsecurity.domain.Event;
import com.packtpub.springsecurity.service.CalendarService;
import com.packtpub.springsecurity.service.UserContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.util.Calendar;

/**
 *
 * @author Mick Knutson
 *
 */
//@WithUserDetails(value="user1@example.com", userDetailsServiceBeanName="calendarUserDetailsService")
//@WithUserDetails("user1@example.com")

@RunWith(SpringRunner.class)

// Option 2: MVC Slice Test:
@WebMvcTest(EventsController.class)
// Can also Load relevant components (@Controller, @RestController, @JsonComponent etc)
public class EventsControllerMockTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserDetailsService userDetailsService;

//    @MockBean
//    private CalendarUserDetailsService calendarUserDetailsService;

    @MockBean
    private CalendarService calendarService;

    @MockBean
    private UserContext userContext;

    @Test
    public void noop() throws Exception {}

    /*



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

/*
JSON AssertJ Spring Boot extension:

    private JacksonTester<VehicleDetails> json;

    @Before
    public void setup() {
        ObjectMapper objectMappper = new ObjectMappper();
        // Possibly configure the mapper
        JacksonTester.initFields(this, objectMappper);
    }
    @Test
    public void serializeJson() {
        VehicleDetails details =
            new VehicleDetails("Honda", "Civic");
        assertThat(this.json.write(details))
            .isEqualToJson("vehicledetails.json");
        assertThat(this.json.write(details))
            .hasJsonPathStringValue("@.make");
        assertThat(this.json.write(details))
            .extractingJsonPathStringValue("@.make")
            .isEqualTo("Honda");
    }
    @Test
    public void deserializeJson() {
        String content = "{\"make\":\"Ford\",\"model\":\"Focus\"}";
        assertThat(this.json.parse(content))
            .isEqualTo(new VehicleDetails("Ford", "Focus"));
        assertThat(this.json.parseObject(content).getMake())
            .isEqualTo("Ford");
    }
 */

} // The End...