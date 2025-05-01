package com.api.crud.apiCrudProject.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import com.api.crud.apiCrudProject.infrastructure.exception.RessourceNotFoundException;

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
        when(this.actionMapper.toEntity(request)).thenReturn(entity);
        when(this.actionRepository.save(entity)).thenReturn(entity);
        when(this.actionMapper.toResponse(entity)).thenReturn(response);

        ActionResponse result = this.actionsService.createAction(request);

        verify(this.actionMapper).toEntity(request);
        verify(this.actionRepository).save(entity);
        verify(this.actionMapper).toResponse(entity);

        assertEquals(response, result);
    }

    // ✅ updateAction() — cas heureux
    @Test
    void updateAction_shouldUpdateIfExists() {
        when(this.actionRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(this.actionMapper.toEntity(request)).thenReturn(entity);
        when(this.actionRepository.save(entity)).thenReturn(entity);
        when(this.actionMapper.toResponse(entity)).thenReturn(response);

        ActionResponse result = this.actionsService.updateAction(1L, request);

        verify(this.actionRepository).findById(1L);
        verify(this.actionRepository).save(entity);
        assertEquals(response, result);
    }

    // ✅ updateAction() — action absente
    @Test
    void updateAction_shouldThrowWhenNotFound() {
        when(this.actionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RessourceNotFoundException.class, () ->
            this.actionsService.updateAction(1L, request)
        );

        verify(this.actionRepository).findById(1L);
        verifyNoMoreInteractions(this.actionRepository);
    }

    // ✅ getAction() — action existante
    @Test
    public void getAction_shouldReturnActionWhenFound() {
        when(this.actionRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(this.actionMapper.toResponse(entity)).thenReturn(response);
    
        ActionResponse result = this.actionsService.getAction(1L);
    
        verify(this.actionRepository).findById(1L);
        verify(this.actionMapper).toResponse(entity);
        assertEquals(response, result);
    }

    // ✅ getAction() — action absente
    @Test
    public void getAction_shouldThrowExceptionWhenNotFound() {
        when(this.actionRepository.findById(1L)).thenReturn(Optional.empty());
    
        assertThrows(RessourceNotFoundException.class, () -> {
            this.actionsService.getAction(1L);
        });
    
        verify(this.actionRepository).findById(1L);
        verifyNoMoreInteractions(this.actionMapper);
    }

    // ✅ getAllActions()
    @Test
    void getAllActions_shouldReturnAllMappedResponses() {
        List<Action> entities = List.of(entity);
        List<ActionResponse> responses = List.of(response);

        when(this.actionRepository.retrieveAll()).thenReturn(entities);
        when(this.actionMapper.toResponse(entity)).thenReturn(response);

        List<ActionResponse> result = this.actionsService.getAllActions();

        verify(this.actionRepository).retrieveAll();
        assertEquals(responses, result);
    }

    // ✅ deleteActionById() — action existe
    @Test
    void deleteActionById_shouldDeleteIfExists() {
        when(this.actionRepository.existsById(1L)).thenReturn(true);
    
        this.actionsService.deleteActionById(1L);
    
        verify(this.actionRepository).existsById(1L);
        verify(this.actionRepository).deleteById(1L);
    }

    // ✅ deleteActionById() — action absente
    @Test
    void deleteActionById_shouldReturnFalseWhenNotFound() {
        when(this.actionRepository.existsById(1L)).thenReturn(false);

        assertThrows(RessourceNotFoundException.class, () -> this.actionsService.deleteActionById(1L));

        verify(this.actionRepository).existsById(1L);
        verify(this.actionRepository, never()).deleteById(anyLong());
    }
}

