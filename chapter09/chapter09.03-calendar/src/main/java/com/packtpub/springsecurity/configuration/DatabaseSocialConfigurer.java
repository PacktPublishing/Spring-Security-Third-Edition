package com.packtpub.springsecurity.configuration;

import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.linkedin.connect.LinkedInConnectionFactory;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.social.github.connect.GitHubConnectionFactory;
import org.springframework.social.google.connect.GoogleConnectionFactory;

import javax.sql.DataSource;

/**
 * Concrete implementation of {@link SocialConfigurer} with convenient default implementations of methods.
 * Specifically connecting our dataSource with the provider connection in order to save Oauth2 attribute
 * data into our local database.
 */
public class DatabaseSocialConfigurer extends SocialConfigurerAdapter {

    private final DataSource dataSource;

    public DatabaseSocialConfigurer(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        TextEncryptor textEncryptor = Encryptors.noOpText();
        return new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, textEncryptor);
    }

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer config, Environment env) {
        super.addConnectionFactories(config, env);

        // Configured through AutoConfiguration:
//        config.addConnectionFactory(new TwitterConnectionFactory(
//            env.getProperty("spring.social.twitter.appId"),
//            env.getProperty("spring.social.twitter.appSecret")));
//        config.addConnectionFactory(new FacebookConnectionFactory(
//            env.getProperty("spring.social.facebook.appId"),
//            env.getProperty("spring.social.facebook.appSecret")));
//        config.addConnectionFactory(new LinkedInConnectionFactory(
//            env.getProperty("spring.social.linkedin.appId"),
//            env.getProperty("spring.social.linkedin.appSecret")));

        // Adding GitHub Connection with properties from application.yml
        config.addConnectionFactory(new GitHubConnectionFactory(
            env.getProperty("spring.social.github.appId"),
            env.getProperty("spring.social.github.appSecret")));

        // Adding Google Connection with properties from application.yml
        config.addConnectionFactory(new GoogleConnectionFactory(
            env.getProperty("spring.social.google.appId"),
            env.getProperty("spring.social.google.appSecret")));
    }

} // The End...
