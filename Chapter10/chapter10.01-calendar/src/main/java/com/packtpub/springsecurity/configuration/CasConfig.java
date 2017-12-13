package com.packtpub.springsecurity.configuration;

import com.packtpub.springsecurity.core.userdetails.CalendarUserDetailsService;
import com.packtpub.springsecurity.service.UserDetailsServiceImpl;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.validation.Cas20ProxyTicketValidator;
import org.jasig.cas.client.validation.Cas30ProxyTicketValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;

/**
 * @author Mick Knutson
 * @since chapter 10.00
 */
@Configuration
public class CasConfig {


    static{
        System.setProperty("cas.server", "https://localhost:9443/cas");
        System.setProperty("cas.server.login", "https://localhost:9443/cas/login");
        System.setProperty("cas.calendar.service", "https://localhost:8443");
        System.setProperty("cas.calendar.service.login", "https://localhost:8443/login");
    }

    @Value("#{systemProperties['cas.server']}")
    private static String casServer;

    @Value("#{systemProperties['cas.service']}")
    private static String casService;

    @Value("#{systemProperties['cas.server.login']}")
    private String casServerLogin;

    @Value("#{systemProperties['cas.server.login']}")
    private String calendarServiceLogin;


    @Bean
    public ServiceProperties serviceProperties(){
        return new ServiceProperties(){{
            setService(calendarServiceLogin);
        }};
    }

    @Autowired
    private CalendarUserDetailsService calendarUserDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

//    @Autowired
//    private Cas20ProxyTicketValidator ticketValidator;

    @Autowired
    private UserDetailsByNameServiceWrapper userDetailsByNameServiceWrapper;


    @Bean
    public CasAuthenticationEntryPoint casAuthenticationEntryPoint()
    throws Exception
    {
        CasAuthenticationEntryPoint entryPoint = new CasAuthenticationEntryPoint();
        entryPoint.setServiceProperties(serviceProperties());
        entryPoint.setLoginUrl(casServerLogin);
        entryPoint.afterPropertiesSet();
        return entryPoint;
    }

    @Bean
    public CasAuthenticationFilter casFilter() {
        CasAuthenticationFilter casAuthenticationFilter = new CasAuthenticationFilter();
        casAuthenticationFilter.setAuthenticationManager(authenticationManager);
        casAuthenticationFilter.setFilterProcessesUrl("/login");
        return casAuthenticationFilter;
    }



    @Bean
    public CasAuthenticationProvider casAuthenticationProvider() {
        CasAuthenticationProvider casAuthenticationProvider = new CasAuthenticationProvider();
        casAuthenticationProvider.setTicketValidator(ticketValidator());
        casAuthenticationProvider.setServiceProperties(serviceProperties());
        casAuthenticationProvider.setKey("casJbcpCalendar");
        casAuthenticationProvider.setAuthenticationUserDetailsService(userDetailsByNameServiceWrapper);
        return casAuthenticationProvider;
    }


    @Bean
    public Cas30ProxyTicketValidator ticketValidator(){
        return new Cas30ProxyTicketValidator(casServer);
    }



    /** Single point logout filter */
//    @Bean
//    public SingleSignOutFilter singleSignOutFilter() {
//        SingleSignOutFilter singleSignOutFilter = new SingleSignOutFilter();
//        singleSignOutFilter.setCasServerUrlPrefix(casProperties.getCasServerUrl());
//        singleSignOutFilter.setIgnoreInitConfiguration(true);
//        return singleSignOutFilter;
//    }

    /** Request single point exit filter */
//    @Bean
//    public LogoutFilter casLogoutFilter() {
//        LogoutFilter logoutFilter = new LogoutFilter(casProperties.getCasServerLogoutUrl(), new SecurityContextLogoutHandler());
//        logoutFilter.setFilterProcessesUrl(casProperties.getAppLogoutUrl());
//        return logoutFilter;
//    }


} // The End...
