package com.api.crud.apiCrudProject.infrastructure.config;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;

import com.api.crud.apiCrudProject.infrastructure.security.SecurityConfig;
import com.api.crud.apiCrudProject.interaction.controller.ActionController;

// @SpringBootTest
// @AutoConfigureMockMvc
@WebMvcTest(controllers = ActionController.class) // Charge les contrôleurs mais importe aussi la configuration de sécurité
@Import(SecurityConfig.class) // Import de la configuration de sécurité
public class SecurityConfigTest {
    // @Autowired
    // private MockMvc mockMvc;

    // @Test
    // @DisplayName("V /api/vES.1/actions est accessible sans authentification")
    // void publicRoute_shouldBeAccessible() throws Exception {
    //     mockMvc.perform(get("/api/vES.1/actions")
    //             .accept(MediaType.APPLICATION_JSON))
    //             .andExpect(status().isOk());
    // }

    // @Test
    // @DisplayName("X Accès interdit à une route non exposée (ex. /api/vES.1/protected)")
    // void protectedRoute_shouldBeForbidden() throws Exception {
    //     mockMvc.perform(get("/api/vES.1/protected")
    //             .accept(MediaType.APPLICATION_JSON))
    //             .andExpect(status().isForbidden()); // ou .isUnauthorized() si Spring Security exige login
    // }
}
