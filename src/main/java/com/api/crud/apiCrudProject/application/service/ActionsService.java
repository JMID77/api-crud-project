package com.api.crud.apiCrudProject.application.service;

import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.api.crud.apiCrudProject.application.dto.ActionRequest;
import com.api.crud.apiCrudProject.application.dto.ActionResponse;
import com.api.crud.apiCrudProject.application.mapper.ActionMapper;
import com.api.crud.apiCrudProject.domain.entity.Action;
import com.api.crud.apiCrudProject.domain.entity.enumeration.ActionStatus;
import com.api.crud.apiCrudProject.domain.repository.ActionRepository;
import com.api.crud.apiCrudProject.infrastructure.exception.Entities;
import com.api.crud.apiCrudProject.infrastructure.exception.RessourceNotFoundException;

@Service
public class ActionsService {
    private final ActionRepository actionRepository;
    private final ActionMapper actionMapper;
    
    public ActionsService(@Lazy ActionRepository actionRepository, ActionMapper actionMapper) {
        this.actionRepository = actionRepository;
        this.actionMapper = actionMapper;
    }

    public ActionResponse createAction(ActionRequest actionRequest) {
        var action = this.actionRepository.persist(this.actionMapper.toEntity(actionRequest));
        return this.actionMapper.toResponse(action);
    }
    
    public ActionResponse updateAction(Long id, ActionRequest actionRequest) {
        ActionResponse actionResponse = null;

        if (checkExistsAction(id)) {
            System.out.println("actionRequest: " + actionRequest);
            System.out.println("actionRequest: " + actionRequest.actionName()+"/"+actionRequest.actionStatus());
            Action theAction = actionMapper.toEntity(actionRequest);
            
            theAction.setId(id);
            
            var action = this.actionRepository.persist(theAction);
            actionResponse = this.actionMapper.toResponse(action);
        }

        return actionResponse;
    }

    public ActionResponse getAction(Long id) {
        return this.actionRepository.searchById(id)
                                        .map(this.actionMapper::toResponse)
                                        .orElseThrow(() -> new RessourceNotFoundException(Entities.ACTION, id));
    }

    public List<ActionResponse> getAllActions() {
        return this.actionRepository.searchAll().stream().map(actionMapper::toResponse).toList();
    }

    public void deleteActionById(Long id) {
        if (checkExistsAction(id)) {
            this.actionRepository.removeById(id);
        }
    }

    public List<ActionResponse> getActionsByStatus(ActionStatus actionStatus) {
        return this.actionRepository.searchActionStatusByStatus(actionStatus.name()).stream().map(this.actionMapper::toResponse).toList();
    }

    private boolean checkExistsAction(Long id) {
        if (!this.actionRepository.checkById(id)) {
            throw new RessourceNotFoundException(Entities.ACTION, id);
        }
        return true;
    } 

}
