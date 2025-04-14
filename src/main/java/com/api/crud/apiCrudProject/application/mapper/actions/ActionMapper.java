package com.api.crud.apiCrudProject.application.mapper.actions;

import org.springframework.stereotype.Component;

import com.api.crud.apiCrudProject.application.dto.actions.ActionResponse;
import com.api.crud.apiCrudProject.domain.entity.actions.Action;
import com.api.crud.apiCrudProject.application.dto.actions.ActionRequest;

@Component
public class ActionMapper {
    public Action toEntity(ActionRequest actionRequest) {
        return new Action(null, actionRequest.actionName(), actionRequest.actionStatus());
    }

    public ActionResponse toResponse(Action action) {
        return new ActionResponse(action.getId(), action.getActionName(), action.getActionStatus());
    }
}
