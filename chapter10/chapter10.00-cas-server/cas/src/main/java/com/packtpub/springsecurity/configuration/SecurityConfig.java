package com.packtpub.springsecurity.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.userdetails.InetOrgPersonContextMapper;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.Arrays;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth
                .ldapAuthentication()
                .userSearchBase("")
                .userSearchFilter("(uid={0})")
                .groupSearchBase("ou=Groups")
                .groupSearchFilter("(uniqueMember={0})")
                .userDetailsContextMapper(new InetOrgPersonContextMapper())
                .contextSource(contextSource())
                .passwordCompare()
                    .passwordAttribute("telephoneNumber")
        ;
    }

    /**
     * LDAP Server Context
     * @return
     */
    @Bean
    public DefaultSpringSecurityContextSource contextSource() {
        return new DefaultSpringSecurityContextSource(
                Arrays.asList("ldap://localhost:33389/"), "dc=jbcpcalendar,dc=com");
    }

} // The End...
