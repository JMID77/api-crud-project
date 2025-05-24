package com.api.crud.apiCrudProject.interaction.controller;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
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
import com.api.crud.apiCrudProject.application.dto.UserSystemRequest;
import com.api.crud.apiCrudProject.domain.entity.enumeration.Language;
import com.api.crud.apiCrudProject.domain.entity.enumeration.RoleType;
import com.api.crud.apiCrudProject.infrastructure.security.SecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = ApiCrudProjectApplication.class)
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
public class UserSystemControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Test pour créer un utilisateur
    @ParameterizedTest
    @CsvSource({
        "adminFr, admin123",
        "adminEn, admin123"
    })
    void createUserSystem_shouldReturn201_forAdminUsers_only(String userName, String pwd) throws Exception {
        UserSystemRequest request = new UserSystemRequest("testUser - "+userName, "password", Language.FRENCH, RoleType.ROLE_USER);

        mockMvc.perform(post("/api/vIS.1/internal/admins/users")
                .with(httpBasic(userName, pwd))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.username").value("testUser - "+userName));
    }

    @ParameterizedTest
    @CsvSource({
        "userFr, user123",
        "userEn, user123"
    })
    void createUserSystem_shouldReturn403_forUserRole(String userName, String pwd) throws Exception {
        UserSystemRequest request = new UserSystemRequest("testUser - "+userName, "password", Language.FRENCH, RoleType.ROLE_USER);

        mockMvc.perform(post("/api/vIS.1/internal/admins/users")
                .with(httpBasic(userName, pwd))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isForbidden());  // Utilisateur ROLE_USER ne doit pas avoir accès
    }

    // Test pour mettre à jour un utilisateur
    @ParameterizedTest
    @CsvSource({
        "adminFr, admin123",
        "adminEn, admin123"
    })
    void updateUserSystem_shouldReturn200_forAdminUsers_only(String userName, String pwd) throws Exception {
        // Créer un utilisateur avant de le mettre à jour
        UserSystemRequest createRequest = new UserSystemRequest("userToUpdate - "+userName, "password", Language.ENGLISH, RoleType.ROLE_USER);
        String createResponse = mockMvc.perform(post("/api/vIS.1/internal/admins/users")
                .with(httpBasic(userName, pwd))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
            .andReturn().getResponse().getContentAsString();

        long id = objectMapper.readTree(createResponse).get("id").asLong();
        
        UserSystemRequest updateRequest = new UserSystemRequest("updatedUser - "+userName, "newPassword", Language.FRENCH, RoleType.ROLE_ADMIN);
        mockMvc.perform(put("/api/vIS.1/internal/admins/users/" + id)
                .with(httpBasic(userName, pwd))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value("updatedUser - "+userName))
            .andExpect(jsonPath("$.password").value("newPassword"))
            .andExpect(jsonPath("$.language").value(Language.FRENCH.name()))
            .andExpect(jsonPath("$.role").value(RoleType.ROLE_ADMIN.name()));
    }

    @ParameterizedTest
    @CsvSource({
        "userFr, user123",
        "userEn, user123"
    })
    void updateUserSystem_shouldReturn403_forUserRole(String userName, String pwd) throws Exception {
        // Créer un utilisateur avant de le mettre à jour
        UserSystemRequest createRequest = new UserSystemRequest("userToUpdate - "+userName, "password", Language.ENGLISH, RoleType.ROLE_USER);
        String createResponse = mockMvc.perform(post("/api/vIS.1/internal/admins/users")
                .with(httpBasic("adminFr", "admin123"))     // Force la création en mode ADMIN, car USER ne peut pas !!
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
            .andReturn().getResponse().getContentAsString();

        long id = objectMapper.readTree(createResponse).get("id").asLong();
        
        UserSystemRequest updateRequest = new UserSystemRequest("updatedUser - "+userName, "newPassword", Language.FRENCH, RoleType.ROLE_ADMIN);
        mockMvc.perform(put("/api/vIS.1/internal/admins/users/" + id)
                .with(httpBasic(userName, pwd))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
            .andExpect(status().isForbidden());  // Utilisateur ROLE_USER ne doit pas avoir accès
    }


    // Test pour obtenir un utilisateur par ID
    @ParameterizedTest
    @CsvSource({
        "adminFr, admin123",
        "adminEn, admin123"
    })
    void getUserById_shouldReturn200_whenAdminAuthorized(String userName, String pwd) throws Exception {
        UserSystemRequest createRequest = new UserSystemRequest("userToGet - "+userName, "password", Language.ENGLISH, RoleType.ROLE_USER);
        String createResponse = mockMvc.perform(post("/api/vIS.1/internal/admins/users")
                .with(httpBasic(userName, pwd))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
            .andReturn().getResponse().getContentAsString();

        long id = objectMapper.readTree(createResponse).get("id").asLong();

        mockMvc.perform(get("/api/vIS.1/internal/admins/users/" + id)
                .with(httpBasic(userName, pwd)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value("userToGet - "+userName));
    }
    
    @ParameterizedTest
    @CsvSource({
        "userFr, user123",
        "userEn, user123"
    })
    void getUserById_shouldReturn403_forUserRole(String userName, String pwd) throws Exception {
        mockMvc.perform(get("/api/vIS.1/internal/admins/users/1")
                .with(httpBasic(userName, pwd)))
            .andExpect(status().isForbidden());
    }



    // Test pour obtenir tous les utilisateurs
    @ParameterizedTest
    @CsvSource({
        "adminFr, admin123",
        "adminEn, admin123"
    })
    void getAllUsers_shouldReturnList_forAdminUsers_only(String userName, String pwd) throws Exception {
        mockMvc.perform(get("/api/vIS.1/internal/admins/users")
                .with(httpBasic(userName, pwd)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(0))));
    }

    @ParameterizedTest
    @CsvSource({
        "userFr, user123",
        "userEn, user123"
    })
    void getAllUsers_shouldReturn403_forUserRole(String userName, String pwd) throws Exception {
        mockMvc.perform(get("/api/vIS.1/internal/admins/users")
                .with(httpBasic(userName, pwd)))
            .andExpect(status().isForbidden());  // Utilisateur ROLE_USER ne doit pas avoir accès
    }


    // Test pour supprimer un utilisateur
    @ParameterizedTest
    @CsvSource({
        "adminFr, admin123",
        "adminEn, admin123"
    })
    void deleteUser_shouldReturn204_forAdminUsers_only(String userName, String pwd) throws Exception {
        // Créer un utilisateur avant de le supprimer
        UserSystemRequest createRequest = new UserSystemRequest("userToDelete", "password", Language.FRENCH, RoleType.ROLE_USER);
        String createResponse = mockMvc.perform(post("/api/vIS.1/internal/admins/users")
                .with(httpBasic(userName, pwd))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
            .andReturn().getResponse().getContentAsString();

        long id = objectMapper.readTree(createResponse).get("id").asLong();
        
        mockMvc.perform(delete("/api/vIS.1/internal/admins/users/" + id)
                .with(httpBasic(userName, pwd)))
            .andExpect(status().isNoContent());
    }

    @ParameterizedTest
    @CsvSource({
        "userFr, user123",
        "userEn, user123"
    })
    void deleteUser_shouldReturn403_forUserRole(String userName, String pwd) throws Exception {
        mockMvc.perform(delete("/api/vIS.1/internal/admins/users/1")
                .with(httpBasic(userName, pwd)))
            .andExpect(status().isForbidden());  // Utilisateur ROLE_USER ne doit pas avoir accès
    }


    // Test pour supprimer un utilisateur inexistant
    @ParameterizedTest
    @CsvSource({
        "adminFr, admin123",
        "adminEn, admin123"
    })
    void deleteUser_shouldReturn404_whenNotExists_forAllAuthorizedUsers(String userName, String pwd) throws Exception {
        mockMvc.perform(delete("/api/vIS.1/internal/admins/users/999999")
                            .with(httpBasic(userName, pwd)))
            .andExpect(status().isNotFound());
    }

    @ParameterizedTest
    @CsvSource({
        "userFr, user123",
        "userEn, user123"
    })
    void deleteUser_shouldReturn403_whenUserRoleTriesToDeleteAnyId(String userName, String pwd) throws Exception {
        mockMvc.perform(delete("/api/vIS.1/internal/admins/users/999999")
                .with(httpBasic(userName, pwd)))
            .andExpect(status().isForbidden());
    }

}
