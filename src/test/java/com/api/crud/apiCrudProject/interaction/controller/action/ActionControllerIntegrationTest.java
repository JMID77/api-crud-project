package com.api.crud.apiCrudProject.interaction.controller.action;

import com.api.crud.apiCrudProject.application.dto.actions.ActionRequest;
import com.api.crud.apiCrudProject.domain.entity.actions.ActionStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ActionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createAction_shouldReturn201_withHateoas() throws Exception {
        ActionRequest request = new ActionRequest("Test Action", ActionStatus.CREATED);

        mockMvc.perform(post("/api/v1/actions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.actionName").value("Test Action"))
            .andExpect(jsonPath("$._links").exists());
    }

    @Test
    void createAction_shouldReturn400_whenInvalid() throws Exception {
        ActionRequest request = new ActionRequest("   ", null);

        mockMvc.perform(post("/api/v1/actions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void updateAction_shouldReturn200_whenValid() throws Exception {
        ActionRequest create = new ActionRequest("Initial", ActionStatus.CREATED);

        String response = mockMvc.perform(post("/api/v1/actions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(create)))
            .andReturn().getResponse().getContentAsString();

        long id = objectMapper.readTree(response).get("id").asLong();

        ActionRequest update = new ActionRequest("Modified", ActionStatus.IN_PROGRESS);

        mockMvc.perform(put("/api/v1/actions/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(update)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.actionName").value("Modified"));
    }

    @Test
    void updateAction_shouldReturn404_whenNotFound() throws Exception {
        ActionRequest update = new ActionRequest("Modified", ActionStatus.IN_PROGRESS);

        mockMvc.perform(put("/api/v1/actions/999999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(update)))
            .andExpect(status().isNotFound());
    }

    @Test
    void updateAction_shouldReturn400_whenInvalid() throws Exception {
        ActionRequest update = new ActionRequest("", null);

        mockMvc.perform(put("/api/v1/actions/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(update)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void getActionById_shouldReturn200_whenExists() throws Exception {
        ActionRequest request = new ActionRequest("Action consultée", ActionStatus.CREATED);

        String response = mockMvc.perform(post("/api/v1/actions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andReturn().getResponse().getContentAsString();

        long id = objectMapper.readTree(response).get("id").asLong();

        mockMvc.perform(get("/api/v1/actions/" + id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.actionName").value("Action consultée"))
            .andExpect(jsonPath("$._links").exists());
    }

    @Test
    void getActionById_shouldReturn404_whenNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/actions/999999"))
            .andExpect(status().isNotFound());
    }

    @Test
    void getAllActions_shouldReturnList() throws Exception {
        mockMvc.perform(get("/api/v1/actions"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(0))));
    }

    @Test
    void deleteAction_shouldReturn204_whenExists() throws Exception {
        ActionRequest request = new ActionRequest("À supprimer", ActionStatus.CREATED);

        String response = mockMvc.perform(post("/api/v1/actions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andReturn().getResponse().getContentAsString();

        long id = objectMapper.readTree(response).get("id").asLong();

        mockMvc.perform(delete("/api/v1/actions/" + id))
            .andExpect(status().isNoContent());
    }

    @Test
    void deleteAction_shouldReturn404_whenNotExists() throws Exception {
        mockMvc.perform(delete("/api/v1/actions/999999"))
            .andExpect(status().isNotFound());
    }

    @Test
    void getAllStatuses_shouldReturnEnumValues() throws Exception {
        mockMvc.perform(get("/api/v1/actions/statuses"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasItem("CREATED")))
            .andExpect(jsonPath("$", hasItem("COMPLETED")));
    }
}