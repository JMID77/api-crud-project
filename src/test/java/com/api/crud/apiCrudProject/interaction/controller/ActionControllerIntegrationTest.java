package com.api.crud.apiCrudProject.interaction.controller;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.api.crud.apiCrudProject.ApiCrudProjectApplication;
import com.api.crud.apiCrudProject.application.dto.ActionRequest;
import com.api.crud.apiCrudProject.domain.entity.enumeration.ActionStatus;
import com.api.crud.apiCrudProject.infrastructure.security.SecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest(classes = ApiCrudProjectApplication.class)
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
public class ActionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    
    // @Test
    @ParameterizedTest
    @CsvSource({
        "adminFr, admin123",
        "adminEn, admin123",
        "userFr, user123",
        "userEn, user123"
    })
    void createAction_shouldReturn201_withHateoas_forAllAuthorizedUsers(String userName, String pwd) throws Exception {
        ActionRequest request = new ActionRequest("Test Action - "+userName, ActionStatus.CREATED);

        mockMvc.perform(post("/api/vES.1/actions")
                .with(httpBasic(userName, pwd))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.actionName").value("Test Action - "+userName))
            .andExpect(jsonPath("$._links").exists());
    }

    // @Test
    @ParameterizedTest
    @CsvSource({
        "adminEn, admin123"
        // "adminFr, admin123"
        // "userEn, user123"
        // "userFr, user123"
    })
    void createAction_shouldReturn400_whenInvalid_forSpecificUsers(String userName, String pwd) throws Exception {
        ActionRequest request = new ActionRequest("   ", ActionStatus.CANCELLED);

        mockMvc.perform(post("/api/vES.1/actions")
                .with(httpBasic(userName, pwd))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }

    // @Test
    @ParameterizedTest
    @CsvSource({
        "adminFr, admin123",
        "adminEn, admin123",
        "userFr, user123",
        "userEn, user123"
    })
    void updateAction_shouldReturn200_whenValid_forAllAuthorizedUsers(String userName, String pwd) throws Exception {
        ActionRequest create = new ActionRequest("Initial - "+userName, ActionStatus.CREATED);

        String response = mockMvc.perform(post("/api/vES.1/actions")
                .with(httpBasic(userName, pwd))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(create)))
            .andReturn().getResponse().getContentAsString();

        long id = objectMapper.readTree(response).get("id").asLong();

        ActionRequest update = new ActionRequest("Modified - "+userName, ActionStatus.IN_PROGRESS);

        mockMvc.perform(put("/api/vES.1/actions/" + id)
                .with(httpBasic(userName, pwd))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(update)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.actionName").value("Modified - "+userName))
            .andExpect(jsonPath("$.actionStatus").value(ActionStatus.IN_PROGRESS.name()));
    }

    // @Test
    @ParameterizedTest
    @CsvSource({
        "adminFr, admin123",
        "adminEn, admin123",
        "userFr, user123",
        "userEn, user123"
    })
    void updateAction_shouldReturn404_whenNotFound_forAllAuthorizedUsers(String userName, String pwd) throws Exception {
        ActionRequest update = new ActionRequest("Modified", ActionStatus.IN_PROGRESS);

        mockMvc.perform(put("/api/vES.1/actions/999999")
                .with(httpBasic(userName, pwd))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(update)))
            .andExpect(status().isNotFound());
    }

    // @Test
    @ParameterizedTest
    @CsvSource({
        "adminFr, admin123",
        "adminEn, admin123",
        "userFr, user123",
        "userEn, user123"
    })
    void updateAction_shouldReturn400_whenInvalid_forAllAuthorizedUsers(String userName, String pwd) throws Exception {
        ActionRequest update = new ActionRequest("", null);

        mockMvc.perform(put("/api/vES.1/actions/1")
                .with(httpBasic(userName, pwd))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(update)))
            .andExpect(status().isBadRequest());
    }

    // @Test
    @ParameterizedTest
    @CsvSource({
        "adminFr, admin123",
        "adminEn, admin123",
        "userFr, user123",
        "userEn, user123"
    })
    void getActionById_shouldReturn200_whenExists_forAllAuthorizedUsers(String userName, String pwd) throws Exception {
        ActionRequest request = new ActionRequest("Action consultée - "+userName, ActionStatus.CREATED);

        String response = mockMvc.perform(post("/api/vES.1/actions")
                .with(httpBasic(userName, pwd))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andReturn().getResponse().getContentAsString();

        long id = objectMapper.readTree(response).get("id").asLong();

        mockMvc.perform(get("/api/vES.1/actions/" + id)
                    .with(httpBasic(userName, pwd)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.actionName").value("Action consultée - "+userName))
            .andExpect(jsonPath("$.actionStatus").value(ActionStatus.CREATED.name()))
            .andExpect(jsonPath("$._links").exists());
    }

    // @Test
    @ParameterizedTest
    @CsvSource({
        "adminFr, admin123",
        "adminEn, admin123",
        "userFr, user123",
        "userEn, user123"
    })
    void getActionById_shouldReturn404_whenNotFound_forAllAuthorizedUsers(String userName, String pwd) throws Exception {
        mockMvc.perform(get("/api/vES.1/actions/999999")
                    .with(httpBasic(userName, pwd)))
            .andExpect(status().isNotFound());
    }

    // @Test
    @ParameterizedTest
    @CsvSource({
        "adminFr, admin123",
        "adminEn, admin123",
        "userFr, user123",
        "userEn, user123"
    })
    void getAllActions_shouldReturnList_forAllAuthorizedUsers(String userName, String pwd) throws Exception {
        mockMvc.perform(get("/api/vES.1/actions")
                        .with(httpBasic(userName, pwd)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(0))));
    }

    // @Test
    @ParameterizedTest
    @CsvSource({
        "adminFr, admin123",
        "adminEn, admin123",
        "userFr, user123",
        "userEn, user123"
    })
    void deleteAction_shouldReturn204_whenExists_forAllAuthorizedUsers(String userName, String pwd) throws Exception {
        ActionRequest request = new ActionRequest("À supprimer - "+userName, ActionStatus.CREATED);

        String response = mockMvc.perform(post("/api/vES.1/actions")
                .with(httpBasic(userName, pwd))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andReturn().getResponse().getContentAsString();

        long id = objectMapper.readTree(response).get("id").asLong();

        mockMvc.perform(delete("/api/vES.1/actions/" + id)
                    .with(httpBasic(userName, pwd)))
            .andExpect(status().isNoContent());
    }

    // @Test
    @ParameterizedTest
    @CsvSource({
        "adminFr, admin123",
        "adminEn, admin123",
        "userFr, user123",
        "userEn, user123"
    })
    void deleteAction_shouldReturn404_whenNotExists_forAllAuthorizedUsers(String userName, String pwd) throws Exception {
        mockMvc.perform(delete("/api/vES.1/actions/999999")
                            .with(httpBasic(userName, pwd)))
            .andExpect(status().isNotFound());
    }

    // @Test
    @ParameterizedTest
    @CsvSource({
        "adminFr, admin123",
        "adminEn, admin123",
        "userFr, user123",
        "userEn, user123"
    })
    void getAllStatuses_shouldReturnEnumValues_forAllAuthorizedUsers(String userName, String pwd) throws Exception {
        mockMvc.perform(get("/api/vES.1/actions/statuses")
                            .with(httpBasic(userName, pwd)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasItem("CREATED")))
            .andExpect(jsonPath("$", hasItem("COMPLETED")));
    }
}