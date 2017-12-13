package com.packtpub.springsecurity.core.authority;

import com.packtpub.springsecurity.CalendarStubs;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

//import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CalendarUserAuthorityUtilsTests {

	@Test
	public void createAuthorities_User() {
        Collection<? extends GrantedAuthority> roles =
                CalendarUserAuthorityUtils.createAuthorities(CalendarStubs.user1());

        System.out.println("*****************************************");
        System.out.println("roles: " + roles);

//        assertThat(roles,
//                containsInAnyOrder(
//                        new SimpleGrantedAuthority("USER_ROLE")));
    }

	@Test
	public void validateUser_Admin() {
        Collection<? extends GrantedAuthority> roles =
                CalendarUserAuthorityUtils.createAuthorities(CalendarStubs.admin1());

        System.out.println("*****************************************");
        System.out.println("roles: " + roles);

//        assertThat(roles,
//                containsInAnyOrder(
//                        new SimpleGrantedAuthority("ADMIN_ROLE")));
    }

} // The End...
