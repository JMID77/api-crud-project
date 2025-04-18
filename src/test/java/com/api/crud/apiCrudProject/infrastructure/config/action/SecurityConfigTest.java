package com.api.crud.apiCrudProject.infrastructure.config.action;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("V /api/v1/actions est accessible sans authentification")
    void publicRoute_shouldBeAccessible() throws Exception {
        mockMvc.perform(get("/api/v1/actions")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("X Accès interdit à une route non exposée (ex. /api/v1/protected)")
    void protectedRoute_shouldBeForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/protected")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden()); // ou .isUnauthorized() si Spring Security exige login
    }
}
