package com.packtpub.springsecurity.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * Spring Security Config Class
 * @see {@link ResourceServerConfigurerAdapter}
 * @since chapter16.00
 */
@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig
        extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(final HttpSecurity http) throws Exception {

        // Allow AJAX preflight requests via HttpMethod.OPTIONS to be made without
        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll();
    }

    /*@Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .formLogin().loginPage("/login").permitAll()
                .and().httpBasic().and()
                .requestMatchers()
                //specify urls handled
                .antMatchers("/", "/oauth/authorize", "/oauth/token")
                .antMatchers("/js/**", "/css/**")
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/js/**", "/css/**").permitAll()
                .anyRequest().authenticated();
    }*/

    /*@Override
    public void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .anonymous().disable()
//                .authorizeRequests()
//                .antMatchers(HttpMethod.OPTIONS).permitAll()
//                .antMatchers("/**").authenticated()
//                .and()
//                .httpBasic()
//        ;
//
//        http.cors();
    }*/


} // The End...
