package com.packtpub.springsecurity.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringSecurityUserContextTests {

    @Autowired
    private UserDetailsService userDetailsService;

	@Test
	public void validateUser_User() {
        String username = "user1@example.com";

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        assertThat(userDetails.getUsername()).isEqualTo(username);
        assertThat(userDetails.getAuthorities().size()).isEqualTo(1);
    }

	@Test
	public void validateUser_Admin() {
        String username = "admin1@example.com";

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        assertThat(userDetails.getUsername()).isEqualTo(username);
        assertThat(userDetails.getAuthorities().size()).isEqualTo(2);
    }

} // The End...
