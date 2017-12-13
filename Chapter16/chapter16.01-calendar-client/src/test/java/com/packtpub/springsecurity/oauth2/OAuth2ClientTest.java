package com.packtpub.springsecurity.oauth2;

import com.packtpub.springsecurity.configuration.SecurityConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class OAuth2ClientTest {

    private static final Logger logger = LoggerFactory
            .getLogger(SecurityConfig.class);


    @Value("${oauth.resource:http://localhost:8080}")
//    @Value("${oauth.resource:https://localhost:8443}")
    private String baseUrl;

//    @Value("${oauth.authorize:https://localhost:8443/oauth/authorize}")
//    private String authorizeUrl;

    @Value("${oauth.token:http://localhost:8080/oauth/token}")
//    @Value("${oauth.token:https://localhost:8443/oauth/token}")
    private String tokenUrl;


    @Before
    public void beforeEachTest()
            throws Exception {
        //
    }

    @Test
    public void noop() throws Exception {}

//    @Test
    public void testConnectDirectlyToResourceServer() throws Exception {
        ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
        resource.setAccessTokenUri(tokenUrl);
        resource.setId("microservice-test");
        resource.setClientId("oauthClient1");
        resource.setClientSecret("oauthClient1Password");

        resource.setGrantType("password");

        resource.setScope(Arrays.asList("openid"));

        resource.setUsername("user1@example.com");
        resource.setPassword("user1");
        OAuth2RestTemplate template = new OAuth2RestTemplate(resource);
        logger.info(" CALLING: " + baseUrl+"/api");

        String result = template.getForObject(baseUrl+"/api", String.class);

        System.err.println(result);
        assertEquals("Hello, Trusted User marissa", result);
    }

}
