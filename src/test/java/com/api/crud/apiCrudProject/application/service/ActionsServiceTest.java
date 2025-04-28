package com.api.crud.apiCrudProject.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.api.crud.apiCrudProject.application.dto.ActionRequest;
import com.api.crud.apiCrudProject.application.dto.ActionResponse;
import com.api.crud.apiCrudProject.application.mapper.ActionMapper;
import com.api.crud.apiCrudProject.domain.entity.Action;
import com.api.crud.apiCrudProject.domain.entity.ActionStatus;
import com.api.crud.apiCrudProject.domain.repository.ActionRepository;
import com.api.crud.apiCrudProject.infrastructure.exception.ActionNotFoundException;

@ExtendWith(MockitoExtension.class)
class ActionsServiceTest {

    @Mock
    private ActionRepository actionRepository;

    @Mock
    private ActionMapper actionMapper;

    @InjectMocks
    private ActionsService actionsService;

    // Exemples de données
    private ActionRequest request;
    private Action entity;
    private ActionResponse response;

    @BeforeEach
    void setup() {
        request = new ActionRequest("Test Action", ActionStatus.CREATED);
        entity = new Action(1L, "Test Action", ActionStatus.CREATED);
        response = new ActionResponse(1L, "Test Action", ActionStatus.CREATED);
    }

    // ✅ createAction()
    @Test
    void createAction_shouldMapAndSaveAndReturnResponse() {
        when(actionMapper.toEntity(request)).thenReturn(entity);
        when(actionRepository.save(entity)).thenReturn(entity);
        when(actionMapper.toResponse(entity)).thenReturn(response);

        ActionResponse result = actionsService.createAction(request);

        verify(actionMapper).toEntity(request);
        verify(actionRepository).save(entity);
        verify(actionMapper).toResponse(entity);

        assertEquals(response, result);
    }

    // ✅ updateAction() — cas heureux
    @Test
    void updateAction_shouldUpdateIfExists() {
        when(actionRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(actionMapper.toEntity(request)).thenReturn(entity);
        when(actionRepository.save(entity)).thenReturn(entity);
        when(actionMapper.toResponse(entity)).thenReturn(response);

        ActionResponse result = actionsService.updateAction(1L, request);

        verify(actionRepository).findById(1L);
        verify(actionRepository).save(entity);
        assertEquals(response, result);
    }

    // ✅ updateAction() — action absente
    @Test
    void updateAction_shouldThrowWhenNotFound() {
        when(actionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ActionNotFoundException.class, () ->
            actionsService.updateAction(1L, request)
        );

        verify(actionRepository).findById(1L);
        verifyNoMoreInteractions(actionRepository);
    }

    // ✅ getAction() — action existante
    @Test
    public void getAction_shouldReturnActionWhenFound() {
        when(actionRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(actionMapper.toResponse(entity)).thenReturn(response);

        Optional<ActionResponse> result = actionsService.getAction(1L);

        verify(actionRepository).findById(1L);
        assertEquals(response, result.get());
    }

    // ✅ getAction() — action absente
    @Test
    void getAction_shouldThrowWhenNotFound() {
        when(actionRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<ActionResponse> result = actionsService.getAction(1L);
        
        assertTrue(result.isEmpty());
        verify(actionRepository).findById(1L);
    }

    // ✅ getAllActions()
    @Test
    void getAllActions_shouldReturnAllMappedResponses() {
        List<Action> entities = List.of(entity);
        List<ActionResponse> responses = List.of(response);

        when(actionRepository.findAll()).thenReturn(entities);
        when(actionMapper.toResponse(entity)).thenReturn(response);

        List<ActionResponse> result = actionsService.getAllActions();

        verify(actionRepository).findAll();
        assertEquals(responses, result);
    }

    // ✅ deleteActionById() — action existe
    @Test
    void deleteActionById_shouldDeleteIfExistsAndReturnTrue() {
        when(actionRepository.existsById(1L)).thenReturn(true);

        boolean result = actionsService.deleteActionById(1L);

        verify(actionRepository).existsById(1L);
        verify(actionRepository).deleteById(1L);
        assertTrue(result);
    }

    // ✅ deleteActionById() — action absente
    @Test
    void deleteActionById_shouldReturnFalseWhenNotFound() {
        when(actionRepository.existsById(1L)).thenReturn(false);

        boolean result = actionsService.deleteActionById(1L);

        verify(actionRepository).existsById(1L);
        verify(actionRepository, never()).deleteById(anyLong());
        assertFalse(result);
    }
}

