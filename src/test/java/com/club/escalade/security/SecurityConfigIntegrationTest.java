package com.club.escalade.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {
        "app.seed.enabled=false"
})
@AutoConfigureMockMvc
class SecurityConfigIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldRedirectAnonymousUserToLoginWhenAccessingSorties() throws Exception {
        mockMvc.perform(get("/sorties"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser(username = "user1", authorities = {"ROLE_USER"})
    void shouldForbidUserWithoutAdminAuthorityOnSorties() throws Exception {
        mockMvc.perform(get("/sorties"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user1", authorities = {"ROLE_ADMIN"})
    void shouldAllowAdminOnSorties() throws Exception {
        mockMvc.perform(get("/sorties"))
                .andExpect(status().isOk());
    }
}
