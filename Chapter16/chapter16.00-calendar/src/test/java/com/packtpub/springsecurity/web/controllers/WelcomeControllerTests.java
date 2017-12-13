package com.packtpub.springsecurity.web.controllers;

import com.packtpub.springsecurity.CalendarApplication;
import com.packtpub.springsecurity.authentication.CalendarUserAuthenticationProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 *
 * @author Mick Knutson
 *
 */
@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CalendarApplication.class)
@AutoConfigureMockMvc//(secure=false)
@Transactional
public class WelcomeControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private CalendarUserAuthenticationProvider authenticationProvider;

    @Test
    public void noop() throws Exception {}

    /*@Test
    @WithAnonymousUser
    public void test_welcome_WithAnonymousUser() throws Exception {
        mockMvc
                .perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andDo(print())
        ;
    }

    @Test
    public void test_welcome() throws Exception {
        mockMvc
                .perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andDo(print())
        ;
    }

    @Test
    @WithUserDetails("admin1@example.com")
    public void test_admin1_showCreateLink() throws Exception {
//        mockMvc
//                .perform(get("/showCreateLink"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/events/"))
//                .andDo(print())
//        ;
    }*/





    private Authentication userAuth(){
        String username = "user1@example.com";
        String password = "user1";

        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(username, password);
        return authenticationProvider.authenticate(authRequest);
    }

    private Authentication adminAuth(){
        String username = "admin@example.com";
        String password = "admin";

        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(username, password);
        return authenticationProvider.authenticate(authRequest);
    }


} // The End...