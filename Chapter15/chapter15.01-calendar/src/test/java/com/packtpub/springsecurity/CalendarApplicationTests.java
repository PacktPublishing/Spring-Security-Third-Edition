package com.packtpub.springsecurity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CalendarApplication.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@AutoConfigureMockMvc
@Transactional
public class CalendarApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void noop() throws Exception {}

    /*@Test
    @WithAnonymousUser
    public void securityEnabled() throws Exception {
        mockMvc
                .perform(get("/admin/h2")
                        .header("X-Requested-With", "XMLHttpRequest")
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void test_failed_Login() throws Exception {
        mockMvc.perform(post("/login")
                .accept(MediaType.TEXT_HTML)
                .contentType(
                        MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "bob@example.com")
                .param("password", "bob1")
//                .with(csrf())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login/form?error"))
                .andDo(print())
        ;
    }

    @Test
    public void test_user1_Login() throws Exception {
        mockMvc.perform(post("/login")
                .accept(MediaType.TEXT_HTML)
                .contentType(
                        MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "user1@example.com")
                .param("password", "user1")
//                .with(csrf())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/default"))
                .andDo(print())
        ;
    }

    @Test
    public void test_admin1_Login() throws Exception {
        mockMvc.perform(post("/login")
                .accept(MediaType.TEXT_HTML)
                .contentType(
                        MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "admin1@example.com")
                .param("password", "admin1")
//                .with(csrf())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/default"))
                .andDo(print())
        ;
    }*/


} // The End...
