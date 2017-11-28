package com.packtpub.springsecurity.configuration;

import com.packtpub.springsecurity.access.CalendarPermissionEvaluator;
import com.packtpub.springsecurity.authentication.CalendarUserAuthenticationProvider;
import com.packtpub.springsecurity.core.userdetails.CalendarUserDetailsService;
import com.packtpub.springsecurity.dataaccess.CalendarUserDao;
import com.packtpub.springsecurity.dataaccess.EventDao;
import com.packtpub.springsecurity.service.CalendarService;
import com.packtpub.springsecurity.web.access.expression.CustomWebSecurityExpressionHandler;
import com.packtpub.springsecurity.web.access.intercept.FilterInvocationServiceSecurityMetadataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Description;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.method.MethodSecurityMetadataSource;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.ConsensusBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.access.expression.WebExpressionVoter;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CustomAuthorizationConfig
{

    @Description("DefaultMethodSecurityExpressionHandler")
    @Bean
    public DefaultMethodSecurityExpressionHandler defaultExpressionHandler(EventDao eventDao){
        DefaultMethodSecurityExpressionHandler deh = new DefaultMethodSecurityExpressionHandler();
        deh.setPermissionEvaluator(
                new CalendarPermissionEvaluator(eventDao));
        return deh;
    }


    @Description("ConsensusBased AccessDecisionManager for Authorization voting")
    @Bean
    public AccessDecisionManager accessDecisionManager(
            CustomWebSecurityExpressionHandler customWebSecurityExpressionHandler) {
        List<AccessDecisionVoter<? extends Object>> decisionVoters
                = Arrays.asList(
//                new AuthenticatedVoter(),
//                new RoleVoter(),
                new WebExpressionVoter(){{
                    setExpressionHandler(customWebSecurityExpressionHandler);
                }}
        );
        return new ConsensusBased(decisionVoters);
    }


    // Looks like this was not used in the original code.
//    @Description("UnanimousBased AccessDecisionManager for Authorization voting")
//    @Bean
    public AccessDecisionManager accessDecisionManager2(
            CustomWebSecurityExpressionHandler customWebSecurityExpressionHandler) {
        List<AccessDecisionVoter<? extends Object>> decisionVoters
                = Arrays.asList(
                new AuthenticatedVoter(),
                new RoleVoter(),
                new WebExpressionVoter(){{
                    setExpressionHandler(customWebSecurityExpressionHandler);
                }}
        );
        return new UnanimousBased(decisionVoters);
    }


    @Description("AuthenticationManager that will generate an authentication token unlike {PreAuthenticatedAuthenticationProvider}")
    @Bean @DependsOn({"defaultCalendarService"})
    public CalendarUserAuthenticationProvider calendarUserAuthenticationProvider(
            CalendarService calendarService,
            PasswordEncoder passwordEncoder){
        return new CalendarUserAuthenticationProvider(calendarService, passwordEncoder);
    }

} // The End...
