package com.club.escalade.security;

import com.club.escalade.entity.Membre;
import com.club.escalade.service.MembreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {
        "app.seed.enabled=false"
})
@AutoConfigureMockMvc
class SecurityConfigIntegrationTest {
    private static final String TEST_USER_EMAIL = "claire.dupont@club-escalade.fr";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MembreService membreService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void ensureTestUserExists() {
        Membre existing = membreService.findByEmail(TEST_USER_EMAIL).orElse(null);
        if (existing != null) {
            if (existing.getAuthorities().contains("ROLE_USER")) {
                return;
            }
            Set<String> authorities = new HashSet<>(existing.getAuthorities());
            authorities.add("ROLE_USER");
            existing.setAuthorities(authorities);
            membreService.save(existing);
            return;
        }

        Membre membre = new Membre();
        membre.setNom("Dupont");
        membre.setPrenom("Claire");
        membre.setEmail(TEST_USER_EMAIL);
        membre.setMotDePasse(passwordEncoder.encode("password"));
        membre.setAuthorities(Set.of("ROLE_USER"));
        membreService.save(membre);
    }

    @Test
    void shouldAllowAnonymousUserToAccessSortiesList() throws Exception {
        mockMvc.perform(get("/sorties"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = TEST_USER_EMAIL, authorities = {"ROLE_USER"})
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
    @WithMockUser(username = TEST_USER_EMAIL, authorities = {"ROLE_USER"})
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
    @WithMockUser(username = TEST_USER_EMAIL, authorities = {"ROLE_USER"})
    void shouldAllowAuthenticatedUserToAccessMembres() throws Exception {
        mockMvc.perform(get("/membres"))
                .andExpect(status().isOk());
    }
}
