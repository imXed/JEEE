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
    void shouldAllowAnonymousUserToAccessSortiesList() throws Exception {
        mockMvc.perform(get("/sorties"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "claire.dupont@club-escalade.fr", authorities = {"ROLE_USER"})
    void shouldAllowAuthenticatedUserToAccessSortiesList() throws Exception {
        mockMvc.perform(get("/sorties"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRedirectAnonymousUserToLoginWhenAccessingSortieCreate() throws Exception {
        mockMvc.perform(get("/sorties/create"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser(username = "claire.dupont@club-escalade.fr", authorities = {"ROLE_USER"})
    void shouldAllowAuthenticatedUserToAccessSortieCreate() throws Exception {
        mockMvc.perform(get("/sorties/create"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRedirectAnonymousUserToLoginWhenAccessingMembres() throws Exception {
        mockMvc.perform(get("/membres"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser(username = "claire.dupont@club-escalade.fr", authorities = {"ROLE_USER"})
    void shouldAllowAuthenticatedUserToAccessMembres() throws Exception {
        mockMvc.perform(get("/membres"))
                .andExpect(status().isOk());
    }
}
