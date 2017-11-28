package com.packtpub.springsecurity.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.sql.DataSource;

/**
 * Spring Security Config Class
 * @see {@link WebSecurityConfigurerAdapter}
 * @since chapter04.02
 */
@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger logger = LoggerFactory
            .getLogger(SecurityConfig.class);

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private DataSource dataSource;

    /*
    TODO: FIXME: Must override each of these, if not using the default table structure:
    // UserDetailsManager SQL
	** public static final String DEF_CREATE_USER_SQL = "insert into users (username, password, enabled) values (?,?,?)";
	public static final String DEF_DELETE_USER_SQL = "delete from users where username = ?";
	public static final String DEF_UPDATE_USER_SQL = "update users set password = ?, enabled = ? where username = ?";
	public static final String DEF_INSERT_AUTHORITY_SQL = "insert into authorities (username, authority) values (?,?)";
	public static final String DEF_DELETE_USER_AUTHORITIES_SQL = "delete from authorities where username = ?";
	public static final String DEF_USER_EXISTS_SQL = "select username from users where username = ?";
	public static final String DEF_CHANGE_PASSWORD_SQL = "update users set password = ? where username = ?";
     */

    public static String CUSTOM_CREATE_USER_SQL = "insert into calendar_users (username, password, enabled) values (?,?,?)";

    private static String CUSTOM_GROUP_AUTHORITIES_BY_USERNAME_QUERY = "select g.id, g.group_name, ga.authority " +
                                                                       "from groups g, group_members gm, " +
                                                                       "group_authorities ga where gm.username = ? " +
                                                                       "and g.id = ga.group_id and g.id = gm.group_id";

    private static String CUSTOM_USERS_BY_USERNAME_QUERY = "select email, password, true " +
                                                           "from calendar_users where email = ?";

    private static String CUSTOM_AUTHORITIES_BY_USERNAME_QUERY = "select cua.id, cua.authority " +
                                                                 "from calendar_users cu, calendar_user_authorities "+
                                                                 "cua where cu.email = ? "+
                                                                 "and cu.id = cua.calendar_user";

    /**
     * Configure AuthenticationManager with inMemory credentials.
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
                .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(CUSTOM_USERS_BY_USERNAME_QUERY)
                .authoritiesByUsernameQuery(CUSTOM_AUTHORITIES_BY_USERNAME_QUERY)
                .passwordEncoder(passwordEncoder())
        ;
    }

    /**
     * The parent method from {@link WebSecurityConfigurerAdapter} (public UserDetailsService userDetailsService())
     * originally returns a {@link UserDetailsService}, but this needs to be a {@link UserDetailsManager}
     * UserDetailsManager vs UserDetailsService
     */
    @Bean
    @Override
    public UserDetailsManager userDetailsService() {
        return new JdbcUserDetailsManager() {{
            setDataSource(dataSource);
            // Override default SQL for JdbcUserDetailsManager
            setGroupAuthoritiesByUsernameQuery(CUSTOM_GROUP_AUTHORITIES_BY_USERNAME_QUERY);
            setUsersByUsernameQuery(CUSTOM_USERS_BY_USERNAME_QUERY);
            setAuthoritiesByUsernameQuery(CUSTOM_AUTHORITIES_BY_USERNAME_QUERY);
            // TODO: This is not available through AuthenticationManagerBuilder
            setCreateUserSql(CUSTOM_CREATE_USER_SQL);
        }};
    }

    /**
     * Standard SHA-256 Password Encoder
     * @return ShaPasswordEncoder
     *
     * @see ShaPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new ShaPasswordEncoder(256);
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
                // FIXME: TODO: Allow anyone to use H2 (NOTE: NOT FOR PRODUCTION USE EVER !!! )
                .antMatchers("/admin/h2/**").permitAll()

                .antMatchers("/").permitAll()
                .antMatchers("/login/*").permitAll()
                .antMatchers("/logout").permitAll()
                .antMatchers("/signup/*").permitAll()
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

                .and().anonymous()

                // CSRF is enabled by default, with Java Config
                .and().csrf().disable();

        // Enable <frameset> in order to use H2 web console
        http.headers().frameOptions().disable();
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

//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        return super.userDetailsService();
//    }


} // The End...
