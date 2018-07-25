package com.sample;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationIT {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply(springSecurity())
            .build();
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void cannotAccessBeforeAuth() throws Exception {
        mvc.perform(get("/auth"))
            .andExpect(unauthenticated())
            .andExpect(status().isUnauthorized());

        mvc.perform(get("/users"))
            .andExpect(unauthenticated())
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void canAccessAfterAuth() throws Exception {
        var auth = httpBasic("admin", "admin");

        MvcResult result = mvc.perform(get("/auth")
            .with(auth))
            .andExpect(authenticated())
            .andExpect(status().isOk())
            .andReturn();

        mvc.perform(get("/users")
            .session(getMockHttpSession(result)))
            .andExpect(authenticated())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void canAuthOnlyAtAuthEndpoint() throws Exception {
        var auth = httpBasic("admin", "admin");

        mvc.perform(get("/users")
            .with(auth))
            .andExpect(unauthenticated())
            .andExpect(status().isUnauthorized());

        mvc.perform(get("/auth")
            .with(auth))
            .andExpect(authenticated())
            .andExpect(status().isOk());
    }

    private static MockHttpSession getMockHttpSession(MvcResult result) {
        return (MockHttpSession) result.getRequest().getSession();
    }
}
