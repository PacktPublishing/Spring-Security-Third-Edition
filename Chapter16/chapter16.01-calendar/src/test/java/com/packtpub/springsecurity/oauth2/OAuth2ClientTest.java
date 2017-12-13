package com.packtpub.springsecurity.oauth2;

import com.packtpub.springsecurity.CalendarApplication;
import com.packtpub.springsecurity.configuration.SecurityConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CalendarApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)

@AutoConfigureMockMvc
@Transactional

@TestPropertySource(locations = {
        "classpath:test.yml",
        "classpath:application.yml" })
public class OAuth2ClientTest {

    private static final Logger logger = LoggerFactory
            .getLogger(OAuth2ClientTest.class);

    @LocalServerPort
    private int port;

    @Autowired
    MockMvc mockMvc;


    @Value("${oauth.resource:http://localhost:8080}")
    private String baseUrl;

    @Value("${oauth.token:http://localhost:8080/oauth/token}")
    private String tokenUrl;

    @Value("${oauth.resource.id:microservice-test}")
    private String resourceId;

    @Value("${oauth.resource.client.id:oauthClient1}")
    private String resourceClientId;

    @Value("${oauth.resource.client.secret:oauthClient1Password}")
    private String resourceClientSecret;

    //-----------------------------------------------------------------------//

    @Before
    public void beforeEachTest() {
        // Nothing yet...
    }

    //-----------------------------------------------------------------------//

    @Test
    public void test_rootContext() throws Exception {

        OAuth2RestTemplate template = template("user1");

        String url = baseUrl+"/";
        String expected = "{'message': 'welcome to the JBCP Calendar Application'}";

        logger.info(" CALLING: {}", url);
        String result = template.getForObject(url, String.class);
        logger.info(" RESULT: {}", result);

        assertThat(result, is(expected));

    }

    @Test
    public void test_api() throws Exception {

        OAuth2RestTemplate template = template("user1");

        String url = baseUrl+"/api";
        String expected = "{'message': 'welcome to the JBCP Calendar Application API'}";

        logger.info(" CALLING: {}", url);
        String result = template.getForObject(url, String.class);
        logger.info(" RESULT: {}", result);

        assertThat(result, is(expected));

    }

//    @Test
    public void test_signup_user1() throws Exception {

        OAuth2RestTemplate template = template("user1");

        String url = baseUrl+"/signup/new";
        String expected = "foobar";

        logger.info(" CALLING: {}", url);
        String result = template.getForObject(url, String.class);
        logger.info(" RESULT: {}", result);

        assertThat(result, is(expected));

    }

//    @Test
    public void test_post_signup_user1() throws Exception {

        OAuth2RestTemplate template = template("user1");

        String url = baseUrl+"/signup/new";
        String expected = "foobar";

        logger.info(" CALLING: {}", url);
        String result = template.getForObject(url, String.class);

//        String result = template.postForObject(url, Object request, String.class, Object... uriVariables)

//        String result = template.postForObject(url, String.class);
        logger.info(" RESULT: {}", result);

        assertThat(result, is(expected));

    }


//    @Test
    public void test_Events_user1() throws Exception {

        OAuth2RestTemplate template = template("user1");

        String url = baseUrl+"/events/my/0";
        String expected = "foobar";

        logger.info(" CALLING: {}", url);
        String result = template.getForObject(url, String.class);
        logger.info(" RESULT: {}", result);

        assertThat(result, is(expected));

    }

    @Test
    public void test_myEvents_user1() throws Exception {

        OAuth2RestTemplate template = template("user1");

        String url = baseUrl+"/events/my";
        String expected = "{\"currentUser\":[{\"id\":100,\"summary\":\"Birthday Party\",\"description\":\"This is going to be a great birthday\",\"when\":";

        logger.info(" CALLING: {}", url);
        String result = template.getForObject(url, String.class);
        logger.info(" RESULT: {}", result);

        assertThat(result, startsWith(expected));


    }

