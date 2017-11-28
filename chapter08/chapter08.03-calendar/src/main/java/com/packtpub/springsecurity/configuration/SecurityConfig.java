package com.packtpub.springsecurity.configuration;

import com.packtpub.springsecurity.service.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.x509.X509AuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

/**
 * Spring Security Config Class
 * @see {@link WebSecurityConfigurerAdapter}
 * @since chapter08.00
 */
@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger logger = LoggerFactory
            .getLogger(SecurityConfig.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PersistentTokenRepository persistentTokenRepository;

    @Autowired
    private RememberMeServices rememberMeServices;

    @Autowired
    private Http403ForbiddenEntryPoint forbiddenEntryPoint;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PreAuthenticatedAuthenticationProvider preAuthAuthenticationProvider;

    /**
     * Configure AuthenticationManager.
     *
     * NOTE:
     * Due to a known limitation with JavaConfig:
     * <a href="https://jira.spring.io/browse/SPR-13779">
     *     https://jira.spring.io/browse/SPR-13779</a>
     *
     * We cannot use the following to expose a {@link UserDetailsManager}
     * <pre>
     *     http.authorizeRequests()
     * </pre>
     *
     * In order to expose {@link UserDetailsManager} as a bean, we must create  @Bean
     *
     * @see {@link super.userDetailsService()}
     * @see {@link com.packtpub.springsecurity.service.DefaultCalendarService}
     *
     * @param auth       AuthenticationManagerBuilder
     * @throws Exception Authentication exception
     */
    @Override
    public void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(preAuthAuthenticationProvider)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
        ;
    }

    /**
     * The parent method from {@link WebSecurityConfigurerAdapter} (public UserDetailsService userDetailsService())
     * originally returns a {@link UserDetailsService}, but this needs to be a {@link UserDetailsManager}
     * UserDetailsManager vs UserDetailsService
     */
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    /**
     * BCryptPasswordEncoder password encoder
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(4);
    }


    /**
     * HTTP Security configuration
     *
     * <pre><http auto-config="true"></pre> is equivalent to:
     * <pre>
     *  <http>
     *      <form-login />
     *      <http-basic />
     *      <logout />
     *  </http>
     * </pre>
     *
     * Which is equivalent to the following JavaConfig:
     *
     * <pre>
     *     http.formLogin()
     *          .and().httpBasic()
     *          .and().logout();
     * </pre>
     *
     * @param http HttpSecurity configuration.
     * @throws Exception Authentication configuration exception
     *
     * @see <a href="http://docs.spring.io/spring-security/site/migrate/current/3-to-4/html5/migrate-3-to-4-jc.html">
     *     Spring Security 3 to 4 migration</a>
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        // Matching
        http.authorizeRequests()
                // FIXME: TODO: Allow anyone to use H2 (NOTE: NOT FOR PRODUCTION USE EVER !!! )
                .antMatchers("/admin/h2/**").permitAll()

                .antMatchers("/").permitAll()
                .antMatchers("/login/*").permitAll()
                .antMatchers("/logout").permitAll()
                .antMatchers("/signup/*").permitAll()
                .antMatchers("/errors/**").permitAll()
                .antMatchers("/admin/*").access("hasRole('ADMIN') and isFullyAuthenticated()")
                .antMatchers("/events/").hasRole("ADMIN")
                .antMatchers("/**").hasRole("USER");

        // Login
        http.formLogin()
                .loginPage("/login/form")
                .loginProcessingUrl("/login")
                .failureUrl("/login/form?error")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/default", true)
                .permitAll();

        // Logout
        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login/form?logout")
                .permitAll();

        // Anonymous
        http.anonymous();

        // CSRF is enabled by default, with Java Config
        http.csrf().disable();

        // Exception Handling
        http.exceptionHandling()
//                .authenticationEntryPoint(forbiddenEntryPoint)
                .accessDeniedPage("/errors/403")
        ;

        // remember me configuration
        http.rememberMe()
                .key("jbcpCalendar")
//                .rememberMeParameter("obscure-remember-me")
//                .rememberMeCookieName("obscure-remember-me")
                .rememberMeServices(rememberMeServices);

        // SSL / TLS x509 support
        http.x509()
//                .userDetailsService(userDetailsService)
                .x509AuthenticationFilter(x509Filter())
        ;

        // Enable <frameset> in order to use H2 web console
        http.headers().frameOptions().disable();
    }

    @Bean
    public Http403ForbiddenEntryPoint forbiddenEntryPoint(){
        return new Http403ForbiddenEntryPoint();
    }

    @Bean
    public X509AuthenticationFilter x509Filter(){
        return new X509AuthenticationFilter(){{
            setAuthenticationManager(authenticationManager);
        }};
    }

    @Bean
    public PreAuthenticatedAuthenticationProvider preAuthAuthenticationProvider(final AuthenticationUserDetailsService authenticationUserDetailsService){
        return new PreAuthenticatedAuthenticationProvider(){{
            setPreAuthenticatedUserDetailsService(authenticationUserDetailsService);
        }};
    }

    @Bean
    public UserDetailsByNameServiceWrapper authenticationUserDetailsService(final UserDetailsService userDetailsService){
        return new UserDetailsByNameServiceWrapper(){{
            setUserDetailsService(userDetailsService);
        }};
    }

    @Bean
    public RememberMeServices rememberMeServices(PersistentTokenRepository ptr){
        PersistentTokenBasedRememberMeServices rememberMeServices =
                new PersistentTokenBasedRememberMeServices(
                        "jbcpCalendar",
                        userDetailsService,
                        ptr);
        rememberMeServices.setParameter("obscure-remember-me");
        rememberMeServices.setCookieName("obscure-remember-me");
        return rememberMeServices;
    }


    /**
     * This is the equivalent to:
     * <pre>
     *     <http pattern="/resources/**" security="none"/>
     *     <http pattern="/css/**" security="none"/>
     *     <http pattern="/webjars/**" security="none"/>
     * </pre>
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/resources/**")
                .antMatchers("/css/**")
                .antMatchers("/webjars/**")
        ;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean()
            throws Exception {
        return super.authenticationManagerBean();
    }

} // The End...
