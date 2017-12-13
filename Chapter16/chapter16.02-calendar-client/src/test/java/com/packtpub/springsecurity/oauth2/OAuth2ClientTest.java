package com.packtpub.springsecurity.oauth2;

import com.packtpub.springsecurity.configuration.SecurityConfig;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

@ContextConfiguration(initializers = ConfigFileApplicationContextInitializer.class)
@RunWith(SpringRunner.class)
@TestPropertySource(locations = {
        "classpath:test.yml",
        "classpath:application.yml" })
public class OAuth2ClientTest {

    private static final Logger logger = LoggerFactory
            .getLogger(SecurityConfig.class);

    @Value("${oauth.resource:https://localhost:8443}")
    private String baseUrl;

    @Value("${oauth.token:https://localhost:8443/oauth/token}")
    private String tokenUrl;

    @Value("${oauth.resource.id:microservice-test}")
    private String resourceId;

    @Value("${oauth.resource.client.id:oauthClient1}")
    private String resourceClientId;

    @Value("${oauth.resource.client.secret:oauthClient1Password}")
    private String resourceClientSecret;


    static {
        //for localhost testing only
        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
                new javax.net.ssl.HostnameVerifier(){

                    public boolean verify(String hostname,
                                          javax.net.ssl.SSLSession sslSession) {
                        if (hostname.equals("localhost")) {
                            return true;
                        }
                        return false;
                    }
                });
//        HttpsURLConnection.setDefaultHostnameVerifier ((hostname, session) -> true);
    }

    @Before
    public void beforeEachTest() {
        // Nothing yet...
    }

    @Test
    public void noop() throws Exception {}

//    @Test
    public void testConnectDirectlyToResourceServer() throws Exception {
        OAuth2RestTemplate template = template();

        logger.info(" CALLING: " + baseUrl+"/api");
        String result = template.getForObject(baseUrl+"/api", String.class);
        logger.info(" RESULT: " + result);

        assertEquals("{Hello API}", result);

        logger.info(" CALLING: " + baseUrl+"/events/101/");
        result = template.getForObject(baseUrl+"/events/101/", String.class);
        logger.info(" RESULT: " + result);
//        assertEquals("{[\"id\":101,\"summary\":\"Conference Call\",\"description\":\"Call with the client\",\"when\":1514059200000]}", result);
//
        logger.info(" CALLING: " + baseUrl+"/events/my/");
        result = template.getForObject(baseUrl+"/events/my/", String.class);
        logger.info(" RESULT: " + result);
        assertEquals("{Hello API}", result);
    }

//    @Test
    public void execute_post_to_tokenUrl()
            throws ClientProtocolException, IOException {

        OAuth2RestTemplate template = template();

        ResponseEntity<String> response = template.exchange(
                tokenUrl,
                HttpMethod.POST,
                null,
                String.class);

        assertThat(response.getStatusCode().value(), equalTo(200));
    }

    private OAuth2RestTemplate template(){
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

        template.setRequestFactory(requestFactory());

//        HttpsURLConnection.setDefaultHostnameVerifier ((hostname, session) -> true);

        return template;
    }

    private HttpComponentsClientHttpRequestFactory requestFactory(){

        CloseableHttpClient httpClient
                = HttpClients.custom()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
//                .setSSLHostnameVerifier((hostname, session) -> true)
                .build();
        HttpComponentsClientHttpRequestFactory requestFactory
                = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);

        return requestFactory;
    }


} // The End...
