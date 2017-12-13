package com.packtpub.springsecurity.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
public class EventRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    /*
    @Autowired
    private UserRepository repository;

    @Test
    public void findByUsernameShouldReturnUser() {
        this.entityManager.persist(new User("sboot", "123"));
        User user = this.repository.findByUsername("sboot");
        assertThat(user.getUsername()).isEqualTo("sboot");
        assertThat(user.getVin()).isEqualTo("123");
    }
     */
    @Autowired
    private EventRepository repository;

	@Test
	public void validateUser_Event() {
        int userId = 0;
//        List<Event> events = repository.findForUser(userId);
//        assertThat(events.get(0).getSummary()).isEqualTo("Birthday Party");
	}

}
