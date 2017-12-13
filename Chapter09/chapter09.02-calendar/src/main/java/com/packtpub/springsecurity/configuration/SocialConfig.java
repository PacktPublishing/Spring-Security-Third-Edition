package com.packtpub.springsecurity.configuration;

import com.packtpub.springsecurity.authentication.ProviderConnectionSignup;
import com.packtpub.springsecurity.authentication.SocialAuthenticationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.connect.web.SignInAdapter;

import javax.sql.DataSource;

@Configuration
public class SocialConfig {

    @Autowired
    private ConnectionFactoryLocator connectionFactoryLocator;

    @Bean
    @SuppressWarnings("SpringJavaAutowiringInspection")
    public SocialConfigurer socialConfigurerAdapter(final DataSource dataSource) {
        // https://github.com/spring-projects/spring-social/blob/master/spring-social-config/src/main/java/org/springframework/social/config/annotation/SocialConfiguration.java#L87
        return new DatabaseSocialConfigurer(dataSource);
    }

    @Autowired
    private UsersConnectionRepository usersConnectionRepository;

    @Autowired
    private ProviderConnectionSignup providerConnectionSignup;

    @Bean
    public SignInAdapter authSignInAdapter() {
        return (userId, connection, request) -> {
            SocialAuthenticationUtils.authenticate(connection);
            return null;
        };
    }

    @Bean
    public ProviderSignInController providerSignInController() {
        ((JdbcUsersConnectionRepository) usersConnectionRepository)
                .setConnectionSignUp(providerConnectionSignup);

        return new ProviderSignInController(
                connectionFactoryLocator,
                usersConnectionRepository,
                authSignInAdapter());
    }

} // The End...
