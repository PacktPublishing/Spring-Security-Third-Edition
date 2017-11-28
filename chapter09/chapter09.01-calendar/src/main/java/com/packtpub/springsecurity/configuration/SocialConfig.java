package com.packtpub.springsecurity.configuration;

import com.packtpub.springsecurity.authentication.ProviderConnectionSignup;
import com.packtpub.springsecurity.authentication.SocialAuthenticationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.connect.web.SignInAdapter;

import javax.sql.DataSource;

/**
 * Social Java Configuration
 *
 * @author mickknutson
 */
@Configuration
public class SocialConfig {

    @Autowired
    private ConnectionFactoryLocator connectionFactoryLocator;

    /**
     * Defines callback methods to customize Java-based configuration enabled by {@code @EnableWebMvc}.
     * {@code @EnableWebMvc}-annotated classes may implement this interface or
     * extend {@link SocialConfigurerAdapter}, which provides some default configuration.
     * In this initialization we are configuring the {@link SocialConfigurer} to use our local
     * database to store OAuth provider details for a given user.
     */
    @Bean
    public SocialConfigurer socialConfigurerAdapter(final DataSource dataSource) {
        // https://github.com/spring-projects/spring-social/blob/master/spring-social-config/src/main/java/org/springframework/social/config/annotation/SocialConfiguration.java#L87
        return new DatabaseSocialConfigurer(dataSource);
    }

    @Autowired
    private UsersConnectionRepository usersConnectionRepository;

    @Autowired
    private ProviderConnectionSignup providerConnectionSignup;

    /**
     * create a custom authenticate utility method to create an
     * Authentication objet and add it to our SecurityContext based on
     * the returned provider details.
     * @return
     */
    @Bean
    public SignInAdapter authSignInAdapter() {
        return (userId, connection, request) -> {
            SocialAuthenticationUtils.authenticate(connection);
            return null;
        };
    }

    /**
     * Configuring a {@link ProviderSignInController} to intercept OAuth2
     * requests that will be used to initiate an OAuth2 handshake with the specified
     * OAuth2 provider.
     * @return
     */
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
