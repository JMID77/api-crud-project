package com.api.crud.apiCrudProject.infrastructure.exception.action;

import com.api.crud.apiCrudProject.application.dto.actions.ActionRequest;
import com.api.crud.apiCrudProject.interaction.controller.action.ActionController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.api.crud.apiCrudProject.application.service.actions.ActionsService;
import com.api.crud.apiCrudProject.infrastructure.hateoas.action.ActionModelAssembler;

@WebMvcTest(controllers = ActionController.class)
@AutoConfigureMockMvc(addFilters = false) // Désactiver la sécurité pour les tests
public class GlobalExceptionHandlerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @Autowired private ActionsService actionsService;

     // ✅ Classe interne STATIC sinon Spring ne peut pas l'utiliser comme configuration
    @TestConfiguration
    static class MockBeanConfig {
        @Bean
        public ActionsService actionsService() {
            return mock(ActionsService.class);
        }

        @Bean
        public ActionModelAssembler actionModelAssembler() {
            return mock(ActionModelAssembler.class);
        }
    }


    @Test
    public void whenValidationFails_thenReturns400AndErrorMessages() throws Exception {
        ActionRequest request = new ActionRequest("  ", null); // nom vide + status null

        mockMvc.perform(post("/api/v1/actions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.actionName").exists())
                .andExpect(jsonPath("$.actionStatus").exists());
    }

    @Test
    public void whenResourceNotFound_thenReturns404() throws Exception {
        when(actionsService.getAction(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/actions/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("not found")));
    }

    @Test
    public void whenUnhandledException_thenReturns500() throws Exception {
        when(actionsService.getAllActions()).thenThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(get("/api/v1/actions"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Unexpected error")));
    }

    @Test
    public void whenAccessDenied_thenReturns403() throws Exception {
        doThrow(new AccessDeniedException("No access")).when(actionsService).deleteActionById(1L);

        mockMvc.perform(delete("/api/v1/actions/1"))
                .andExpect(status().isForbidden())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Access denied")));
    }
}