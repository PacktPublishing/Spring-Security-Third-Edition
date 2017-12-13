package com.packtpub.springsecurity.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.ClientTokenServices;
import org.springframework.security.oauth2.client.token.JdbcClientTokenServices;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Arrays;

/**
 * @author Mick Knutson
 * @since chapter 01.00
 */
@Configuration
public class JavaConfig {

    @Value("${oauth.resource:http://localhost:8080/api}")
//    @Value("${oauth.resource:https://localhost:8443}")
    private String baseUrl;

    @Value("${oauth.token:http://localhost:8080/oauth/token}")
//    @Value("${oauth.token:https://localhost:8443/oauth/token}")
    private String tokenUrl;

    @Value("${oauth.resource.id:microservice-test}")
    private String resourceId;

    @Value("${oauth.resource.client.id:oauthClient1}")
    private String resourceClientId;

    @Value("${oauth.resource.client.secret:oauthClient1Password}")
    private String resourceClientSecret;


    @Resource
    @Qualifier("accessTokenRequest")
    private AccessTokenRequest accessTokenRequest;

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private DataSource dataSource;

    @Autowired
    private HttpComponentsClientHttpRequestFactory requestFactory;


    @Bean
    public ClientTokenServices clientTokenServices() {
        return new JdbcClientTokenServices(dataSource);
    }

    @Bean
    public OAuth2RestOperations oAuth2RestOperations() {

        ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
        resource.setAccessTokenUri(tokenUrl);
        resource.setId(resourceId);
        resource.setClientId(resourceClientId);
        resource.setClientSecret(resourceClientSecret);

        resource.setGrantType("password");

        resource.setScope(Arrays.asList("openid"));

        resource.setUsername("user1@example.com");
        resource.setPassword("user1");

        OAuth2RestTemplate template = new OAuth2RestTemplate(resource);
//        template.setRequestFactory(requestFactory);
        return template;
    }

    /*
    <bean id="oAuth2RestTemplate" class="org.springframework.security.oauth2.client.OAuth2RestTemplate">
        <constructor-arg ref="clientCredentialsResourceDetails"/>
        <constructor-arg ref="defaultOAuth2ClientContext"/>
        <property name="requestFactory" ref="httpComponentsClientHttpRequestFactory"/>
    </bean>

    <bean id="httpComponentsClientHttpRequestFactory" class="org.springframework.http.client.HttpComponentsClientHttpRequestFactory">
        <constructor-arg ref="selfSignedHttpsClientFactory"/>
    </bean>

    <bean id="clientCredentialsResourceDetails" class="org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails">
        <property name="accessTokenUri" value="${authentication.service.client.token.url:https://localhost:8443/oauth/token}"/>
        <property name="clientId" value="${authentication.service.client.id:testClient}"/>
        <property name="clientSecret" value="${authentication.service.client.secret:password}"/>
    </bean>

    <bean id="defaultOAuth2ClientContext" class="org.springframework.security.oauth2.client.DefaultOAuth2ClientContext"/>



     */


} // The End...
