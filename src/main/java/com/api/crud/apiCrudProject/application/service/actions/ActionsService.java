package com.api.crud.apiCrudProject.application.service.actions;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.api.crud.apiCrudProject.application.dto.actions.ActionRequest;
import com.api.crud.apiCrudProject.application.dto.actions.ActionResponse;
import com.api.crud.apiCrudProject.application.mapper.actions.ActionMapper;
import com.api.crud.apiCrudProject.domain.repository.actions.ActionRepository;

@Service
public class ActionsService {
    private final ActionRepository actionRepository;
    private final ActionMapper actionMapper;
    
    public ActionsService(ActionRepository actionRepository, ActionMapper actionMapper) {
        this.actionRepository = actionRepository;
        this.actionMapper = actionMapper;
    }

    public ActionResponse createAction(ActionRequest actionRequest) {
        var action = this.actionRepository.save(actionMapper.toEntity(actionRequest));
        return actionMapper.toResponse(action);
    }
    
    public Optional<ActionResponse> getAction(Long id) {
        return this.actionRepository.findById(id).map(actionMapper::toResponse);
    }
    /* 
    public ActionResponse getAction(Long id) {
        return this.actionRepository.findById(id).map(actionMapper::toResponse).orElse(null);
    }
    */

    public List<ActionResponse> getAllActions() {
        return this.actionRepository.findAll().stream().map(actionMapper::toResponse).toList();
    }

    public void deleteActionById(Long id) {
        this.actionRepository.deleteById(id);
    }

}
