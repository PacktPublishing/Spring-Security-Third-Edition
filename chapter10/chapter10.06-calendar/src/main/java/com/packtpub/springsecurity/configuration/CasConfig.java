package com.packtpub.springsecurity.configuration;

import com.packtpub.springsecurity.core.userdetails.CalendarUserDetailsService;
import com.packtpub.springsecurity.service.UserDetailsServiceImpl;
import org.jasig.cas.client.proxy.ProxyGrantingTicketStorage;
import org.jasig.cas.client.proxy.ProxyGrantingTicketStorageImpl;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.jasig.cas.client.validation.Cas20ProxyTicketValidator;
import org.jasig.cas.client.validation.Cas30ProxyTicketValidator;
import org.jasig.cas.client.validation.Saml11TicketValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.authentication.EhCacheBasedTicketCache;
import org.springframework.security.cas.userdetails.GrantedAuthorityFromAssertionAttributesUserDetailsService;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.cas.web.authentication.ServiceAuthenticationDetailsSource;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDateTime;

import net.sf.ehcache.Cache;

/**
 * @author Mick Knutson
 * @since chapter 10.00
 */
@Configuration
@EnableScheduling
public class CasConfig {

    private static final Logger logger = LoggerFactory
            .getLogger(CasConfig.class);

    static{
        System.setProperty("cas.server", "https://localhost:9443/cas");
        System.setProperty("cas.calendar.service", "https://localhost:8443");
    }

    @Value("#{systemProperties['cas.server']}")
    private static String casServer;

    @Value("#{systemProperties['cas.calendar.service']}")
    private static String casService;

    @Value("#{systemProperties['cas.server']}/login")
    private String casServerLogin;

    @Value("#{systemProperties['cas.server']}/logout")
    private String casServerLogout;

    @Value("#{systemProperties['cas.calendar.service']}/login")
    private String calendarServiceLogin;

    @Value("#{systemProperties['cas.calendar.service']}/pgtUrl")
    private String calendarServiceProxyCallbackUrl;


    @Bean
    public ServiceProperties serviceProperties(){
        return new ServiceProperties(){{
            setService(calendarServiceLogin);
            setAuthenticateAllArtifacts(true);
        }};
    }

    @Autowired
    private AuthenticationManager authenticationManager;

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
        CasAuthenticationFilter caf = new CasAuthenticationFilter();
        caf.setAuthenticationManager(authenticationManager);
        caf.setFilterProcessesUrl("/login");
        caf.setProxyGrantingTicketStorage(pgtStorage());
        caf.setProxyReceptorUrl("/pgtUrl");

        caf.setServiceProperties(serviceProperties());
        caf.setAuthenticationDetailsSource(
                new ServiceAuthenticationDetailsSource(
                        serviceProperties())
        );
        return caf;
    }


    @Bean
    public CasAuthenticationProvider casAuthenticationProvider() {
        CasAuthenticationProvider cap = new CasAuthenticationProvider();
        cap.setTicketValidator(ticketValidator());
        cap.setServiceProperties(serviceProperties());
        cap.setKey("casJbcpCalendar");
        cap.setAuthenticationUserDetailsService(userDetailsByNameServiceWrapper);

        cap.setStatelessTicketCache(ehCacheBasedTicketCache());

        return cap;
    }


    @Bean
    public EhCacheBasedTicketCache ehCacheBasedTicketCache() {
        EhCacheBasedTicketCache cache = new EhCacheBasedTicketCache();
        cache.setCache(ehcache());
        return cache;
    }

    @Bean(initMethod = "initialise", destroyMethod = "dispose")
    public Cache ehcache() {
        Cache cache = new Cache("casTickets",
                50,
                true,
                false,
                3_600,
                900);

        return cache;
    }

//    @Bean
//    public Cas30ProxyTicketValidator ticketValidator(){
//        Cas30ProxyTicketValidator tv = new Cas30ProxyTicketValidator(casServer);
//        tv.setProxyCallbackUrl(calendarServiceProxyCallbackUrl);
//        tv.setProxyGrantingTicketStorage(pgtStorage());
//
//        tv.setAcceptAnyProxy(true);
//
//        return tv;
//    }

    @Bean
    public Saml11TicketValidator ticketValidator(){
        return new Saml11TicketValidator(casServer);
    }




    /**
     * Single point logout filter
     */
    @Bean
    public SingleSignOutFilter singleSignOutFilter() {
        return new SingleSignOutFilter();
    }

    /**
     * Request single point exit filter
     */
    @Bean
    public LogoutFilter casLogoutFilter() {
        LogoutFilter logoutFilter = new LogoutFilter(
                casServerLogout,
                new SecurityContextLogoutHandler());
        logoutFilter.setFilterProcessesUrl("/logout");
        return logoutFilter;
    }

    /**
     *  For single point logout
     */
    @Bean
    public ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> singleSignOutHttpSessionListener() {
        ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> listener = new ServletListenerRegistrationBean<>();
        listener.setEnabled(true);
        listener.setListener(new SingleSignOutHttpSessionListener());
        listener.setOrder(1);
        return listener;
    }

    @Bean
    public FilterRegistrationBean characterEncodingFilterRegistration() {
        FilterRegistrationBean registrationBean =
                new FilterRegistrationBean(characterEncodingFilter());
        registrationBean.setName("CharacterEncodingFilter");
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }

    private CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return filter;
    }

    /**
     * Implementation of {@link ProxyGrantingTicketStorage} that is backed by a
     * HashMap that keeps a ProxyGrantingTicket for a specified amount of time.
     * <p>
     * {@link ProxyGrantingTicketStorage#cleanUp()} must be called on a regular basis to
     * keep the HashMap from growing indefinitely.
     * </p>
     */
    @Bean
    public ProxyGrantingTicketStorage pgtStorage() {
        return new ProxyGrantingTicketStorageImpl();
    }


    @Scheduled(fixedRate = 300_000)
    public void proxyGrantingTicketStorageCleaner(){
        logger.info("Running ProxyGrantingTicketStorage#cleanup() at {}",
                LocalDateTime.now());
        pgtStorage().cleanUp();
    }


    @Bean
    public AuthenticationUserDetailsService userDetailsService(){
        GrantedAuthorityFromAssertionAttributesUserDetailsService uds =
                new GrantedAuthorityFromAssertionAttributesUserDetailsService(
                        new String[]{"role"}
                        );
        return uds;
    }



} // The End...
