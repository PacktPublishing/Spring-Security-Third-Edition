package com.packtpub.springsecurity.web.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Spring Security Config Class
 * @see WebSecurityConfigurerAdapter
 */
@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger logger = LoggerFactory
            .getLogger(SecurityConfig.class);

    /**
     * Configure AuthenticationManager with inMemory credentials.
     *
     * @param auth       AuthenticationManagerBuilder
     * @throws Exception Authentication exception
     */
    @Override
    public void configure(final AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("user").password("user").roles("USER")
                .and().withUser("admin").password("admin").roles("USER", "ADMIN")
                .and().withUser("user1@example.com").password("user1").roles("USER")
                .and().withUser("admin1@example.com").password("admin1").roles("USER", "ADMIN")
        ;
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
        http.authorizeRequests()
                .antMatchers("/resources/**").permitAll()

                .antMatchers("/").permitAll()
                .antMatchers("/login/*").permitAll()
                .antMatchers("/logout").permitAll()
                .antMatchers("/errors/**").permitAll()
                .antMatchers("/admin/*").hasRole("ADMIN")
                .antMatchers("/events/").hasRole("ADMIN")
                .antMatchers("/**").hasRole("USER")

                .and().exceptionHandling().accessDeniedPage("/errors/403")

                .and().formLogin()
                    .loginPage("/login/form")
                    .loginProcessingUrl("/login")
                    .failureUrl("/login/form?error")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/default", true)
                    .permitAll()

                .and().logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login/form?logout")
                    .permitAll()

                .and().httpBasic()

                .and().anonymous()

                // CSRF is enabled by default, with Java Config
                .and().csrf().disable()
        ;
    }

    /**
     * This is the equivalent to:
     * <pre><http pattern="/resources/**" security="none"/></pre>
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/resources/**");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean()
            throws Exception {
        return super.authenticationManagerBean();
    }
} // The End...
