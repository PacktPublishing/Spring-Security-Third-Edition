package com.packtpub.springsecurity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest//(classes = CalendarApplication.class)
@AutoConfigureMockMvc
@Transactional
public class CalendarApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void test_user1_Login() throws Exception {
        mockMvc.perform(post("/login")
                        .accept(MediaType.TEXT_HTML)
                        .contentType(
                                MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "user1@example.com")
                        .param("password", "user1")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/default"))
                .andDo(print())
        ;
    }


} // The End...
