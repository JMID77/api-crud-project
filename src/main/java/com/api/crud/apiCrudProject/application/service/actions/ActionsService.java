package com.api.crud.apiCrudProject.application.service.actions;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.api.crud.apiCrudProject.application.dto.actions.ActionRequest;
import com.api.crud.apiCrudProject.application.dto.actions.ActionResponse;
import com.api.crud.apiCrudProject.application.mapper.actions.ActionMapper;
import com.api.crud.apiCrudProject.domain.entity.actions.Action;
import com.api.crud.apiCrudProject.domain.repository.actions.ActionRepository;
import com.api.crud.apiCrudProject.infrastructure.exception.actions.ActionNotFoundException;

@Service
public class ActionsService {
    private final ActionRepository actionRepository;
    private final ActionMapper actionMapper;
    
    public ActionsService(ActionRepository actionRepository, ActionMapper actionMapper) {
        this.actionRepository = actionRepository;
        this.actionMapper = actionMapper;
    }

    public ActionResponse createAction(ActionRequest actionRequest) {
        var action = this.actionRepository.save(this.actionMapper.toEntity(actionRequest));
        return this.actionMapper.toResponse(action);
    }
    
    public ActionResponse updateAction(Long id, ActionRequest actionRequest) {
        Action existingAction = this.actionRepository.findById(id).orElse(null);
        if (existingAction == null || existingAction.getId() != id) {
            throw new ActionNotFoundException("The action "+id+" not the correct action !", id);
        }

        Action theAction = this.actionMapper.toEntity(actionRequest);

        theAction.setId(id);
        
        var action = this.actionRepository.save(theAction);
        return this.actionMapper.toResponse(action);
    }

    public Optional<ActionResponse> getAction(Long id) {
        return this.actionRepository.findById(id).map(this.actionMapper::toResponse);
    }

    public List<ActionResponse> getAllActions() {
        return this.actionRepository.findAll().stream().map(this.actionMapper::toResponse).toList();
    }

    public boolean deleteActionById(Long id) {
        if (this.actionRepository.existsById(id)) {
            this.actionRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

}
