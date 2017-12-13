package com.packtpub.springsecurity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;


@DirtiesContext
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@AutoConfigureMockMvc
@Transactional
public class CalendarApplicationTests {

    private static final Logger logger = LoggerFactory
            .getLogger(CalendarApplicationTests.class);

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ApplicationContext context;

    @Test
    public void contextLoads() {
        String[] beanNames = context.getBeanDefinitionNames();
        Arrays.sort(beanNames);

        logger.info("***** Beans in context: {}", beanNames.length);

//        Assert.assertThat(beanNames.length, is(greaterThan(5)));
    }

} // The End...
