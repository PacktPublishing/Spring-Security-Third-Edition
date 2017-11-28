package com.packtpub.springsecurity.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.session.ConcurrentSessionFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.session.SimpleRedirectSessionInformationExpiredStrategy;

/**
 * @author Mick Knutson
 * @since chapter 14.05
 */
@Configuration
public class SessionConfig {

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    /**
     * sessionAuthenticationStrategy does not work in JavaConfig
     * @param sessionRegistry
     * @return
     */
//    @Bean
    public SessionAuthenticationStrategy sessionAuthenticationStrategy(SessionRegistry sessionRegistry){
        return new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry){{
            setMaximumSessions(-1);
        }};
    }

//    @Bean
    public ConcurrentSessionFilter concurrentSessionFilter(){
        SimpleRedirectSessionInformationExpiredStrategy expiredSessionStrategy =
                new SimpleRedirectSessionInformationExpiredStrategy("/login/form?expired");
        return new ConcurrentSessionFilter(sessionRegistry(), expiredSessionStrategy);
    }

    @Bean
    public SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl();
    }


} // The End...
