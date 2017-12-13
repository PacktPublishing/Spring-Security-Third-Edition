package com.packtpub.springsecurity;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;
import org.springframework.test.web.servlet.htmlunit.webdriver.MockMvcHtmlUnitDriverBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * https://docs.spring.io/spring-security/site/docs/current/reference/html/test-method.html
 */
@RunWith(SpringRunner.class)
@ContextConfiguration
@WebAppConfiguration


// Option two:
//@WebMvcTest

public class CalendarApplicationTests {


    @Autowired
    WebApplicationContext context;

    WebClient webClient;

    WebDriver driver;

//    @Autowired
//    private MockMvc mvc;
    private MockMvc mvc = MockMvcBuilders
        .webAppContextSetup(context)
//        .apply(springSecurity())
        .build();

    @Before
    public void setup() {
        webClient = MockMvcWebClientBuilder
                // demonstrates applying a MockMvcConfigurer (Spring Security)
//                .webAppContextSetup(context, springSecurity())
                .webAppContextSetup(context)
                // for illustration only - defaults to ""
                .contextPath("")
                // By default MockMvc is used for localhost only;
                // the following will use MockMvc for example.com and example.org as well
                .useMockMvcForHosts("example.com","example.org")
                .build();

        driver = MockMvcHtmlUnitDriverBuilder
                .webAppContextSetup(context)
                .build();

        /*MockMvc mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
//                .apply(springSecurity())
                .build();

        webClient = MockMvcWebClientBuilder
                .mockMvcSetup(mockMvc)
                // for illustration only - defaults to ""
                .contextPath("")
                // By default MockMvc is used for localhost only;
                // the following will use MockMvc for example.com and example.org as well
                .useMockMvcForHosts("example.com","example.org")
                .build();*/
    }


    @After
    public void destroy() {
        if (driver != null) {
            driver.close();
        }
    }

    @Test
//    @WithAnonymousUser
    public void pageTest() throws Exception {
        HtmlPage createMsgFormPage = webClient.getPage("http://localhost:8080/login/form");


        HtmlForm form = createMsgFormPage.getHtmlElementById("messageForm");
        HtmlTextInput summaryInput = createMsgFormPage.getHtmlElementById("summary");
        summaryInput.setValueAttribute("Spring Rocks");
        HtmlTextArea textInput = createMsgFormPage.getHtmlElementById("text");
        textInput.setText("In case you didn't know, Spring Rocks!");
        HtmlSubmitInput submit = form.getOneHtmlElementByAttribute("input", "type", "submit");
        HtmlPage newMessagePage = submit.click();


        assertThat(newMessagePage.getUrl().toString()).endsWith("/messages/123");
        String id = newMessagePage.getHtmlElementById("id").getTextContent();
        assertThat(id).isEqualTo("123");
        String summary = newMessagePage.getHtmlElementById("summary").getTextContent();
        assertThat(summary).isEqualTo("Spring Rocks");
        String text = newMessagePage.getHtmlElementById("text").getTextContent();
        assertThat(text).isEqualTo("In case you didn't know, Spring Rocks!");



    }


    @Test
    public void securityEnabled() throws Exception {
        mvc
                .perform(get("/admin/h2")
                        .header("X-Requested-With", "XMLHttpRequest")
                )
                .andExpect(status().isUnauthorized());
    }

//    @Test
    public void test_failed_Login() throws Exception {
        mvc.perform(post("/login")
                .accept(MediaType.TEXT_HTML)
                .contentType(
                        MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "bob@example.com")
                .param("password", "bob1")
//                .with(csrf())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login/form?error"))
                .andDo(print())
        ;
    }


//    @Test
    public void test_login_user1() throws Exception {
        mvc.perform(post("/login")
                .accept(MediaType.TEXT_HTML)
                .contentType(
                        MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "user1@example.com")
                .param("password", "user1")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/default"))
        ;
    }

//    @Test
    public void test_login_admin1() throws Exception {
        mvc.perform(post("/login")
                .accept(MediaType.TEXT_HTML)
                .contentType(
                        MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "admin1@example.com")
                .param("password", "admin1")
//                .with(csrf())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/default"))
                .andDo(print())
        ;
    }



    @Test
//    @WithAnonymousUser
    public void test_events_WithAnonymousUser() throws Exception {
        mvc.perform(get("/events/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login/form"))
//                .andExpect(redirectedUrlPattern("/login/form"))
        ;
    }

//    @Test
//    @WithMockUser
    public void test_events_WithMockUser() throws Exception {
        mvc.perform(get("/events/"))
                .andExpect(status().is4xxClientError())
//                .andExpect(view().name("login"))
//                .andExpect(content().string(Matchers.containsString("Spring Framework Guru")))
        ;
    }

    /*@Test
    @WithMockUser(username="admin1@example.com",roles={"USER","ADMIN"})
    public void noopWithMockUser() throws Exception {}

    @Test
    @WithAnonymousUser
    public void noopWithAnonymousUser() throws Exception {}

    @Test
    @Transactional
    @WithUserDetails(value = "user1@example.com", userDetailsServiceBeanName = "calendarUserDetailsService")
    public void testWithUserDetails() {

    }*/

    /*
    @Test
    public void testShowProduct() throws Exception {
        assertThat(this.productServiceMock).isNotNull();
        when(productServiceMock.getProductById(1)).thenReturn(product1);

        MvcResult result= mockMvc.perform(get("/product/{id}/", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("productshow"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("product"))
                .andExpect(model().attribute("product", hasProperty("id", is(1))))
                .andExpect(model().attribute("product", hasProperty("productId", is("235268845711068308"))))
                .andExpect(model().attribute("product", hasProperty("description", is("Spring Framework Guru Shirt"))))
                .andExpect(model().attribute("product", hasProperty("price", is(new BigDecimal("18.95")))))
                .andExpect(model().attribute("product", hasProperty("imageUrl", is("https://springframework.guru/wp-content/uploads/2015/04/spring_framework_guru_shirt-rf412049699c14ba5b68bb1c09182bfa2_8nax2_512.jpg"))))
                .andReturn();


        MockHttpServletResponse mockResponse=result.getResponse();
        assertThat(mockResponse.getContentType()).isEqualTo("text/html;charset=UTF-8");

        Collection<String> responseHeaders = mockResponse.getHeaderNames();
        assertNotNull(responseHeaders);
        assertEquals(1, responseHeaders.size());
        assertEquals("Check for Content-Type header", "Content-Type", responseHeaders.iterator().next());
        String responseAsString=mockResponse.getContentAsString();
        assertTrue(responseAsString.contains("Spring Framework Guru"));

        verify(productServiceMock, times(1)).getProductById(1);
        verifyNoMoreInteractions(productServiceMock);
    }
*/

} // The End...
