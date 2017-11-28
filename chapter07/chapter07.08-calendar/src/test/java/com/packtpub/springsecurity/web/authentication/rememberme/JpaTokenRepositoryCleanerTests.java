package com.packtpub.springsecurity.web.authentication.rememberme;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Mick Knutson
 *
 */
@DirtiesContext
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc//(secure=false)
@Transactional
@DataJpaTest
public class JpaTokenRepositoryCleanerTests {

    private Logger logger = LoggerFactory.getLogger(getClass());

    //    @Autowired
    private JpaTokenRepositoryCleaner repository;

    @Test
    public void noop(){}



} // The End...
