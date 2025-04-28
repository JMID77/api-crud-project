package com.api.crud.apiCrudProject.application.mapper;

import org.springframework.stereotype.Component;

import com.api.crud.apiCrudProject.application.dto.ActionRequest;
import com.api.crud.apiCrudProject.application.dto.ActionResponse;
import com.api.crud.apiCrudProject.domain.entity.Action;

@Component
public class ActionMapper {
    public Action toEntity(ActionRequest actionRequest) {
        return new Action(null, actionRequest.actionName(), actionRequest.actionStatus());
    }

    public ActionResponse toResponse(Action action) {
        return new ActionResponse(action.getId(), action.getActionName(), action.getActionStatus());
    }
}
