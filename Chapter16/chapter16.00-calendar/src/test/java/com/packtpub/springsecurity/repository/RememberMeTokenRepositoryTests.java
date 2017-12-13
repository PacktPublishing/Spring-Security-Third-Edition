package com.packtpub.springsecurity.repository;

import com.packtpub.springsecurity.domain.PersistentLogin;
import com.packtpub.springsecurity.domain.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
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