    @Test
    public void test_myEvents_admin1() throws Exception {

        OAuth2RestTemplate template = template("admin1");

        String url = baseUrl+"/events/my";
        String expected = "{\"currentUser\":[{\"id\":102,\"summary\":\"Vacation\",\"description\":\"Paragliding in Greece\",\"when\":";

        logger.info(" CALLING: {}", url);
        String result = template.getForObject(url, String.class);
        logger.info(" RESULT: {}", result);

        assertThat(result, startsWith(expected));


    }

    @Test
    public void test_show_user1() throws Exception {

        OAuth2RestTemplate template = template("user1");

        String url = baseUrl+"/events/101";
        String expected = "{\"id\":101,\"summary\":\"Conference Call\",\"description\":\"Call with the client\",\"when\":";

        logger.info(" CALLING: {}", url);
        String result = template.getForObject(url, String.class);
        logger.info(" RESULT: {}", result);

        assertThat(result, startsWith(expected));

    }

//    @Test
    public void test_new_create_new_EventFormAutoPopulate() throws Exception {

        OAuth2RestTemplate template = template("user1");

        String url = baseUrl+"/events/new?auto";
        String expected = "foobar";

        logger.info(" CALLING: {}", url);
        String result = template.getForObject(url, String.class);
//        String result = template.postForObject(url, String.class);
        logger.info(" RESULT: {}", result);

        assertThat(result, is(expected));

    }

//    @Test
    public void test_createEvent_user1() throws Exception {

        OAuth2RestTemplate template = template("user1");

        String url = baseUrl+"/events/new";
        String expected = "foobar";

        logger.info(" CALLING: {}", url);
        String result = template.getForObject(url, String.class);
        logger.info(" RESULT: {}", result);

        assertThat(result, is(expected));

    }

    @Test
    public void test_default_user1() throws Exception {

        OAuth2RestTemplate template = template("user1");

        String url = baseUrl+"/default";
        String expected = "{'message': 'welcome to the JBCP Calendar Application'}";

        logger.info(" CALLING: {}", url);
        String result = template.getForObject(url, String.class);
        logger.info(" RESULT: {}", result);

        assertThat(result, is(expected));

    }

    /*
    org.springframework.expression.spel.SpelEvaluationException: EL1008E: Property or field 'timestamp' cannot be found on object of type 'java.util.HashMap' - maybe not public?

     */
//    @Test
    public void test_default_admin1() throws Exception {

        OAuth2RestTemplate template = template("admin1");

        String url = baseUrl+"/default";
        String expected = "{'message': 'welcome to the JBCP Calendar Application'}";

        logger.info(" CALLING: {}", url);
        String result = template.getForObject(url, String.class);
        logger.info(" RESULT: {}", result);

        assertThat(result, is(expected));

    }

    //-----------------------------------------------------------------------//

    private OAuth2RestTemplate template(String user){
        ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
        resource.setAccessTokenUri(tokenUrl);
        resource.setId(resourceId);
        resource.setClientId(resourceClientId);
        resource.setClientSecret(resourceClientSecret);

        resource.setGrantType("password");

        resource.setScope(Arrays.asList("openid"));

        this.setResourceUser(resource, user);

        return new OAuth2RestTemplate(resource);
    }

    private void setResourceUser(ResourceOwnerPasswordResourceDetails resource,
                                 String user){
        if("admin1".equalsIgnoreCase(user)){
            resource.setUsername("admin1@example.com");
            resource.setPassword("admin1");
        } else if("user1".equalsIgnoreCase(user)) {
            resource.setUsername("user1@example.com");
            resource.setPassword("user1");
        }
    }

    private void printToken(OAuth2AccessToken token){
        logger.info("-------------------------------------------------------");
        logger.info("TokenType: {}", token.getTokenType());
        logger.info("Value: {}", token.getValue());
        logger.info("-------------------------------------------------------");
        logger.info("Scope: {}", token.getScope());
        logger.info("Expiration: {}", token.getExpiration());
        logger.info("ExpiresIn: {}", token.getExpiresIn());
        logger.info("RefreshToken: {}", token.getRefreshToken());
        logger.info("-------------------------------------------------------");
    }



} // The End...
