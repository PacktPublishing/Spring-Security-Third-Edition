package com.packtpub.springsecurity.dataaccess;

import com.packtpub.springsecurity.CalendarApplication;
import com.packtpub.springsecurity.CalendarUserStub;
import com.packtpub.springsecurity.TestConfig;
import com.packtpub.springsecurity.domain.Event;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        CalendarApplication.class,
        TestConfig.class})
//@DirtiesContext
//@DataJpaTest
//@MockBean
@Transactional
public class EventDaoTests {

    @Autowired
    private JpaEventDao dao;

	@Test
    @WithUserDetails("user1@example.com")
	public void getEvent_USER_byId_USER1() {
        Event event = dao.getEvent(100);
        assertThat(event.getId()).isEqualTo(100);
        assertThat(event.getOwner()).isEqualTo(CalendarUserStub.user1());
        assertThat(event.getAttendee()).isEqualTo(CalendarUserStub.admin1());
	}

	@Test
    @WithUserDetails("admin1@example.com")
	public void getEvent_USER_byId_ADMIN1() {
        Event event = dao.getEvent(102);
        assertThat(event.getId()).isEqualTo(102);
        assertThat(event.getOwner()).isEqualTo(CalendarUserStub.admin1());
        assertThat(event.getAttendee()).isEqualTo(CalendarUserStub.user2());
	}

	@Test
    @WithUserDetails("user2@example.com")
	public void getEvent_USER_byId_USER2() {
        Event event = dao.getEvent(101);
        assertThat(event.getId()).isEqualTo(101);
        assertThat(event.getOwner()).isEqualTo(CalendarUserStub.user2());
        assertThat(event.getAttendee()).isEqualTo(CalendarUserStub.user1());
	}

    @Test
//    @WithUserDetails("user1@example.com")
	public void test_createEvent_USER() {
	    //
	}

    @Test
//    @WithUserDetails("user1@example.com")
	public void test_findForUser_USER_ById() {
	    //
	}

    @Test
//    @WithUserDetails("user1@example.com")
	public void test_findForUser_USER_ByEmail() {
	    //
	}

    @Test
//    @WithUserDetails("user1@example.com")
	public void test_getEvents_USER() {
	    //
	}


} // The End...
