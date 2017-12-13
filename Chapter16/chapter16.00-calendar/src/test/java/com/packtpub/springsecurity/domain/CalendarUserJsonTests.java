package com.packtpub.springsecurity.domain;

import com.packtpub.springsecurity.CalendarUserStub;
import com.packtpub.springsecurity.domain.CalendarUser;
import com.packtpub.springsecurity.domain.Event;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Calendar;

/**
 *
 * @author Mick Knutson
 *
 */
@RunWith(SpringRunner.class)
@JsonTest
public class CalendarUserJsonTests {


    private JacksonTester<CalendarUser> json;


    @Test
    public void noop() throws Exception {}

    /*

    @Test
    public void serializeJson() {
        VehicleDetails details = new VehicleDetails(
            "Honda", "Civic");

        assertThat(this.json.write(details))
            .extractingJsonPathStringValue("@.make")
            .isEqualTo("Honda");
    }

    */



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