package com.sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.domain.User;
import com.sample.parameter.RegisterUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.Cookie;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationIT {

    private static final String ADMIN_STRING = "admin";
    private static final String UNICODE_STRING = "árvíztűrőtükörfúrógép";

    private static final RequestPostProcessor ADMIN_AUTH = httpBasic("admin", ADMIN_STRING);
    private static final RequestPostProcessor UNICODE_AUTH = httpBasic(UNICODE_STRING, UNICODE_STRING);

    private static final String CSRF_HEADER_NAME = "X-XSRF-TOKEN";
    private static final String CSRF_COOKIE_NAME = "XSRF-TOKEN";

    /**
     * {@link RequestPostProcessor} that applies session identifiers, cookies and CSRF tokens. from the response of a
     * previously made HTTP call.
     *
     * @param result the previously made HTTP call's result
     * @return the {@code RequestPostProcessor} that can be fed to {@link org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder#with(RequestPostProcessor)}}.
     */
    private static RequestPostProcessor previousState(MvcResult result) {
        return request -> {
            request.setSession(result.getRequest().getSession());
            request.setCookies(result.getResponse().getCookies());

            Cookie cookie = result.getResponse().getCookie(CSRF_COOKIE_NAME);
            if (cookie != null) {
                request.addHeader(CSRF_HEADER_NAME, cookie.getValue());
            }
            return request;
        };
    }

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    private ObjectMapper om;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply(springSecurity())
            .build();

        om = new ObjectMapper();
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
        MvcResult result = mvc.perform(get("/auth")
            .with(ADMIN_AUTH))
            .andExpect(authenticated())
            .andExpect(status().isOk())
            .andReturn();

        mvc.perform(get("/users")
            .with(previousState(result)))
            .andExpect(authenticated())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void canAuthOnlyAtAuthEndpoint() throws Exception {
        mvc.perform(get("/users")
            .with(ADMIN_AUTH))
            .andExpect(unauthenticated())
            .andExpect(status().isUnauthorized());

        mvc.perform(get("/auth")
            .with(ADMIN_AUTH))
            .andExpect(authenticated())
            .andExpect(status().isOk());
    }

    @Test
    public void canAddUserWithUnicodeUsernameAndPasswordAndLogin() throws Exception {
        MvcResult result = mvc.perform(get("/auth"))
            .andExpect(status().isUnauthorized())
            .andReturn();

        result = mvc.perform(post("/register")
            .with(previousState(result))
            .contentType(MediaType.APPLICATION_JSON)
            .content(om.writeValueAsBytes(new RegisterUser(UNICODE_STRING, UNICODE_STRING, UNICODE_STRING))))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

        User user = om.readValue(result.getResponse().getContentAsString(), User.class);

        mvc.perform(get("/auth")
            .with(UNICODE_AUTH))
            .andExpect(authenticated())
            .andExpect(status().isOk());

        result = mvc.perform(get("/auth")
            .with(ADMIN_AUTH))
            .andExpect(authenticated())
            .andExpect(status().isOk())
            .andReturn();

        mvc.perform(delete("/users/{id}", user.getId())
            .with(previousState(result)))
            .andExpect(status().isOk());
    }
}
