package com.packtpub.springsecurity.configuration;

import com.packtpub.springsecurity.repository.RememberMeTokenRepository;
import com.packtpub.springsecurity.web.authentication.rememberme.JpaTokenRepositoryCleaner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author Mick Knutson
 * @since chapter 01.00
 */
@Configuration
@Import({SecurityConfig.class})
@EnableScheduling
public class JavaConfig {

    @Autowired
    private RememberMeTokenRepository rememberMeTokenRepository;

    @Scheduled(fixedRate = 600_000)
    public void tokenRepositoryCleaner(){
        Thread trct = new Thread(
                new JpaTokenRepositoryCleaner(
                        rememberMeTokenRepository,
                        100_000L));
        trct.start();
    }

} // The End...
