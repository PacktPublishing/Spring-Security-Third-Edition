package com.packtpub.springsecurity.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
public class RememberMeTokenRepositoryTests {

    @Autowired
    private RememberMeTokenRepository repository;

    /*
        PersistentLogin findBySeries(String series);
    List<PersistentLogin> findByUsername(String username);

    Iterable<PersistentLogin> findByLastUsedAfter(Date expiration);

     */
	@Test
	public void validateUser_User() {
//        PersistentLogin login = repository.findBySeries("");
//        assertThat(login.getUsername()).isEqualTo("user1@example.com");
	}

} // The End...
