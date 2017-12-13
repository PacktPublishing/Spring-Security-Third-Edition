package com.packtpub.springsecurity;

import com.packtpub.springsecurity.core.userdetails.CalendarUserDetailsService;
import com.packtpub.springsecurity.service.CalendarService;
import com.packtpub.springsecurity.service.UserContext;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * https://docs.spring.io/spring-security/site/docs/current/reference/html/test-method.html
 */
@RunWith(SpringRunner.class)

// Option one:
//@Transactional
//@DirtiesContext
@SpringBootTest
@AutoConfigureMockMvc

// Option two:
//@WebMvcTest

// Option three
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CalendarApplicationTests {

    @Autowired
    private MockMvc mvc;

    /*@MockBean
    private CalendarUserDetailsService calendarUserDetailsService;

    @MockBean
    private CalendarService calendarService;

    @MockBean
    private UserContext userContext;*/


    /*@Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mvc = webAppContextSetup(webApplicationContext).build();
    }*/

    @Test
    @WithAnonymousUser
    public void securityEnabled() throws Exception {
        mvc
                .perform(get("/admin/h2")
                        .header("X-Requested-With", "XMLHttpRequest")
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void test_failed_Login() throws Exception {
        mvc.perform(post("/login")
                        .accept(MediaType.TEXT_HTML)
                        .contentType(
                                MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "bob@example.com")
                        .param("password", "bob1")
//                .with(csrf())
        )
                .andExpect(status().is4xxClientError())
                .andDo(print())
        ;
    }


    @Test
    public void test_login_user1() throws Exception {
        mvc.perform(post("/login")
                .accept(MediaType.TEXT_HTML)
                .contentType(
                        MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "user1@example.com")
                .param("password", "user1")
        )
                .andExpect(status().is4xxClientError())
//                .andExpect(redirectedUrl("/default"))
        ;
    }

    @Test
    public void test_login_admin1() throws Exception {
        mvc.perform(post("/login")
                        .accept(MediaType.TEXT_HTML)
                        .contentType(
                                MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "admin1@example.com")
                        .param("password", "admin1")
//                .with(csrf())
        )
                .andExpect(status().is4xxClientError())
//                .andExpect(redirectedUrl("/default"))
                .andDo(print())
        ;
    }


    @Test
    @WithUserDetails("user1@example.com")
    public void test_events_user() throws Exception {
        mvc.perform(get("/events/"))
                .andExpect(status().is4xxClientError())
        ;
    }

    @Test
    @WithAnonymousUser
    public void test_events_WithAnonymousUser() throws Exception {
        mvc.perform(get("/events/"))
                .andExpect(status().is4xxClientError())
        ;
    }

    @Test
    @WithMockUser
    public void test_events_WithMockUser() throws Exception {
        mvc.perform(get("/events/"))
                .andExpect(status().is4xxClientError())
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
