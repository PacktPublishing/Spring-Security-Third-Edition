package com.packtpub.springsecurity.dataaccess;

import com.packtpub.springsecurity.domain.CalendarUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CalendarUserDaoTests {

    @Autowired
    private CalendarUserDao dao;

	@Test
	public void validateUser_User() {
        String email = "user1@example.com";
        CalendarUser user = dao.findUserByEmail(email);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getRoles().size()).isEqualTo(1);
//        assertThat(user.getFirstName()).as("check %s's username", user.getEmail()).isEqualTo("foo@bar.com");
	}

	@Test
	public void validateUser_Admin() {
	    String email = "admin1@example.com";
        CalendarUser user = dao.findUserByEmail(email);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getRoles().size()).isEqualTo(2);
	}

	@Test
	public void test_getUser() {
	    //
	}

	@Test
	public void test_findUserByEmail() {
	    //
	}

	@Test
	public void test_findUsersByEmail() {
	    //
	}

	@Test
	public void test_createUser() {
	    //
	}

}
