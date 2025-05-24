package com.api.crud.apiCrudProject.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.api.crud.apiCrudProject.application.dto.UserSystemRequest;
import com.api.crud.apiCrudProject.application.dto.UserSystemResponse;
import com.api.crud.apiCrudProject.application.mapper.UserSystemMapper;
import com.api.crud.apiCrudProject.domain.entity.UserSystem;
import com.api.crud.apiCrudProject.domain.entity.enumeration.Language;
import com.api.crud.apiCrudProject.domain.entity.enumeration.RoleType;
import com.api.crud.apiCrudProject.domain.repository.UserSystemRepository;
import com.api.crud.apiCrudProject.infrastructure.exception.RessourceNotFoundException;

public class UserSystemServiceTest {
private UserSystemRepository repository;
    private UserSystemMapper mapper;
    private UserSystemService service;

    @BeforeEach
    void setUp() {
        repository = mock(UserSystemRepository.class);
        mapper = mock(UserSystemMapper.class);
        service = new UserSystemService(repository, mapper);
    }

    @Test
    void createUserSystem_shouldPersistAndReturnResponse() {
        // Given
        UserSystemRequest request = new UserSystemRequest("john", "pass", Language.FRENCH, RoleType.ROLE_USER);
        UserSystem entity = new UserSystem(null, "john", "pass", Language.FRENCH, RoleType.ROLE_USER);
        UserSystem savedEntity = new UserSystem(1L, "john", "pass", Language.FRENCH, RoleType.ROLE_USER);
        UserSystemResponse response = new UserSystemResponse(1L, "john", "pass", Language.FRENCH, RoleType.ROLE_USER);

        when(mapper.toEntity(request)).thenReturn(entity);
        when(repository.persist(entity)).thenReturn(savedEntity);
        when(mapper.toResponse(savedEntity)).thenReturn(response);

        // When
        UserSystemResponse result = service.createUserSystem(request);

        // Then
        assertEquals(response, result);
        verify(mapper).toEntity(request);
        verify(repository).persist(entity);
        verify(mapper).toResponse(savedEntity);
    }

    @Test
    void updateUserSystem_shouldUpdateAndReturnResponse() {
        // Given
        Long id = 1L;
        UserSystemRequest request = new UserSystemRequest("updated", "pass", Language.ENGLISH, RoleType.ROLE_ADMIN);
        UserSystem existing = new UserSystem(id, "old", "pass", Language.FRENCH, RoleType.ROLE_USER);
        UserSystem updatedEntity = new UserSystem(id, "updated", "pass", Language.ENGLISH, RoleType.ROLE_ADMIN);
        UserSystem saved = new UserSystem(id, "updated", "pass", Language.ENGLISH, RoleType.ROLE_ADMIN);
        UserSystemResponse response = new UserSystemResponse(id, "updated", "pass", Language.ENGLISH, RoleType.ROLE_ADMIN);

        when(repository.searchById(id)).thenReturn(Optional.of(existing));
        when(mapper.toEntity(request)).thenReturn(updatedEntity);
        when(repository.persist(updatedEntity)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        // When
        UserSystemResponse result = service.updateUserSystem(id, request);

        // Then
        assertEquals(response, result);
        verify(repository).searchById(id);
        verify(repository).persist(updatedEntity);
        verify(mapper).toEntity(request);
        verify(mapper).toResponse(saved);
    }

    @Test
    void getUserSystem_shouldReturnUserSystemResponse() {
        Long id = 2L;
        UserSystem entity = new UserSystem(id, "jane", "123", Language.ENGLISH, RoleType.ROLE_USER);
        UserSystemResponse response = new UserSystemResponse(id, "jane", "123", Language.ENGLISH, RoleType.ROLE_USER);

        when(repository.searchById(id)).thenReturn(Optional.of(entity));
        when(mapper.toResponse(entity)).thenReturn(response);

        UserSystemResponse result = service.getUserSystem(id);

        assertEquals(response, result);
    }

    @Test
    void getUserSystem_shouldThrowWhenNotFound() {
        Long id = 3L;
        when(repository.searchById(id)).thenReturn(Optional.empty());

        assertThrows(RessourceNotFoundException.class, () -> service.getUserSystem(id));
    }

    @Test
    void getAllUserSystems_shouldReturnListOfResponses() {
        List<UserSystem> users = List.of(
                new UserSystem(1L, "john", "pass", Language.FRENCH, RoleType.ROLE_USER),
                new UserSystem(2L, "jane", "pass", Language.ENGLISH, RoleType.ROLE_ADMIN)
        );

        List<UserSystemResponse> responses = List.of(
                new UserSystemResponse(1L, "john", "pass", Language.FRENCH, RoleType.ROLE_USER),
                new UserSystemResponse(2L, "jane", "pass", Language.ENGLISH, RoleType.ROLE_ADMIN)
        );

        when(repository.searchAll()).thenReturn(users);
        when(mapper.toResponse(users.get(0))).thenReturn(responses.get(0));
        when(mapper.toResponse(users.get(1))).thenReturn(responses.get(1));

        List<UserSystemResponse> result = service.getAllUserSystems();

        assertEquals(2, result.size());
        assertEquals(responses, result);
    }

    @Test
    void deleteUserSystem_shouldCallRemoveById_whenUserExists() {
        Long id = 1L;
        UserSystem user = new UserSystem(id, "john", "pass", Language.FRENCH, RoleType.ROLE_USER);

        when(repository.searchById(id)).thenReturn(Optional.of(user));

        service.deleteUserSystem(id);

        verify(repository).removeById(id);
    }

    @Test
    void deleteUserSystem_shouldThrow_whenUserNotFound() {
        Long id = 404L;
        when(repository.searchById(id)).thenReturn(Optional.empty());

        assertThrows(RessourceNotFoundException.class, () -> service.deleteUserSystem(id));
    }
}
