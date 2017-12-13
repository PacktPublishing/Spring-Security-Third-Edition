package com.packtpub.springsecurity.repository;

import com.packtpub.springsecurity.domain.CalendarUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@DataJpaTest
//@EnableJpaAuditing
public class CalendarUserRepositoryTests {

    @Autowired
    private CalendarUserRepository repository;

	@Test
	public void validateUser_User() {
        String username = "user1@example.com";
        CalendarUser user = repository.findByEmail(username);
        assertThat(user.getEmail()).isEqualTo(username);
        assertThat(user.getRoles().size()).isEqualTo(1);
//        assertThat(user.getFirstName()).as("check %s's username", user.getEmail()).isEqualTo("foo@bar.com");
	}

	@Test
	public void validateUser_Admin() {
	    String username = "admin1@example.com";
        CalendarUser user = repository.findByEmail(username);
        assertThat(user.getEmail()).isEqualTo(username);
        assertThat(user.getRoles().size()).isEqualTo(2);
	}

}
